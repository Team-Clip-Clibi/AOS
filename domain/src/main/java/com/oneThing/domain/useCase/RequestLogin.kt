package com.oneThing.domain.useCase

import com.oneThing.domain.UseCase
import com.oneThing.domain.repository.DatabaseRepository
import com.oneThing.domain.repository.DeviceRepository
import com.oneThing.domain.repository.NetworkRepository
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

    /**
     * 400 번대 에러 대응 필요
     */
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
        when (response.first) {
            400 -> {
                return Result.Fail("Fail to login")
            }
            200 -> {
                if (response.second == null || response.third == null) {
                    return Result.Fail("Fail to login")
                }
                database.setToken(response.second!!, response.third!!)
                return Result.Success("Success to Login")
            }
            else -> {
                return Result.Fail("Server error : ${response.first}")
            }
        }
    }
}