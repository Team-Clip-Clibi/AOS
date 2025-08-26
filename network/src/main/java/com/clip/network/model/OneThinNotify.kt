package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OneThinNotify (
    val id: Int,
    val notificationType: String,
    val content: String,
    val createdAt: String
)