package com.clip.domain.useCase

import com.clip.domain.TOKEN_FORM
import com.clip.domain.UseCase
import com.clip.domain.model.OneThineNotify
import com.clip.domain.repository.DatabaseRepository
import com.clip.domain.repository.NetworkRepository
import com.clip.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetNewNotification @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: List<OneThineNotify>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val oneThingNotification = network.requestOneThineNotification(TOKEN_FORM + token.first)
        when (oneThingNotification.responseCode) {
            401 -> {
                val refreshToke = tokenManger.requestUpdateToken(token.second)
                if (!refreshToke) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestOneThineNotification(TOKEN_FORM + newToken.first)
                if (reRequest.responseCode == 200) {
                    return Result.Success(reRequest.notification)
                }
                if (reRequest.responseCode == 204) {
                    /**
                     * TODO -> 배포시 서버 데이터로만 출력
                     */
                    val testData = OneThineNotify(
                        id = 0,
                        notificationType = "NOTICE",
                        content = "테스트에용",
                        createdAt = "2025-04-23"
                    )
                    return Result.Success(listOf(testData))
                }
                return Result.Fail("network error")
            }

            200 -> {
                return Result.Success(oneThingNotification.notification)
            }

            204 -> {
                val testData = OneThineNotify(
                    id = 0,
                    notificationType = "NOTICE",
                    content = "테스트에용",
                    createdAt = "2025-04-23"
                )
                return Result.Success(listOf(testData))
            }

            else -> {
                return Result.Fail("network error")
            }
        }
    }
}