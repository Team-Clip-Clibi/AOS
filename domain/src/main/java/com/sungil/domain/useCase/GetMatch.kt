package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.Match
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class GetMatch @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) {

    sealed interface Result : UseCase.Result {
        data class Success(val data: Match) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val data = network.requestMatchingData(TOKEN_FORM + token.first)
        when (data.responseCode) {
            401 -> {
                val refreshToken = network.requestUpdateToken(TOKEN_FORM + token.second)
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
                return Result.Success(reRequest)
            }

            -100 -> return Result.Fail("network error")
            200 -> return Result.Success(data)
            else -> return Result.Fail("network error")
        }
    }

}