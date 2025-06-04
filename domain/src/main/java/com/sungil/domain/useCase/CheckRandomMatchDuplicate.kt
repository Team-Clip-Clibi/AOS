package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class CheckRandomMatchDuplicate @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val message: String) : Result
        data class Fail(val meetTime: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        val apiResult = network.requestRandomMatchDuplicate(TOKEN_FORM + token.first)
        when (apiResult.first) {
            200 -> {
                val isDuplicate = apiResult.third ?: false
                if (apiResult.second == null) {
                    return Result.Fail("network Error")
                }
                if (isDuplicate) {
                    return Result.Fail(apiResult.second!!)
                }
                return Result.Success("No Duplicate")
            }
            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val retry = network.requestRandomMatchDuplicate(TOKEN_FORM + newToken.first)
                if (retry.first == 401) {
                    return Result.Fail("reLogin")
                }
                if (retry.second == null) {
                    return Result.Fail("network Error")
                }
                if (retry.first == 200 && retry.third == true) {
                    return Result.Fail(retry.second!!)
                }
                if (retry.first == 200 && retry.third == false) {
                    return Result.Success("No Duplicate")
                }
                return Result.Success("network Error")
            }
            else -> {
                return Result.Fail("network Error")
            }
        }
    }
}