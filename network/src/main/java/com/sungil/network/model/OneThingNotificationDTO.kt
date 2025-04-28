package com.sungil.network.model

import kotlinx.serialization.Serializable
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class OneThingNotificationDTO(
    val id: Int,
    val notificationType : String,
    val content: String,
    val createdAt: String,
)