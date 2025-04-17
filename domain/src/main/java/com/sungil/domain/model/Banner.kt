package com.sungil.domain.model

data class Banner(
    val responseCode: Int,
    val banner : BannerData
)

data class BannerData(
    val image: String,
    val headText: String,
    val subText: String,
)