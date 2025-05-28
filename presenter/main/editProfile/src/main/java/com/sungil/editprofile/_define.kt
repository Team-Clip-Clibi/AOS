package com.sungil.editprofile

const val NAV_PROFILE_MAIN = "profileMain"
const val NAV_CHANGE_NICK_NAME = "changeNickName"
const val NAV_CHANGE_JOB = "changeJob"
const val NAV_CHANGE_LOVE = "changeLove"
const val NAV_CHANGE_LANGUAGE = "language"
const val NAV_LOGOUT = "Login"
const val NAV_SIGN_OUT = "signOut"
const val NAV_DIET =" diet"
const val NAV_MAIN = "Main"

const val ERROR_TO_LONG = "to long"
const val ERROR_TO_SHORT = "to short"
const val ERROR_SPECIAL = "no special"
const val ERROR_TOKEN_NULL = "token is null"
const val ERROR_USER_TOKEN_NLL = "Token is null"
const val ERROR_ALREADY_USE = "Already use"
const val ERROR_NETWORK = "network error"
const val ERROR_FAIL_TO_UPDATE_LOVE = "Fail to update Love"
const val ERROR_FAIL_SAVE = "Save Fail"
const val ERROR_USER_DATA_NULL = "userData is null"
const val ERROR_NONE_DATA_SELECT = "NONE"
const val ERROR_DELETE_FAIL = "delete fail"
const val ERROR_TOKEN_EXPIRE = "reLogin"
const val ERROR_SAVE_DATA_FAIL = "save error"
const val ERROR_UPDATE_FAIL = "update fail"
const val MESSAGE_NICKNAME_OKAY = "name okay"
const val MESSAGE_NICKNAME_UPDATE_SUCCESS = "nick name update Success"

const val MESSAGE_SAVE_SUCCESS ="Save Success"
const val MESSAGE_FAIL_UPDATE_JOB = "Fail to update job"
enum class JOB(val displayName: String) {
    STUDENT("학생"),
    MANUFACTURING("제조업"),
    MEDICAL("의료계"),
    ART("예술계"),
    IT("IT"),
    SERVICE("서비스업"),
    SALES("판매업"),
    BUSINESS("사업"),
    POLITICS("정치"),
    ETC("기타"),
    NONE("NONE");

    companion object {
        fun fromDisplayName(displayName: String): JOB {
            return entries.find { it.displayName == displayName } ?: NONE
        }

        fun fromName(name : String) : JOB {
            return entries.find { it.name == name } ?: NONE
        }
    }
}

enum class LOVE(val displayName: String) {
    SINGLE("싱글"),
    COUPLE("연애중"),
    MARRIAGE("기혼"),
    SECRET("밝히고 싶지 않아요")
}

enum class MEETING(val displayName: Boolean) {
    SAME(true),
    OKAY(false);

    companion object {
        fun fromDisplayName(value: Boolean): MEETING {
            return entries.find { it.displayName == value } ?: OKAY
        }
    }
}

enum class LANGUAGE {
    KOREAN,
    ENGLISH,
    BOTH
}

enum class SignOutData {
    SERVICE,
    APPLY,
    NOT_GOOD,
    NOT_NEED,
    ETC,
    NOTING
}

enum class DIET(val displayName: String) {
    VG("비건이에요"),
    VT("베지테리언이에요"),
    GF("글루텐프리를 지켜요"),
    ALL("다 잘먹어요"),
    ETC("기타"),
    NONE("NULL");

    companion object{
        fun fromDisplayName(value : String) : DIET{
            return entries.find { data -> data.displayName == value } ?: ETC
        }
    }
}