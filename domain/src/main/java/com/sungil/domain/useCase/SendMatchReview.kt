package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.NetworkResult
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import javax.inject.Inject

class SendMatchReview @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<SendMatchReview.Param, SendMatchReview.Result> {
    data class Param(
        val mood: String,
        val positivePoints: String,
        val negativePoints: String,
        val reviewContent: String,
        val noShowMembers: String,
        val allAttend: Boolean,
        val matchId: Int,
        val matchType: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val matchId: Int) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val token = database.getToken()
        val result = network.sendReviewData(
            token = TOKEN_FORM + token.first,
            allAttend = param.allAttend,
            matchId = param.matchId,
            matchType = param.matchType,
            mood = param.mood,
            negativePoints = param.negativePoints,
            noShowMembers = if (param.allAttend) "" else param.noShowMembers,
            positivePoints = param.positivePoints,
            reviewContent = param.reviewContent
        )
        when (result) {
            is NetworkResult.Error -> {
                when (result.code) {
                    401 -> {
                        val refreshToken = tokenManger.requestUpdateToken(token.second)
                        if (!refreshToken) return Result.Fail("reLogin")
                        val newToken = database.getToken()
                        val reRequest = network.sendReviewData(
                            token = TOKEN_FORM + newToken.first,
                            allAttend = param.allAttend,
                            matchId = param.matchId,
                            matchType = param.matchType,
                            mood = param.mood,
                            negativePoints = param.negativePoints,
                            noShowMembers = if (param.allAttend) "" else param.noShowMembers,
                            positivePoints = param.positivePoints,
                            reviewContent = param.reviewContent
                        )
                        if (reRequest is NetworkResult.Success) {
                            return Result.Success(param.matchId)
                        }
                        return Result.Fail("network error")
                    }

                    else -> return Result.Fail("network error")
                }
            }

            is NetworkResult.Success -> {
                return Result.Success(param.matchId)
            }
        }
    }
}
