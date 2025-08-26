package com.clip.domain.model

data class DietResponse(
    val response: Int,
    val diet: DietData,
    )

data class DietData(
    val diet: String,
)