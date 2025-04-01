package com.sungil.network.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDetailRequest(
    val gender: String,
    val birth: String,
    val city: String,
    val county: String
)