package com.clip.network.model

import kotlinx.serialization.Serializable


@Serializable
data class MatchingResponse(
    val oneThingMatchings: List<MatchingDto>,
    val randomMatchings: List<MatchingDto>,
)

@Serializable
data class MatchingDto(
    val matchingId: Int,
    val daysUntilMeeting: Int,
    val meetingPlace: String,
    val meetingTime: String,
)