package com.sungil.domain.useCase

import com.sungil.domain.CATEGORY
import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.Match
import com.sungil.domain.model.MatchData
import com.sungil.domain.model.MatchInfo
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetMatch @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
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
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                val saveToken = database.setToken(refreshToken.second!!, refreshToken.third!!)
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest = network.requestMatchingData(TOKEN_FORM + refreshToken.second)
                if (reRequest.responseCode != 200) {
                    return Result.Fail("network error")
                }
                return Result.Success(reRequest.data)
            }
            200 -> {
                val serverMatch = MatchData(
                    oneThingMatch = listOf(
                        MatchInfo(CATEGORY.CONTENT_ONE_THING, 1, 3, "강남역"),
                        MatchInfo(CATEGORY.CONTENT_ONE_THING, 2, 4, "홍대입구")
                    ),
                    randomMatch = listOf(
                        MatchInfo(CATEGORY.CONTENT_RANDOM, 3, 5, "잠실")
                    )
                )
                return Result.Success(serverMatch)
            }
            else -> return Result.Fail("network error")
        }
    }

}