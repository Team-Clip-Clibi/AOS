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
        data class Success(val data: List<WeekData>) : Result
        data class Fail(val errorMessage: String) : Result
    }

    suspend fun invoke(): Result {
        return try {
            val startDate = LocalDate.now().plusDays(14)
            val dateFormatter = DateTimeFormatter.ofPattern("MM.dd", Locale.KOREAN)
            val dayOfWeekFormatter = DateTimeFormatter.ofPattern("E", Locale.KOREAN)

            val weekendList = (0..13)
                .map { startDate.plusDays(it.toLong()) }
                .filter {
                    it.dayOfWeek in listOf(
                        DayOfWeek.FRIDAY,
                        DayOfWeek.SATURDAY,
                        DayOfWeek.SUNDAY
                    )
                }
                .map { date ->
                    WeekData(
                        date = date.format(dateFormatter),
                        dayOfWeek = date.format(dayOfWeekFormatter),
                        timeSlots = getTimeSlotsFor(date.dayOfWeek)
                    )
                }
            Result.Success(weekendList)
        } catch (e: Exception) {
            Result.Fail(errorMessage = e.message ?: "Unknown error")
        }
    }


    private fun getTimeSlotsFor(dayOfWeek: DayOfWeek): List<String> {
        return when (dayOfWeek) {
            DayOfWeek.SATURDAY -> listOf(
                MATCHTIME.DINNER.name
            )
            DayOfWeek.SUNDAY -> listOf(MATCHTIME.DINNER.name)
            else -> listOf(MATCHTIME.DINNER.name)
        }
    }

}
