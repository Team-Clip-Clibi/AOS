package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchReviewDTO(
    val mood: String,
    val positivePoints: String,
    val negativePoints: String,
    val reviewContent: String,
    val noShowMembers: String,
    val is_member_all_attended: Boolean,
)