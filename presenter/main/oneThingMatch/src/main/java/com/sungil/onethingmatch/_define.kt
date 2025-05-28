package com.sungil.onethingmatch

const val NAV_HOME = "Main"
const val NAV_PAY = "pay"

const val NAV_INTRO = "intro"
const val NAV_SUBJECT = "subject"
const val NAV_CATEGORY = "category"
const val NAV_LOCATION = "location"
const val NAV_DATE = "date"
const val NAV_BUDGET = "budget"
const val NAV_TMI = "tmi"
const val NAV_BEFORE_PAY = "beforePay"

enum class CATEGORY(val displayName: String) {
    ECONOMY("경제"),
    GRADUATE_SCHOOL("대학원"),
    PET("반려동물"),
    BEAUTY("뷰티"),
    VOLUNTEER("봉사"),
    REAL_ESTATE("부동산"),
    BUSINESS("사업"),
    STUDY("스터디"),
    TRAVEL("여행"),
    RELATIONSHIP("연애/결혼"),
    ENTERTAINMENT("영화/드라마/유튜브"),
    LANGUAGE("외국어"),
    STUDY_ABROAD("유학"),
    MEDICAL("의료"),
    FOOD("음식"),
    MUSIC("음악"),
    EXERCISE("운동"),
    SELF_DEVELOPMENT("자기개발"),
    INDEPENDENT_LIVING("자취"),
    STOCK_COIN("주식/코인"),
    HOBBY("취미생활"),
    JOB_CHANGE("취업/이직"),
    CAREER("커리어"),
    ETC("기타");

    override fun toString(): String = displayName
}

enum class Location(val displayName: String) {
    HONGDAE_HAPJEONG("홍대/합정"),
    GANGNAM("강남");

    override fun toString(): String = displayName
}

enum class Budget(val displayName: String) {
    LOW("10,000~30,000원"),
    MEDIUM("30,000~50,000원"),
    HIGH("50,000~70,000원"),
    RANGE_NONE("None");

    override fun toString(): String = displayName
}