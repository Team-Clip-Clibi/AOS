package com.sungil.domain.model

data class HomeBanner(
    val id: Int,
    val notificationBannerType: NotificationType,
)

enum class NotificationType(val priority: Int) {
    MATCHING(1),
    MATCHING_INFO(2),
    REVIEW(3),
    UNKNOWN(Int.MAX_VALUE);

    companion object {
        fun from(value: String): NotificationType =
            when (value.uppercase()) {
                "MATCHING" -> MATCHING
                "MATCHING_INFO" -> MATCHING_INFO
                "REVIEW" -> REVIEW
                else -> UNKNOWN
            }
    }
}