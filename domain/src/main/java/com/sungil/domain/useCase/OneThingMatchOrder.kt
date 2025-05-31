package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.WeekData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class OneThingMatchOrder @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger : TokenMangerController
) : UseCase<OneThingMatchOrder.Param, OneThingMatchOrder.Result> {
    data class Param(
        val topic: String,
        val districts: String,
        val date: List<WeekData>,
        val tmiContent: String,
        val oneThingBudgetRange: String,
        val oneThingCategory : String
    ) : UseCase.Param

    sealed interface Result : UseCase.Result {
        data class Success(val orderId: String, val userId: String, val amount: Int) : Result
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
        val convertedDate = param.date.map {
            it.copy(date = convertToFullDate(it.date))
        }
        val requestOrder = network.requestOneThingOrder(
            token = TOKEN_FORM + token.first,
            topic = param.topic,
            districts = param.districts,
            date = convertedDate,
            tmiContent = param.tmiContent,
            oneThingBudgetRange = param.oneThingBudgetRange,
            oneThingCategory = param.oneThingCategory
        )
        when (requestOrder.first) {
            200 -> {
                if (requestOrder.second == null || requestOrder.third == null) {
                    return Result.Fail("network error")
                }
                return Result.Success(
                    orderId = requestOrder.second!!,
                    userId = userId,
                    amount = requestOrder.third!!
                )
            }

            401 -> {
                val refreshToken = tokenManger.requestUpdateToken(token.second)
                if (!refreshToken) return Result.Fail("reLogin")
                val newToken = database.getToken()
                val reRequest = network.requestOneThingOrder(
                    token = TOKEN_FORM + newToken.first,
                    topic = param.topic,
                    districts = param.districts,
                    date = convertedDate,
                    tmiContent = param.tmiContent,
                    oneThingBudgetRange = param.oneThingBudgetRange,
                    oneThingCategory = param.oneThingCategory
                )
                if (reRequest.first == 200) {
                    if (reRequest.second == null || reRequest.third == null) {
                        return Result.Fail("network error")
                    }
                    return Result.Success(
                        orderId = reRequest.second!!,
                        userId = userId,
                        amount = reRequest.third!!
                    )
                }
                return Result.Fail("network error")
            }

            else -> return Result.Fail("network error")
        }
    }

    private fun convertToFullDate(input: String): String {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val inputFormat = SimpleDateFormat("MM.dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(input)
        val calendar = Calendar.getInstance().apply {
            time = date!!
            set(Calendar.YEAR, currentYear)
        }
        return outputFormat.format(calendar.time)
    }

}