package com.clip.domain.model

data class NotificationResponse(
    val responseCode : Int,
    val notificationDataList : List<NotificationData>
)

data class NotificationData(
    val noticeType: String,
    val content: String,
    val link: String,
)