package com.sungil.domain.model

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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
    val diet: List<String>,
    val category : String
) {
    val simpleTime = matchTime.toSimpleTime()
    val detailTime = matchTime.toDetailDate()
    val week = matchTime.getWeek()

    private fun String.toSimpleTime(): String {
        return try {
            val parsed = Instant.parse(this).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
            parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toDetailDate(): String {
        return try {
            val parsed = Instant.parse(this).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
            val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd(E) a hh시 mm분", Locale.KOREAN)
            parsed.format(formatter)
        } catch (e: Exception) {
            this
        }
    }
    private fun String.getWeek(): String {
        return try {
            val dateTime = Instant.parse(this).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
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