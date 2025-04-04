package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.UserInfo
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetUserInfo @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val data: UserInfo) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val userData = database.getUserInfo()
        if (userData != null) {
            return Result.Success(userData)
        }
        val token = database.getToken()
        if (token.first == null || token.second == null) {
            return Result.Fail("Token is null")
        }
        val apiData = network.requestUserData(TOKEN_FORM + token.first!!) ?: return Result.Fail("network Error")
        return Result.Success(apiData)
    }
}