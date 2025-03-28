package com.sungil.domain.useCase

import com.sungil.domain.UseCase
import com.sungil.domain.repository.DeviceRepository
import javax.inject.Inject

class CheckNameOkay @Inject constructor(private val deviceRepo : DeviceRepository) : UseCase<CheckNameOkay.Param, CheckNameOkay.Result> {
    data class Param(val name: String) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val name: String = param.name
        if (!name.matches(Regex("^[a-zA-Z가-힣]+$"))) {
            deviceRepo.requestVibrate()
            return Result.Fail("Only english and korean")
        }
        return Result.Success("name Okay")
    }

}