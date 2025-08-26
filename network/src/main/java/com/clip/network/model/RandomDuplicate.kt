package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomDuplicate(
    val meetingTime: String,
    val isDuplicated: Boolean,
)