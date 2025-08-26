package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OneThingNotificationDTO(
    val id: Int,
    val notificationType : String,
    val content: String,
    val createdAt: String,
)