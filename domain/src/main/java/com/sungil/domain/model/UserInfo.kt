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
    val nickName: String,
    var phoneNumber: String,
    var job: Pair<String, String>,
    val loveState: Pair<String, String>,
    val diet: String,
    val language: String,
)