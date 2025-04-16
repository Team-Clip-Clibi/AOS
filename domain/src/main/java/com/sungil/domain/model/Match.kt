package com.sungil.domain.model

import com.sungil.domain.CATEGORY

data class Match(
    val responseCode: Int,
    val oneThingMatch: List<MatchInfo>,
    val randomMatch: List<MatchInfo>,
)

data class MatchInfo(
    val category : CATEGORY,
    val matchingId: Int,
    val daysUntilMeeting: Int,
    val meetingPlace: String,
)