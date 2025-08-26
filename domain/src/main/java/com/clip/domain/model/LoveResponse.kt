package com.clip.domain.model

data class LoveResponse(
    val responseCode: Int,
    val data: Love,
)

data class Love(
    val relationShip: String,
    val isSameRelationShip: Boolean,
)