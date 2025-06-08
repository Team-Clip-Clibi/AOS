package com.sungil.domain.useCase

import com.sungil.domain.TOKEN_FORM
import com.sungil.domain.UseCase
import com.sungil.domain.repository.DatabaseRepository
import com.sungil.domain.repository.NetworkRepository
import com.sungil.domain.tokenManger.TokenMangerController
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CheckRandomMatchDuplicate @Inject constructor(
    private val network: NetworkRepository,
    private val database: DatabaseRepository,
    private val tokenManger: TokenMangerController,
) {
    sealed interface Result : UseCase.Result {
        data class Success(val meetTime: String, val nextMeetTime: String) : Result
        data class Fail(val errorMessage: String) : Result
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
                    val date = getDate(apiResult.second!!)
                    val nextDate = getNextDay(apiResult.second!!)
                    return Result.Success(date, nextDate)
                }
                return Result.Success("No Duplicate", "")
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
                    val date = getDate(retry.second!!)
                    val nextDate = getNextDay(retry.second!!)
                    return Result.Success(date, nextDate)
                }
                if (retry.first == 200 && retry.third == false) {
                    return Result.Success("No Duplicate", "")
                }
                return Result.Fail("network Error")
            }
            else -> {
                return Result.Fail("network Error")
            }
        }
    }

    private fun getDate(isoString: String): String {
        val localDateTime = LocalDateTime.parse(isoString)
        val koreaTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"))
        val formatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN)
        return koreaTime.format(formatter)
    }

    private fun getNextDay(isoString: String): String {
        val localDateTime = LocalDateTime.parse(isoString)
        val koreaTime = localDateTime.atZone(ZoneId.of("Asia/Seoul")).plusDays(1)
        val formatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN)
        return koreaTime.format(formatter)
    }
}