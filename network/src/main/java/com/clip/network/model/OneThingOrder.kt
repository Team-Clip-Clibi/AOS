package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class OneThingOrder(
    val topic: String,
    val district: String,
    val preferredDates: List<PreferredDate>,
    val tmiContent: String,
    val oneThingBudgetRange: String,
    val oneThingCategory : String
)

@Serializable
data class PreferredDate(
    val date: String,
    val timeSlot: String
)