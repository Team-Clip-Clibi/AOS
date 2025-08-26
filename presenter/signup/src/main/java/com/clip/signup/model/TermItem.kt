package com.clip.signup.model

data class TermItem(
    val termName: String,
    val isRequired: Boolean,
    val isAllCheck: Boolean = false,
    val checked: Boolean = false,
)