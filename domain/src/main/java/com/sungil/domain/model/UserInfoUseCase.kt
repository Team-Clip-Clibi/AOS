package com.sungil.domain.model

data class UserInfoUseCase(
    val userName: String,
    val gender: String,
    val birthYear: String,
    val birthMonth: String,
    val city: String,
    val county: String,
    val servicePermission: Boolean,
    val privatePermission: Boolean,
    val marketingPermission: Boolean,
    val nickName: String,
    val phoneNumber: String,
)