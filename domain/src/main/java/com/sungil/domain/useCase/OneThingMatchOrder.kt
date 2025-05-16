package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.WeekData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import javax.inject.Inject

class OneThingMatchOrder @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
) : UseCase<OneThingMatchOrder.Param, OneThingMatchOrder.Result> {
    data class Param(
        val topic: String,
        val districts: List<String>,
        val date : List<WeekData>,
        val tmiContent: String,
        val oneThingBudgetRange: String,
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val orderId: String ,val userId : String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    override suspend fun invoke(param: Param): Result {
        val userId: String = database.getKaKaoId()
        if (userId.trim() == "") {
            return Result.Fail("user id is null")
        }
        val token = database.getToken()
        if (token.first.trim() == "" || token.second.trim() == "") {
            return Result.Fail("token is null")
        }
        val requestOrder = network.requestOneThingOrder(
            token = TOKEN_FORM + token.first,
            topic = param.topic,
            districts = param.districts,
            date = param.date,
            tmiContent = param.tmiContent,
            oneThingBudgetRange = param.oneThingBudgetRange
        )
        when (requestOrder.first) {
            200 -> {
                if (requestOrder.second == null) {
                    return Result.Fail("network error")
                }
                return Result.Success(
                    orderId = requestOrder.second!!,
                    userId = userId
                )
            }

            401 -> {
                val refreshToken = network.requestUpdateToken(
                    refreshToken = token.second
                )
                if (refreshToken.first != 200) {
                    return Result.Fail("reLogin")
                }
                if (refreshToken.second == null || refreshToken.third == null) {
                    return Result.Fail("network error")
                }
                val saveToken = database.setToken(
                    accessToken = refreshToken.second!!,
                    refreshToken = refreshToken.third!!
                )
                if (!saveToken) {
                    return Result.Fail("save error")
                }
                val reRequest = network.requestOneThingOrder(
                    token = TOKEN_FORM + refreshToken.second,
                    topic = param.topic,
                    districts = param.districts,
                    date = param.date,
                    tmiContent = param.tmiContent,
                    oneThingBudgetRange = param.oneThingBudgetRange
                )
                if (reRequest.first != 200 || reRequest.second == null) {
                    return Result.Fail("network error")
                }
                return Result.Success(orderId = reRequest.second!! , userId = userId)
            }

            else -> return Result.Fail("network error")
        }
    }
}