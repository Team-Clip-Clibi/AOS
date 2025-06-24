package com.sungil.main


sealed class Screen(val title: Int, val icon: Int, val screenRoute: String) {
    data object Home : Screen(R.string.nav_home, R.drawable.ic_home, NAV_HOME)
    data object Calendar : Screen(R.string.nav_calendar, R.drawable.ic_calendar, NAV_CALENDAR)
    data object MyPage : Screen(R.string.nav_my, R.drawable.ic_my, NAV_MY)
    data object Guide : Screen(R.string.nav_guide, R.drawable.ic_pin, NAV_GUIDE)
}

enum class MyMatchDestination(val route: String, val label: String) {
    MATCH_HISTORY("matchHistory", "모임 내역"),
    MATCH_NOTICE("matchNotice", "안내문");

    companion object {
        fun fromRoute(route: String?): MyMatchDestination =
            entries.find { it.route == route } ?: MATCH_HISTORY
    }
}
enum class TimeLine {
    FIRST, SECOND, THIRD, LAST
}
val bottomNavItems = listOf(
    Screen.Home,
    Screen.Calendar,
    Screen.MyPage
)

const val NAV_HOME = "home"
const val NAV_CALENDAR = "calendar"
const val NAV_MY = "my"
const val NAV_GUIDE = "guide"
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

const val MATCH_DATA_EMPTY = 0

const val MATCH_ALL = 0
const val MATCH_APPLY = 1
const val MATCH_CONFIRM = 2
const val MATCH_COMPLETE = 3
const val MATCH_CANCEL = 4

const val MATCH_KEY_ALL = ""
const val MATCH_KEY_APPLIED = "APPLIED"
const val MATCH_KEY_CONFIRMED = "CONFIRMED"
const val MATCH_KEY_COMPLETED = "COMPLETED"
const val MATCH_KEY_CANCELLED = "CANCELLED"