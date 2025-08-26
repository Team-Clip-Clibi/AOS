package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class ParticipantsDTO(
    val id: Int,
    val nickname: String,
)