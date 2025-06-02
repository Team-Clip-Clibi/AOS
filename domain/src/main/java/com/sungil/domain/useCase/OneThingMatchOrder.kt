package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.model.WeekData
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class OneThingMatchOrder @Inject constructor(
    private val database: DatabaseRepository,
    private val network: NetworkRepository,
    private val tokenManger: TokenMangerController,
) : UseCase<OneThingMatchOrder.Param, OneThingMatchOrder.Result> {
    data class Param(
        val topic: String,
        val districts: String,
        val date: List<WeekData>,
        val tmiContent: String,
        val oneThingBudgetRange: String,
        val oneThingCategory: String,
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
        param.date.forEach { date ->
            val checkOrderTime = calculateDaysRemainingFromDate(date)
            if (checkOrderTime == null || checkOrderTime < 4) {
                return Result.Fail("order time is not valid")
            }
        }
        val convertedDate = param.date.map { date ->
            date.copy(date = convertToFullDate(date.date))
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

    /**
     * 현재 날짜를 기준으로 몇일이 남앗는지 계산
     */
    private fun calculateDaysRemainingFromDate(weekData: WeekData): Long? {
        val today = LocalDate.now()
        val year = today.year
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        return try {
            val fullDateStr = "$year.${weekData.date}"
            val targetDate = LocalDate.parse(fullDateStr, formatter)
            ChronoUnit.DAYS.between(today, targetDate)
        } catch (e: Exception) {
            null
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