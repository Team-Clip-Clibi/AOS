package com.oneThing.domain.model

import com.oneThing.domain.CATEGORY

data class MatchData(
    val oneThingMatch: List<MatchInfo>,
    val randomMatch: List<MatchInfo>,
)

data class MatchInfo(
    val category : CATEGORY,
    val matchingId: Int,
    val daysUntilMeeting: Int,
    val meetingPlace: String,
    val matchTime : String
)
