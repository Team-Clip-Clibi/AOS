package com.oneThing.random.component

const val NAV_RANDOM_MATCH_INTRO = "randomMatchIntro"
const val NAV_RANDOM_MATCH_DUPLICATE = "randomMatchDuplicate"
const val NAV_RANDOM_LOCATION = "randomLocation"
const val NAV_RANDOM_TMI = "randomTmi"
const val NAV_RANDOM_TOPIC = "randomTopic"
const val NAV_RANDOM_BEFORE_PAY = "randomBeforePay"
const val NAV_HOME = "Main"
const val NAV_LOGIN = "Login"
const val NAV_PAY = "pay"

const val ERROR_NETWORK_ERROR = "network Error"
const val ERROR_RE_LOGIN = "reLogin"
const val ERROR_RANDOM_MATCH_FAIL = "Random Match Error"
const val ERROR_TOSS_NOT_INSTALL = "not install toss"

const val NEXT_DATE_EMPTY = ""

enum class Location(val displayName: String) {
    HONGDAE_HAPJEONG("홍대/합정"),
    GANGNAM("강남"),
    NONE("선택안함"),;

    override fun toString(): String = displayName
}

