package com.sungil.editprofile

const val NAV_PROFILE_MAIN = "profileMain"
const val NAV_CHANGE_NICK_NAME = "changeNickName"
const val ERROR_TO_LONG = "to long"
const val ERROR_TO_SHORT = "to short"
const val ERROR_SPECIAL = "no special"
const val ERROR_TOKEN_NULL = "token is null"
const val ERROR_USER_TOKEN_NLL = "Token is null"
const val ERROR_ALREADY_USE = "Already use"
const val ERROR_NETWORK = "network Error"

enum class JOB(val displayName : String){
    STUDENT("학생"),
    MANUFACTURING("제조업"),
    MEDICAL("의료계"),
    ART("예술계"),
    IT("IT"),
    SERVICE("서비스업"),
    SALES("판매업"),
    BUSINESS("사업"),
    POLITICS("정치"),
    ETC("기타")
}