package com.sungil.main


sealed class Screen(val title: Int, val icon: Int, val screenRoute: String) {
    data object Home : Screen(R.string.nav_home, R.drawable.ic_home, NAV_HOME)
    data object Calendar : Screen(R.string.nav_calendar, R.drawable.ic_calendar, NAV_CALENDAR)
    data object MyPage : Screen(R.string.nav_my, R.drawable.ic_my, NAV_MY)
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Calendar,
    Screen.MyPage
)

const val NAV_HOME = "home"
const val NAV_CALENDAR = "calendar"
const val NAV_MY = "my"
const val NAV_EDIT_PROFILE = "MainEditProfile"
const val NAV_REPORT = "report"
const val NAV_LOW = "low"
const val NAV_ALARM = "alarm"
const val NAV_ONE_THING = "oneThing"
const val NAV_FIRST_MATCH = "first_match"
const val NAV_LOGIN = "Login"
const val NAV_RANDOM_MATCH = "RandomMatch"

const val ERROR_RE_LOGIN = "reLogin"
const val ERROR_SAVE_ERROR = "save error"
const val ERROR_NETWORK_ERROR = "network error"

const val CONTENT_NOTICE = "NOTICE"
const val CONTENT_ARTICLE = "ARTICLE"