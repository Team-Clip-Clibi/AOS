package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchingOrder(
    val matchingStatus: String,
    val lastMeetingTime: String,
)