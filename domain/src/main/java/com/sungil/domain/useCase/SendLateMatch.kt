package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SendLateMatch @Inject constructor(
    private val tokenManger: TokenMangerController,
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<SendLateMatch.Param, SendLateMatch.Result> {
    data class Param(
        val id: Int,
        val matchType: String,
        val lateTime: Int,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val id: Int, val lateTime: Int) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val sendLateMatch = network.sendLateMatch(
            token = TOKEN_FORM + token.first,
            matchType = param.matchType,
            matchId = param.id,
            lateTime = param.lateTime
        )
        when (sendLateMatch) {
            is NetworkResult.Success -> {
                return Result.Success(id = param.id, lateTime = param.lateTime)
            }

            is NetworkResult.Error -> {
                when (sendLateMatch.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.sendLateMatch(
                            token = TOKEN_FORM + newToken.first,
                            matchId = param.id,
                            matchType = param.matchType,
                            lateTime = param.lateTime
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success(id = param.id, lateTime = param.lateTime)
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }
        }
    }
}