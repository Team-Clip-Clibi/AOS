package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Notification(
    val noticeType: String,
    val content: String,
    val link: String?,
)

