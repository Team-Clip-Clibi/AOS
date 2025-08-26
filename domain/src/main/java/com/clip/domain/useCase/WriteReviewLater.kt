package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.NetworkResult
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class WriteReviewLater @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController
) : UseCase<WriteReviewLater.Param, WriteReviewLater.Result> {
    data class Param(
        val matchId: Int,
        val matchType: String
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data object Success : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val request = network.requestReviewLater(
            token = TOKEN_FORM + token.first,
            matchId = param.matchId,
            matchType = param.matchType
        )
        when (request) {
            is NetworkResult.Error -> {
                when (request.code) {
                    401 -> {
                        val updateToken = tokenManger.requestUpdateToken(token.second)
                        if (!updateToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestReviewLater(
                            token = TOKEN_FORM + newToken.first,
                            matchId = param.matchId,
                            matchType = param.matchType
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }

            is NetworkResult.Success -> {
                return Result.Success
            }
        }
    }
}