package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.Notification
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetNotification @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: Notification) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("token is null")
        }
        val notification = network.requestNotification(TOKEN_FORM + token.first)
        if (notification.responseCode != 200) {
            return Result.Fail("network is error")
        }
        return Result.Success(notification)
    }
}