package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDTO(
    val matchingId : Int,
    val meetingTime : String,
    val matchingType : String
)