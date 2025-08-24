package com.sungil.domain.model

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class NotWriteReview(
    val id: Int,
    val time: String,
    val type: String,
) {
    val simpleTime = time.toSimpleTime()

    private fun String.toSimpleTime(): String {
        return try {
            val parsed = Instant.parse(this).atZone(ZoneId.of("Asia/Seoul")).toLocalDateTime()
            parsed.format(DateTimeFormatter.ofPattern("MM.dd"))
        } catch (e: Exception) {
            this
        }
    }
}