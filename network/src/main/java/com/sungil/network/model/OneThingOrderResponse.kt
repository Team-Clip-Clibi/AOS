package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OneThingOrderResponse(
    val orderId: String,
    val amount: Int
)