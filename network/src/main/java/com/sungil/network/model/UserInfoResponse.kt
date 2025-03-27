package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val userName: String,
    val platform: String,
    val createdAt: String
)