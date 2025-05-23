package com.sungil.domain.useCase

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import java.io.IOError
import java.io.IOException
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