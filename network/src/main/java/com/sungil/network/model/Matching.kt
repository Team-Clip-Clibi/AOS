package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Matching(
    val id: Int,
    val meetingTime: String,
    val matchingStatus: MatchingStatus,
    val matchingType: String,
    val myOneThingContent: String,
    val isReviewWritten: Boolean,
)
@Serializable
data class MatchingStatus(
    val matchingStatusName: String,
)