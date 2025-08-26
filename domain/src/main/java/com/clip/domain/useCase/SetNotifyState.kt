package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SetNotifyState @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<SetNotifyState.Param, SetNotifyState.Result> {
    data class Param(
        val notifyState: Boolean,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val beforeNotify = database.getNotifyState()
        if (beforeNotify == param.notifyState) {
            return Result.Success("Already same")
        }
        val result = database.setNotifyState(param.notifyState)
        if (!result) {
            return Result.Fail("Error save Notify")
        }
        val isTokenNull = database.getTokenDataStatus()
        if (isTokenNull) {
            return Result.Success("Success save Notify")
        }
        val token = database.getToken()
        val updateResult = network.requestUpdateNotify(
            token = TOKEN_FORM + token.first,
            isAllowNotify = param.notifyState
        )
        when (updateResult) {
            204 -> {
                return Result.Success("Success save Notify")
            }

            401 -> {
                val updateToken = tokenManger.requestUpdateToken(token.first)
                if (!updateToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest =
                    network.requestUpdateNotify(TOKEN_FORM + newToken.second, param.notifyState)
                if (reRequest == 204) {
                    return Result.Success("Success save Notify")
                }
                if (reRequest == 401) {
                    return Result.Fail("reLogin")
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }
}