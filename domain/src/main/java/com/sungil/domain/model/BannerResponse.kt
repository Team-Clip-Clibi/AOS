package com.sungil.domain.model

data class BannerResponse(
    val responseCode: Int,
    val bannerResponse : List<BannerData>
)

data class BannerData(
    val image: String,
    val headText: String,
    val subText: String,
)