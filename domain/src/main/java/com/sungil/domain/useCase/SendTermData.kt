package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SendTermData @Inject constructor(
    private val database: DatabaseRepository,
    private val device: DeviceRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<SendTermData.Param, SendTermData.Result> {
    data class Param(
        val termServicePermission: Boolean,
        val privatePermission: Boolean,
        val marketingPermission: Boolean,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val firebaseToken = database.getFcmToken()
        val socialId = database.getKaKaoId()
        val notifyState = database.getNotifyState()
        if (firebaseToken.isEmpty() || socialId.isEmpty()) {
            return Result.Fail("Token is Null firebase : $firebaseToken , kakao : $socialId")
        }

        val osVersion = device.getAndroidOsVersion()
        if (osVersion.toString().isEmpty()) {
            return Result.Fail("Android os version is null")
        }

        val apiResult = network.requestSignUp(
            servicePermission = param.termServicePermission,
            privatePermission = param.privatePermission,
            marketingPermission = param.marketingPermission,
            socialId = socialId,
            osVersion = osVersion.toString(),
            firebaseToken = firebaseToken,
            isAllowNotify = notifyState
        )

        if (apiResult.first.isNullOrEmpty() || apiResult.second.isNullOrEmpty()) {
            return Result.Fail("Fail to get Token accessToken : ${apiResult.first} , refreshToken : ${apiResult.second}")
        }

        val saveToken = database.setToken(apiResult.first!!, apiResult.second!!)
        if (!saveToken) {
            return Result.Fail("Fail to save Token")
        }
        val updateNotifyState =
            network.requestUpdateNotify(TOKEN_FORM + apiResult.first!!, notifyState)
        when (updateNotifyState) {
            204 -> {
                return Result.Success("SignUp Success")
            }

            401 -> {
                val updateToken = tokenManger.requestUpdateToken(apiResult.second!!)
                if (!updateToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest =
                    network.requestUpdateNotify(TOKEN_FORM + newToken.first, notifyState)
                if (reRequest == 204) {
                    return Result.Success("SignUp Success")
                }
                if (reRequest == 401) {
                    return Result.Fail("reLogin")
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }
}