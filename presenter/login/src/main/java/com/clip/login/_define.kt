package com.clip.login

//ERROR CODE
const val ERROR_FCM_TOKEN = "Error to Fail Fcm"
const val ERROR_NOTIFY_SAVE = "Error save Notify"
const val ERROR_RE_LOGIN = "reLogin"
const val ERROR_NETWORK = "network error"
const val ERROR_NOT_SIGNUP = "Not SignUp"
const val UPDATE_APP = "Update App"

//Banner
const val BANNER = "LOGIN"

//NAV
const val NAV_SPLASH = "splash"
const val NAV_LOGIN = "Login"
const val NAV_MAIN = "Main"
const val NAV_PREVIEW = "preview"
const val NAV_SIGNUP = "SignUp"

enum class CATEGORY {
    CONTENT_ONE_THING,
    CONTENT_RANDOM
}
sealed class BottomView(val title: Int, val icon: Int = -1, val screenRoute: String) {
    data object Home : BottomView(R.string.nav_home, R.drawable.ic_home, NAV_HOME)
    data object MatchView : BottomView(R.string.nav_match, R.drawable.ic_calendar, MATCH)
    data object MyPage : BottomView(R.string.nav_my, R.drawable.ic_my, NAV_MY)
}

const val NAV_HOME = "home"
const val MATCH = "match"
const val NAV_MY = "my"

val bottomNavItems = listOf(
    BottomView.Home,
    BottomView.MatchView,
    BottomView.MyPage
)
