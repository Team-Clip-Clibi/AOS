package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class RequestUserInfo(
    val username: String?,
    val platform: String,
    val phoneNumber: String?,
    val nickname: String?,
)