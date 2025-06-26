package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.MatchDetail
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetMatchDetail @Inject constructor(
    private val database: DatabaseRepository,
    private val updateToken: TokenMangerController,
    private val network: NetworkRepository,
) : UseCase<GetMatchDetail.Param, GetMatchDetail.Result> {
    data class Param(
        val matchId: Int,
        val matchType: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val matchDetail: MatchDetail) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val result = network.requestMatchDetail(
            token = TOKEN_FORM + token.first,
            matchingId = param.matchId,
            matchType = param.matchType
        )
        when (result) {
            is NetworkResult.Success -> return Result.Success(result.data)
            is NetworkResult.Error -> {
                when (result.code) {
                    401 -> {
                        val refreshToken = updateToken.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestMatchDetail(
                            token = TOKEN_FORM + newToken.first,
                            matchingId = param.matchId,
                            matchType = param.matchType
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success(reRequest.data)
                        }
                        return Result.Fail("network error")
                    }

                    else -> {
                        return Result.Fail("network error")
                    }
                }
            }
        }
    }
}