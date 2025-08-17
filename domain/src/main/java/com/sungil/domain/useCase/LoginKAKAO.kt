package com.sungil.domain.useCase

import android.app.Activity
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.repository.SNSLoginRepository
import javax.inject.Inject

class LoginKAKAO @Inject constructor(
    private val database: DatabaseRepository,
    private val networkRepository: NetworkRepository,
    private val deviceRepository: DeviceRepository,
    private val snsLoginRepository: SNSLoginRepository,
) : UseCase<LoginKAKAO.Param, LoginKAKAO.Result> {

    data class Param(val activity: Activity, val isDebug: Boolean) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val snsData: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val kakaoId = database.getKaKaoId().trim()
        if(kakaoId.isEmpty() && param.isDebug){
            /**
             * TODO 배포 이전 삭제
             */
            return handleLoginWithKakaoId("3975324589")
        }
        when {
            kakaoId.isEmpty() -> {
                if (!snsLoginRepository.checkKAKAOLogin(param.activity)) {
                    val webLogin = snsLoginRepository.loginKAKOWeb(param.activity)
                    if (webLogin.contains("error", ignoreCase = true)) {
                        if(kakaoId.isEmpty() && param.isDebug){
                            /**
                             * TODO 배포 이전 삭제
                             */
                            return handleLoginWithKakaoId("3975324589")
                        }
                        return Result.Fail("kakao Login failed: $webLogin")
                    }
                    return handleLoginWithKakaoId(webLogin)
                }
                val sdkLogin = snsLoginRepository.loginKAKAOSdk(param.activity)
                if (sdkLogin.contains("error", ignoreCase = true)) {
                    if(kakaoId.isEmpty() && param.isDebug){
                        /**
                         * TODO 배포 이전 삭제
                         */
                        return handleLoginWithKakaoId("3975324589")
                    }
                    return Result.Fail("kakao Login failed: $sdkLogin")
                }
                return handleLoginWithKakaoId(sdkLogin)
            }

            else -> {
                return handleLoginWithKakaoId(kakaoId)
            }
        }
    }

    private suspend fun handleLoginWithKakaoId(kakaoId: String): Result {
        val (statusCode, accessToken, refreshToken) = networkRepository.login(
            socialId = kakaoId,
            osVersion = deviceRepository.getAndroidOsVersion().toString(),
            firebaseToken = database.getFcmToken(),
            isAllowNotify = database.getNotifyState()
        )
        return when (statusCode) {
            400 -> {
                database.saveSingUpKey(false)
                Result.Fail("Not SignUp")
            }

            200 -> {
                if (accessToken == null || refreshToken == null) {
                    return Result.Fail("Not SignUp")
                }
                database.setToken(accessToken, refreshToken)
                if (!database.getUserDataStatus()) {
                    return Result.Success("SignUp")
                }
                val userInfo = networkRepository.requestUserData(TOKEN_FORM + accessToken)
                    ?: return Result.Fail("Network Error")
                if (userInfo.data.nickName == null || userInfo.data.userName.isEmpty() || userInfo.data.phoneNumber.isEmpty()) {
                    database.saveSingUpKey(false)
                    return Result.Fail("Not SignUp")
                }
                val saved = database.saveUserInfo(
                    name = userInfo.data.userName,
                    nickName = userInfo.data.nickName ?: "ERROR",
                    platform = "KAKAO",
                    phoneNumber = userInfo.data.phoneNumber,
                    jobList = userInfo.data.job,
                    loveState = userInfo.data.loveState,
                    diet = userInfo.data.diet,
                    language = userInfo.data.language
                )
                if (!saved) {
                    return Result.Fail("Fail to save User Info")
                }
                Result.Success("Success Login")
            }

            else -> Result.Fail("Network Error")
        }
    }
}

