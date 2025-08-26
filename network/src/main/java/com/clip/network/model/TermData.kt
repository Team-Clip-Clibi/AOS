package com.clip.network.model

import kotlinx.serialization.Serializable
@Serializable
data class TermData(
    val servicePermission: Boolean,
    val privatePermission: Boolean,
    val marketingPermission: Boolean,
    val socialId: String,
    val platform: String,
    val deviceType: String,
    val osVersion: String,
    val firebaseToken: String,
    val isAllowNotify: Boolean
)