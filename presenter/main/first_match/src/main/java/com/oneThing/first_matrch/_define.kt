package com.oneThing.first_matrch

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
        fun fromDisplayName(displayName: String): JOB? {
            return entries.find { it.displayName == displayName }
        }

        fun fromCode(code: String): JOB? {
            return entries.find { it.name == code }
        }
    }
}

enum class Error(val errorContent: String) {
    ERROR_TO_MANY_SELECT("To many job select");

    companion object {
        fun fromCode(code: String): Error? = entries.find { it.errorContent == code }
    }
}

enum class DomainError(val errorContent : String){
    ERROR_SELECT_NONE("No Change"),
    ERROR_NETWORK_ERROR("network Error"),
    ERROR_SAVE_ERROR("Save Fail");

    companion object{
        fun fromCode(code : String) : DomainError? = entries.find { it.errorContent == code }
    }
}

enum class DIET(val displayName: String) {
    VG("비건이에요"),
    VT("베지테리언이에요"),
    GF("글루텐프리를 지켜요"),
    ALL("다 잘먹어요"),
    ETC("기타"),
    NONE("NULL")
}

enum class LANGUAGE {
    KOREAN,
    ENGLISH,
    BOTH
}

const val NAV_INTRO = "intro"
const val NAV_JOB = "job"
const val NAV_DIET = "diet"
const val NAV_LANGUAGE = "language"

const val NAV_HOME = "Main"
const val NAV_ONE_THING = "oneThing"
const val NAV_RANDOM = ""