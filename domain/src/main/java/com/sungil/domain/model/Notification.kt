package com.sungil.domain.model

data class Notification(
    val responseCode : Int,
    val noticeType: String,
    val content: String,
    val link: String,
)