package com.clip.domain.model

data class OneThineNotification(
    val responseCode: Int,
    val notification: List<OneThineNotify>,
)

data class OneThineNotify(
    val id: Int,
    val notificationType: String,
    val content: String,
    val createdAt: String,
)
