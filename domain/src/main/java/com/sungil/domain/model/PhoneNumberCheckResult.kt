package com.sungil.domain.model

data class PhoneNumberCheckResult(
    val code: Int,
    val userName: String?,
    val platform: String?,
    val createdAt: String?,
)