package com.sungil.domain.useCase

import com.sungil.domain.MATCHTIME
import java.util.Locale
import com.sungil.domain.UseCase
import com.sungil.domain.model.WeekData
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetOneThingDay @Inject constructor() {

    sealed interface Result : UseCase.Result {
        data class Success(val data: List<WeekData>, val enterTime: String) : Result
        data class Fail(val errorMessage: String) : Result
    }

    fun invoke(): Result {
        return try {
            val today = LocalDate.now()
            val startDate = today.plusDays(4)
            val endDate = today.plusDays(20)

            val dateFormatter = DateTimeFormatter.ofPattern("MM.dd", Locale.KOREAN)
            val dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN)
            val enterTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREAN)
            val weekendList = mutableListOf<WeekData>()

            var currentDate = startDate
            while (!currentDate.isAfter(endDate)) {
                if (currentDate.dayOfWeek == DayOfWeek.SATURDAY || currentDate.dayOfWeek == DayOfWeek.SUNDAY) {
                    weekendList.add(
                        WeekData(
                            date = currentDate.format(dateFormatter),
                            dayOfWeek = currentDate.format(dayOfWeekFormatter),
                            timeSlots = listOf(MATCHTIME.DINNER.name)
                        )
                    )
                }
                currentDate = currentDate.plusDays(1)
            }
            val enterTime = today.format(enterTimeFormatter)
            Result.Success(weekendList, enterTime)
        } catch (e: Exception) {
            Result.Fail(errorMessage = e.message ?: "Unknown error")
        }
    }
}