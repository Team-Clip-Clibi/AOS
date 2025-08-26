package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.NetworkResult
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject
import com.clip.domain.model.NotWriteReview
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs

class GetNotWriteReview @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: ArrayList<NotWriteReview>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val request = network.requestNotWriteReview(
            token = TOKEN_FORM + token.first
        )
        when (request) {
            is NetworkResult.Success -> {
                /**
                 * TODO 배포 시 수정!
                 */
//                val data = ArrayList(request.data.map { apiData ->
//                    NotWriteReview(
//                        id = apiData.first,
//                        time = apiData.second,
//                        type = apiData.third
//                    )
//                })
                val fakeData = fakeData()
                val result = sortByClosestDateKST(fakeData)
                return Result.Success(result)
            }

            is NetworkResult.Error -> {
                when (request.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestNotWriteReview(
                            token = TOKEN_FORM + newToken.first
                        )
                        if (reRequest is NetworkResult.Success) {
                            val data = ArrayList(reRequest.data.map { apiData ->
                                NotWriteReview(
                                    id = apiData.first,
                                    time = apiData.second,
                                    type = apiData.third
                                )
                            })
                            val result = sortByClosestDateKST(data)
                            return Result.Success(result)
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }
        }
    }

    /**
     * TODO 배포 시 수정!
     */
    private fun fakeData(): ArrayList<NotWriteReview> {
        val zone = ZoneId.of("Asia/Seoul")
        val nowKst = ZonedDateTime.now(zone)
        val mock = arrayListOf(
            NotWriteReview(103, nowKst.minusDays(2).toInstant().toString(), "RANDOM"),
            NotWriteReview(102, nowKst.minusDays(1).toInstant().toString(), "RANDOM"), // 하루 전
            NotWriteReview(101, nowKst.toInstant().toString(), "RANDOM"),           // 오늘
        )
        return mock
    }

    private fun sortByClosestDateKST(items: List<NotWriteReview>): ArrayList<NotWriteReview> {
        val zone = ZoneId.of("Asia/Seoul")
        val today = LocalDate.now(zone)

        return items
            .asSequence()
            .map { it to Instant.parse(it.time).atZone(zone).toLocalDate() }
            .sortedWith(
                compareBy<Pair<NotWriteReview, LocalDate>> {
                    abs(ChronoUnit.DAYS.between(today, it.second))
                }.thenBy { it.second }
            )
            .map { it.first }
            .toCollection(ArrayList())
    }
}