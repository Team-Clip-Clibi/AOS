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
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun invoke(): Result {
        return try{
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
        }catch (e : HttpException){
            Result.Fail("Server error : ${e.message}")
        }catch (e : IOException){
            Result.Fail("Server error : ${e.message}")
        }catch (e : Exception){
            Result.Fail("Server error : ${e.message}")
        }
    }
}