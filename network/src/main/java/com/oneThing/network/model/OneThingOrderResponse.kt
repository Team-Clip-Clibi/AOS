package com.oneThing.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OneThingOrderResponse(
    val orderId: String,
    val amount: Int
)