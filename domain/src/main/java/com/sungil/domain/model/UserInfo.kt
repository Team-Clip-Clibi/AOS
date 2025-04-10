package com.sungil.domain.model

data class UserInfo(
    val userName: String,
    var nickName: String,
    var phoneNumber: String,
    var job: Pair<String, String>,
    var loveState: Pair<String, String>,
    var diet: String,
    var language: String,
)