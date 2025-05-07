package com.sungil.domain.model

data class UserInfo(
    val responseCode: Int,
    val data: UserData,
)

data class UserData(
    val userName: String,
    var nickName: String?,
    var phoneNumber: String,
    var job: Pair<String, String>,
    var loveState: Pair<String, Boolean>,
    var diet: String,
    var language: String,
)