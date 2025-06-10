package com.sungil.domain.useCase

import com.sungil.domain.CATEGORY
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.Match
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetMatch @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: MatchData) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val data = network.requestMatchingData(TOKEN_FORM + token.first)
        when (data.responseCode) {
            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestMatchingData(TOKEN_FORM + newToken.first)
                if (reRequest.responseCode == 200) {
                    val serverMatch = reRequest.data
                    return Result.Success(serverMatch)
                }
                return Result.Fail("network error")
            }

            200 -> {
                val serverMatch = data.data
                return Result.Success(serverMatch)
            }

            else -> return Result.Fail("network error")
        }
    }

}