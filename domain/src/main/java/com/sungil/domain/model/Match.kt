package com.sungil.domain.model

data class Match(
    val responseCode: Int,
    val oneThingMatch: List<MatchData>,
    val randomMatch: List<MatchData>,
)

data class MatchData(
    val matchingId: Int,
    val daysUntilMeeting: Int,
    val meetingPlace: String,
)