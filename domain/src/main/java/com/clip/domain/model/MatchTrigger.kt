package com.clip.domain.model

import java.time.format.DateTimeFormatter
import java.util.Locale
import java.time.ZonedDateTime

data class MatchTrigger(
    val id: Int,
    val time: String,
    val type: String,
    val trigger: String,
) {
    val localTime = time.toLocalTime()
    val matchType = type.toMatchType()

    private fun String.toLocalTime(): String {
        return try {
            val zonedDateTime = ZonedDateTime.parse(this)
            val formatter = DateTimeFormatter.ofPattern("MM월 dd일", Locale.KOREAN)
            val formatted = zonedDateTime.format(formatter)
            return formatted
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toMatchType(): String {
        if (this == "RANDOM") {
            return "랜덤 모임"
        }
        return "원띵모임"
    }

}