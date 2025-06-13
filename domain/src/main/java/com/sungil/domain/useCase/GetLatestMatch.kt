package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

class GetLatestMatch @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val time: String, val applyTime: Int, val confirmTime: Int) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        val token = database.getToken()
        when (val request = network.requestMatchOverView(TOKEN_FORM + token.first)) {
            is NetworkResult.Success -> {
                if(request.data.date == null){
                    return Result.Success(
                        time = "",
                        applyTime = request.data.applyMatch,
                        confirmTime = request.data.confirmMatch
                    )
                }
                return Result.Success(
                    time = request.data.date.toDateTime(),
                    applyTime = request.data.applyMatch,
                    confirmTime = request.data.confirmMatch
                )
            }

            is NetworkResult.Error -> {
                when (request.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) {
                            return Result.Fail("reLogin")
                        }
                        val newToken = database.getToken()
                        val reRequest = network.requestMatchOverView(TOKEN_FORM + newToken.first)
                        return when (reRequest) {
                            is NetworkResult.Error -> {
                                Result.Fail("network error")
                            }

                            is NetworkResult.Success -> {
                                if(reRequest.data.date == null){
                                    return Result.Success(
                                        time = "",
                                        applyTime = reRequest.data.applyMatch,
                                        confirmTime = reRequest.data.confirmMatch
                                    )
                                }
                                Result.Success(
                                    time = reRequest.data.date.toDateTime(),
                                    applyTime = reRequest.data.applyMatch,
                                    confirmTime = reRequest.data.confirmMatch
                                )
                            }
                        }
                    }

                    else -> return Result.Fail("network error")
                }
            }
        }
    }

    private fun String.toDateTime(): String {
        return try {
            val zonedDateTime = ZonedDateTime.parse(this)
            val localDate = zonedDateTime.toLocalDate()
            localDate.format(DateTimeFormatter.ofPattern("MM월 dd일"))
        } catch (e: DateTimeParseException) {
            "ERROR"
        }
    }
}