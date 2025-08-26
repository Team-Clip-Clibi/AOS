package com.clip.domain.model

data class RandomInfo(
    val responseCode: Int,
    val orderId: String,
    val amount: Int,
    val meetingTime: String,
    val meetingPlace: String,
    val meetingLocation: String,
)