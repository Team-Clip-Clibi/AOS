package com.clip.network.model

import kotlinx.serialization.Serializable

@Serializable
data class MatchOverView(
    val nextMatchingDate: String?,
    val appliedMatchingCount: Int,
    val confirmedMatchingCount: Int,
    val isAllNoticeRead: Boolean,
)