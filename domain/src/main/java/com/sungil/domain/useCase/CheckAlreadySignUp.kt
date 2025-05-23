package com.sungil.domain.useCase

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
        val result = database.getSingUpData()
        if (!result) {
            return Result.Fail("Not SignUp")
        }
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
                return Result.Success("SignUp")
            }

            else -> return Result.Fail("Network Error")
        }
    }
}