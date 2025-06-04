package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RandomOrder(
    val topic: String,
    val tmiContent: String,
    val district: String,
)