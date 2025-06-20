package com.sungil.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MatchingData(
    val id: Int,
    val meetTime: String,
    val matchingStatusName: String,
    val matchingType: String,
    val myOneThingContent: String,
    val isReviewWritten: Boolean,
) {
    val formattedTime: String get() = meetTime.toViewTime()

    private fun String.toViewTime(): String {
        return try {
            val parsed = LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            parsed.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        } catch (e: Exception) {
            this
        }
    }
}