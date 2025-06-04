package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class GetRandomMatch @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<GetRandomMatch.Params, GetRandomMatch.Result> {
    data class Params(
        val tmi: String,
        val topic: String,
        val district: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(
            val orderId: String,
            val amount: Int,
            val meetingTime: String,
            val meetingPlace: String,
            val meetingLocation: String,
        ) : Result

        data class Error(val error: String) : Result
    }

    override suspend fun invoke(param: Params): Result {
        val token = database.getToken()
        val result = network.requestRandomMatch(
            token = TOKEN_FORM + token.first,
            tmiContent = param.tmi,
            topic = param.topic,
            district = param.district
        )
        when (result.responseCode) {
            200 -> {
                if (result.orderId.trim()
                        .isEmpty() || result.amount < 0 || result.meetingTime.trim()
                        .isEmpty() || result.meetingPlace.trim()
                        .isEmpty() || result.meetingLocation.trim().isEmpty()
                ) {
                    return Result.Error("network Error")
                }
                return Result.Success(
                    orderId = result.orderId,
                    amount = result.amount,
                    meetingTime = result.meetingTime,
                    meetingPlace = result.meetingPlace,
                    meetingLocation = result.meetingLocation
                )
            }

            400 -> {
                return Result.Error("Random Match Error")
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Error("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestRandomMatch(
                    token = TOKEN_FORM + newToken.first,
                    tmiContent = param.tmi,
                    topic = param.topic,
                    district = param.district
                )
                if (reRequest.responseCode == 200) {
                    return Result.Success(
                        orderId = reRequest.orderId,
                        amount = reRequest.amount,
                        meetingTime = reRequest.meetingTime,
                        meetingPlace = reRequest.meetingPlace,
                        meetingLocation = reRequest.meetingLocation
                    )
                }
                return Result.Error("network Error")
            }

            else -> {
                return Result.Error("network Error")
            }
        }
    }
}