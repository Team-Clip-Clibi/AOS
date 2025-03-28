package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DeviceRepository
import javax.inject.Inject

class CheckNickName @Inject constructor(private val deviceRepo : DeviceRepository) :
    UseCase<CheckNickName.Param, CheckNickName.Result> {

    data class Param(
        val name: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val message: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val name = param.name
        if (name.length > 8) {
            deviceRepo.requestVibrate()
            return Result.Fail("to long")
        }
        if (name.length < 2) {
            deviceRepo.requestVibrate()
            return Result.Fail("to short")
        }
        if (!name.matches(Regex("^[a-zA-Z가-힣]+$"))) {
            deviceRepo.requestVibrate()
            return Result.Fail("no special")
        }
        return Result.Success("name okay")
    }

}