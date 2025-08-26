package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.DeviceRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

/**
 * 닉네임 중복 검사만 실행
 */
class CheckNickName @Inject constructor(
    private val deviceRepo: DeviceRepository,
    private val networkRepo: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
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
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val retry = networkRepo.checkNickName(
                    data = name,
                    token = TOKEN_FORM + newToken.first
                )
                if (retry == 200) {
                    deviceRepo.requestVibrate()
                    return Result.Success("name okay")
                }
                if (retry == 204) {
                    deviceRepo.requestVibrate()
                    return Result.Success("Already use")
                }
                if (retry == 401) {
                    return Result.Fail("reLogin")
                }
                return Result.Fail("network Error")
            }

            else -> return Result.Fail("network Error")
        }
    }

}