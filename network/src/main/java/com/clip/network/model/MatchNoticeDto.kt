package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchNoticeDto(
    val matchingId: Int,
    val meetingTime: String,
    val matchingStatus: List<String>,
    val matchingType: String,
    val myOneThingContent: String,
    val restaurantName: String,
    val location: String,
    val menu: String,
    val cuisineType : String,
    val jobInfos: List<JobInfo>,
    val dietaryList: List<String>,
)

@Serializable
data class JobInfo(
    val jobName: String,
    val count: Int,
)
