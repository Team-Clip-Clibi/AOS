package com.sungil.domain.model

import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

data class MatchDetail(
    val time: String,
    val matchStatus: String,
    val matchType: String,
    val matchTime: List<MatchDate>,
    val matchCategory: String,
    val matchBudget: String,
    val matchContent: String,
    val matchPrice: Int,
    val paymentPrice: Int,
    val requestTime: String,
    val approveTime: String,
    val district: String,
    val job: String,
    val loveState: String,
    val diet: String,
    val language: String,
) {
    val simpleTime = time.toSimpleDate()
    val detailTime = time.toDetailDate()
    val cancelButton = requestTime.cancelButton()

    private fun String.toSimpleDate(): String {
        return try {
            val parsed = OffsetDateTime.parse(this)
            parsed.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        } catch (e: Exception) {
            this
        }
    }

    private fun String.toDetailDate(): String {
        return try {
            val parsed = OffsetDateTime.parse(this)
            parsed.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"))
        } catch (e: Exception) {
            this
        }
    }

    private fun String.cancelButton(): Boolean {
        return try {
            val parsedTime = OffsetDateTime.parse(this)
            val currentTime = OffsetDateTime.now()

            val duration = Duration.between(parsedTime, currentTime).abs()
            duration.toHours() < 24
        } catch (e: Exception) {
            false
        }
    }
}

data class MatchDate(
    val date: String,
    val time: String,
) {
    val week = date.getWeek()
    private fun String.getWeek(): String {
        return try {
            val date = LocalDate.parse(this)
            val dayOfWeek = date.dayOfWeek
            dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN)
        } catch (e: Exception) {
            "EE"
        }
    }
}