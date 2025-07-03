package com.sungil.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class MatchNotice(
    val matchId: Int,
    val matchTime: String,
    val matchStatus: String,
    val matchType: String,
    val restaurantName: String,
    val restaurantAddress: String,
    val menuCategory: String,
    val jonInfos: List<Job>,
    val diet: List<DietOption>,
    val category : String
) {
    val simpleTime = matchTime.toSimpleTime()
    val detailTime = matchTime.toDetailDate()
    val week = matchTime.getWeek()

    private fun String.toSimpleTime(): String {
        return try {
            val parsed = LocalDateTime.parse(this)
            parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toDetailDate(): String {
        return try {
            val parsed = LocalDateTime.parse(this)
            parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
        } catch (e: Exception) {
            this
        }
    }
    private fun String.getWeek(): String {
        return try {
            val dateTime = LocalDateTime.parse(this)
            val date = dateTime.toLocalDate()
            val dayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        } catch (e: Exception) {
            "EE"
        }
    }
}

data class Job(
    val jobName: String,
    val count: Int,
)

data class DietOption(
    val dietaryOption: String,
    val count: Int,
)