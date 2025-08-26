package com.clip.domain.model

import java.time.OffsetDateTime
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
    val matchStatus: String get() = matchingStatusName.toViewStatus()
    val matchType: String get() = matchingType.toViewMatchType()

    private fun String.toViewTime(): String {
        return try {
            val parsed = OffsetDateTime.parse(this)
            parsed.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toViewStatus(): String {
        return when (this) {
            "APPLIED" -> "신청완료"
            "CONFIRMED" -> "모임확정"
            "CANCELED" -> "취소"
            "COMPLETED" -> "모임종료"
            else -> this
        }
    }

    private fun String.toViewMatchType(): String {
        return when (this) {
            "ONETHING" -> "원띵모임"
            "RANDOM" -> "랜덤모임"
            else -> this
        }
    }
}