package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class RequestLogin @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val device: DeviceRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val kakaoId: String = database.getKaKaoId()
        val osVersion: String = device.getAndroidOsVersion().toString()
        val firebaseToken: String = database.getFcmToken()
        val isAllowNotify: Boolean = database.getNotifyState()
        val response = network.login(
            socialId = kakaoId,
            osVersion = osVersion,
            firebaseToken = firebaseToken,
            isAllowNotify = isAllowNotify
        )
        if (response.first == null || response.second == null) {
            return Result.Fail("Fail to login")
        }
        val saveResult = database.setToken(response.first!!, response.second!!)
        if (!saveResult) {
            return Result.Fail("Fail to save token")
        }
        return Result.Success("Success to Login")
    }
}