package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchProgressDTO(
    val nicknameList: List<String>,
    val tmiList: List<String>,
    val nicknameOnethingMap: Map<String, String>,
)