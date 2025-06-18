package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.model.NotificationData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import com.sungil.domain.useCase.GetLatestMatch.Result
import javax.inject.Inject

class GetNotification @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<NotificationData>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Any {
        val token = database.getToken()
        return when (val notification = network.requestNotification(TOKEN_FORM + token.first)) {
            is NetworkResult.Success -> {
                Result.Success(notification.data.notificationDataList)
            }

            is NetworkResult.Error -> {
                when (notification.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.requestNotification(TOKEN_FORM + newToken.first)
                        when (reRequest) {
                            is NetworkResult.Success -> {
                                return Result.Success(reRequest.data.notificationDataList)
                            }

                            is NetworkResult.Error -> {
                                return Result.Fail("reLogin")
                            }
                        }
                    }
                    else -> return Result.Fail("network error")
                }
            }
        }
    }
}