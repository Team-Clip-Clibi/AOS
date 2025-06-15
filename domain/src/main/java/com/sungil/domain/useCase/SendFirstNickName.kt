package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SendFirstNickName @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenMangerController: TokenMangerController
) :
    UseCase<SendFirstNickName.Param, SendFirstNickName.Result> {
    data class Param(
        val nickName: String
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val result = network.inputNickName(token = TOKEN_FORM + token.first, data = param.nickName)
        when (result) {
            204 -> {
                return Result.Success("nick name update Success")
            }

            400 -> {
                return Result.Fail("update fail")
            }

            401 -> {
                val refreshToken = tokenMangerController.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest =
                    network.inputName(token = TOKEN_FORM + newToken.first, data = param.nickName)
                if (reRequest != 204) {
                    return Result.Fail("update fail")
                }
                return Result.Success("nick name update Success")
            }

            else -> return Result.Fail("network Error")
        }
    }
}