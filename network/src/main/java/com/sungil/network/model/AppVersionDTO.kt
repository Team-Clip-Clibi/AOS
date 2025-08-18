package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class AppVersionDTO(
    val requiredVersion: String,
)