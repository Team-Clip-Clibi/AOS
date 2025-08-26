package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val socialId: String,
    val platform: String,
    val deviceType: String,
    val osVersion: String,
    val firebaseToken: String,
    val isAllowNotify: Boolean,
)