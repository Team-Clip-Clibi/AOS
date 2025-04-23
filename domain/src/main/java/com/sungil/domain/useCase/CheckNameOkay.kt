package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckNameOkay @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
) : UseCase<CheckNameOkay.Param, CheckNameOkay.Result> {
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
        val token = database.getToken()
        val response = network.inputName(name, TOKEN_FORM+ token.first)
        if(response == 401){
            return Result.Fail("reLogin")
        }
        if (response != 204) {
            return Result.Fail("network error")
        }
        return Result.Success("name Okay")
    }

}