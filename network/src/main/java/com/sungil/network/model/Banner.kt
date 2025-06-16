package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class Banner(
    val imagePresignedUrl: String?,
    val text: String?
)