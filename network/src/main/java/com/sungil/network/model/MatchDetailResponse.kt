package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchDetailResponse(
    val matchingId: Int,
    val meetingTime: String,
    val matchingStatus: MatchingStatus,
    val matchingType: String,
    val myOneThingContent: String,
    val applicationInfo: ApplicationInfo,
    val myMatchingInfo: MyMatchingInfo,
    val paymentInfo: PaymentInfo
)
@Serializable
data class ApplicationInfo(
    val district: String,
    val preferredDates: List<PreferredDate>,
    val oneThingBudgetRange: String,
    val oneThingCategory: String
)
@Serializable
data class MyMatchingInfo(
    val job: String,
    val relationshipStatus: String,
    val dietaryOption: String,
    val language: String
)
@Serializable
data class PaymentInfo(
    val matchingPrice: Int,
    val paymentPrice: Int,
    val requestedAt: String,
    val approvedAt: String
)