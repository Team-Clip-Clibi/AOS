package com.sungil.domain.model


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
            val isoFormat = java.text.SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                java.util.Locale.getDefault()
            )
            isoFormat.timeZone = java.util.TimeZone.getTimeZone("UTC")
            val date = isoFormat.parse(this)

            val outputFormat = java.text.SimpleDateFormat("MM월 dd일", java.util.Locale.getDefault())
            outputFormat.format(date!!)
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