package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DeviceRepository
import javax.inject.Inject

class CheckTossInstall @Inject constructor(private val device: DeviceRepository) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val checkTossInstall = device.checkTossInstall()
        if (!checkTossInstall) {
            return Result.Fail("not install toss")
        }
        return Result.Success("install toss")
    }
}