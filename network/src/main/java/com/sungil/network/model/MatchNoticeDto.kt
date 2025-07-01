package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchNoticeDto(
    val matchingId: Int,
    val meetingTime: String,
    val matchingStatus: String,
    val matchingType: String,
    val myOneThingContent: String,
    val restaurantName: String,
    val location: String,
    val menuCategory: String,
    val jobInfos: List<JobInfo>,
    val dietaryInfos: List<DietaryInfo>,
)

@Serializable
data class JobInfo(
    val jobName: String,
    val count: Int,
)

@Serializable
data class DietaryInfo(
    val dietaryOption: String,
    val count: Int,
)