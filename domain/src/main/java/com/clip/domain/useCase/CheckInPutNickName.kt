package com.clip.domain.useCase

import com.clip.domain.UseCase
import com.clip.domain.repository.DeviceRepository
import javax.inject.Inject

class CheckInPutNickName @Inject constructor(
    private val deviceRepo: DeviceRepository,
) :
    UseCase<CheckInPutNickName.Param, CheckInPutNickName.Result> {
    data class Param(
        val name: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        if (param.name.length <= 2) {
            return Result.Fail("to short")
        }
        if (param.name.length > 8) {
            return Result.Fail("to long")
        }
        if (!param.name.matches(Regex("^[a-zA-Z가-힣0-9]+$"))) {
            deviceRepo.requestVibrate()
            return Result.Fail("no special")
        }
        return Result.Success("success")
    }
}