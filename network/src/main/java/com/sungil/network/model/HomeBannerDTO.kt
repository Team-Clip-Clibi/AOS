package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeBannerDTO(
    val id: Int,
    val notificationBannerType: String,
)