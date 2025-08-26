package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AppVersionDTO(
    val requiredVersion: String,
)