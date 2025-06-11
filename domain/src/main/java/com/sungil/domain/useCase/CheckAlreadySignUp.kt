package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckAlreadySignUp @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val device: DeviceRepository,
) : UseCase<CheckAlreadySignUp.Param, CheckAlreadySignUp.Result> {
    data class Param(
        val socialId: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val requestLogin = network.login(
            socialId = param.socialId,
            osVersion = device.getAndroidOsVersion().toString(),
            firebaseToken = database.getFcmToken(),
            isAllowNotify = database.getNotifyState()
        )
        when (requestLogin.first) {
            400 -> {
                database.saveSingUpKey(false)
                return Result.Fail("Not SignUp")
            }

            200 -> {
                if (requestLogin.second == null || requestLogin.third == null) {
                    return Result.Fail("Not SignUp")
                }
                database.setToken(requestLogin.second!!, requestLogin.third!!)
                val userDataStatus = database.getUserDataStatus()
                if (!userDataStatus) {
                    return Result.Success("SignUp")
                }
                val requestUserInfo = network.requestUserData(TOKEN_FORM + requestLogin.second!!)
                    ?: return Result.Fail("Network Error")
                if(requestUserInfo.data.nickName == null || requestUserInfo.data.userName == "" || requestUserInfo.data.phoneNumber == ""){
                    database.saveSingUpKey(false)
                    return Result.Fail("Not SignUp")
                }
                val saveUserInfo = database.saveUserInfo(
                    name = requestUserInfo.data.userName,
                    nickName = requestUserInfo.data.nickName ?: "ERROR",
                    platform = "KAKAO",
                    phoneNumber = requestUserInfo.data.phoneNumber,
                    jobList = requestUserInfo.data.job,
                    loveState = requestUserInfo.data.loveState,
                    diet = requestUserInfo.data.diet,
                    language = requestUserInfo.data.language
                )
                if (!saveUserInfo) {
                    return Result.Fail("Save error")
                }
                return Result.Success("SignUp")
            }

            else -> return Result.Fail("Network Error")
        }
    }
}