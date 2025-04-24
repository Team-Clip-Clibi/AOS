package com.sungil.domain.model

import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

data class Notification(
    val id: Int,
    val content: String,
    val createdAt: String,
) {
    val formattedTime: String
        get() = createdAt.toFriendlyTime()

    private fun String.toFriendlyTime(): String {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val time = ZonedDateTime.parse(this, formatter)
        val now = ZonedDateTime.now(ZoneId.systemDefault())

        val duration = Duration.between(time, now)

        return when {
            duration.toMinutes() < 1 -> "방금 전"
            duration.toMinutes() < 60 -> "${duration.toMinutes()}분 전"
            duration.toHours() < 24 -> "${duration.toHours()}시간 전"
            else -> time.toLocalDate().format(DateTimeFormatter.ofPattern("M월 d일"))
        }
    }
}