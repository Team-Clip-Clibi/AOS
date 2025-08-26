package com.clip.domain.useCase

import android.util.Log
import com.clip.domain.CATEGORY
import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.MatchData
import com.clip.domain.model.MatchInfo
import com.clip.domain.model.NetworkResult
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
        when (val response = network.requestMatchingData(TOKEN_FORM + token.first)) {
            is NetworkResult.Error -> {
                when (response.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestMatchingData(TOKEN_FORM + newToken.first)
                        if (reRequest is NetworkResult.Success) {
                            val serverMatch = reRequest.data
                            return Result.Success(serverMatch)
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }

            is NetworkResult.Success -> {
                if (response.data.randomMatch.isEmpty() || response.data.oneThingMatch.isEmpty()) {
                    val testData = generateDummyMatchData()
                    Log.d(javaClass.name.toString(), "testData insert")
                    return Result.Success(testData)
                }
                return Result.Success(response.data)
            }
        }
    }
}

fun generateDummyMatchData(): MatchData {
    val matchTime = ZonedDateTime.now()
        .plusMinutes(1)
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    return MatchData(
        oneThingMatch = listOf(
            MatchInfo(
                category = CATEGORY.CONTENT_ONE_THING,
                matchingId = 1,
                daysUntilMeeting = 1,
                meetingPlace = "서울 강남역 3번 출구 앞",
                matchTime = matchTime
            )
        ),
        randomMatch = listOf(
            MatchInfo(
                category = CATEGORY.CONTENT_RANDOM,
                matchingId = 2,
                daysUntilMeeting = 0,
                meetingPlace = "서울 신촌역 1번 출구 앞",
                matchTime = matchTime
            )
        )
    )
}