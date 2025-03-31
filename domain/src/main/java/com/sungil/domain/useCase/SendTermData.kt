package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class SendTermData @Inject constructor(
    private val database: DatabaseRepository,
    private val device: DeviceRepository,
    private val network: NetworkRepository,
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

        return Result.Success("SignUp Success")
    }

}