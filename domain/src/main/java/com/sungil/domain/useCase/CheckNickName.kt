package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.DeviceRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class CheckNickName @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val networkRepo: NetworkRepository,
    private val database: DatabaseRepository,
) :
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
        if (name.length <= 2) {
            deviceRepo.requestVibrate()
            return Result.Fail("to short")
        }
        if (!name.matches(Regex("^[a-zA-Z가-힣0-9]+$"))) {
            deviceRepo.requestVibrate()
            return Result.Fail("no special")
        }
        val token = database.getToken()
        val apiResult = networkRepo.checkNickName(name, TOKEN_FORM + token.first)
        if (apiResult == 401) {
            return Result.Fail("reLogin")
        }
        if (apiResult != 200) {
            deviceRepo.requestVibrate()
            return Result.Fail("Already use")
        }
        val inputResult = networkRepo.inputNickName(name, TOKEN_FORM + token.first)
        if (inputResult == 401) {
            return Result.Fail("reLogin")
        }
        if (inputResult != 204) {
            return Result.Fail("network Error")
        }
        return Result.Success("name okay")
    }

}