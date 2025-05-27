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
        when (apiResult) {
            200 -> {
                deviceRepo.requestVibrate()
                return Result.Success("name okay")
            }

            204 -> {
                deviceRepo.requestVibrate()
                return Result.Success("Already use")
            }

            401 -> {
                val refreshToken = networkRepo.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                if (refreshToken.second == null || refreshToken.third == null) {
                    return Result.Fail("network Error")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest = networkRepo.checkNickName(name, TOKEN_FORM + refreshToken.second!!)
                if (reRequest == 200) {
                    deviceRepo.requestVibrate()
                    return Result.Success("name okay")
                }
                if (reRequest == 204) {
                    deviceRepo.requestVibrate()
                    return Result.Fail("Already use")
                }
                return Result.Fail("network Error")
            }

            else -> return Result.Fail("network Error")
        }
    }

}