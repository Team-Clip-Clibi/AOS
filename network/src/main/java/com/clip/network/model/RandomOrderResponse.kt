package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomOrderResponse(
    val orderId: String,
    val amount: Int,
    val meetingTime: String,
    val meetingPlace: String,
    val meetingLocation: String,
    val matchingId: Int,
)