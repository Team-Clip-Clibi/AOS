package com.oneThing.random.component

const val NAV_RANDOM_MATCH_INTRO = "randomMatchIntro"
const val NAV_RANDOM_MATCH_DUPLICATE = "randomMatchDuplicate"
const val NAV_RANDOM_LOCATION = "randomLocation"
const val NAV_RANDOM_TMI = "randomTmi"

const val ERROR_NETWORK_ERROR = "network Error"
const val ERROR_RE_LOGIN = "reLogin"

const val NEXT_DATE_EMPTY = ""

enum class Location(val displayName: String) {
    HONGDAE_HAPJEONG("홍대/합정"),
    GANGNAM("강남"),
    NONE("선택안함"),;

    override fun toString(): String = displayName
}

