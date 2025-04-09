package com.sungil.domain.model

data class UserInfo(
    val userName: String,
    val gender: String,
    val birthYear: String,
    val birthMonth: String,
    val birthDay: String,
    val city: String,
    val county: String,
    val marketingPermission: Boolean,
    var nickName: String,
    var phoneNumber: String,
    var job: Pair<String, String>,
    var loveState: Pair<String, String>,
    var diet: String,
    var language: String,
)