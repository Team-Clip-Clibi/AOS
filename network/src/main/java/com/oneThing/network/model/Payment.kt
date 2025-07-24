package com.oneThing.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Payment(
    val paymentKey : String,
    val orderId : String,
    val orderType : String
)