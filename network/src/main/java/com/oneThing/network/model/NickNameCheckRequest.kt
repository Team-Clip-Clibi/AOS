package com.oneThing.network.model

import kotlinx.serialization.Serializable

@Serializable
data class NickNameCheckRequest(
    val nickname : String
)