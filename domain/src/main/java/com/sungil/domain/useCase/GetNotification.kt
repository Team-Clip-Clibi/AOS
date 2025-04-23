package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NotificationData
import com.sungil.domain.model.NotificationResponse
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetNotification @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<NotificationData>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val notification = network.requestNotification(TOKEN_FORM + token.first)
        when (notification.responseCode) {
            401 -> {
                val refreshToken = network.requestUpdateToken(token.second)
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                val saveNewToken = database.setToken(
                    accessToken = refreshToken.second!!,
                    refreshToken = refreshToken.third!!
                )
                if (!saveNewToken) {
                    return Result.Fail("save error")
                }
                val reRequest = network.requestNotification(TOKEN_FORM + refreshToken.second)
                if (reRequest.responseCode != 200) {
                    return Result.Fail("network error")
                }
                return Result.Success(reRequest.notificationDataList)
            }

            200 -> {
                return Result.Success(notification.notificationDataList)
            }

            204 -> {
                return Result.Success(
                    listOf(
                        NotificationData(
                            noticeType = "NOTICE",
                            content = "테스트에용",
                            link = "www.naver.com"
                        ),
                    )
                )
            }

            else -> return Result.Fail("network error")
        }
    }
}