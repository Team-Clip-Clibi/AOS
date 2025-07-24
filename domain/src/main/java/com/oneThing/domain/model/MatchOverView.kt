package com.oneThing.domain.model

data class MatchOverView(
    val responseCode: Int,
    val date: String?,
    val applyMatch: Int,
    val confirmMatch: Int,
    val isAllNoticeRead: Boolean,
)