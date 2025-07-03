package com.sungil.main



sealed class BottomView(val title: Int, val icon: Int = -1, val screenRoute: String) {
    data object Home : BottomView(R.string.nav_home, R.drawable.ic_home, NAV_HOME)
    data object Calendar : BottomView(R.string.nav_calendar, R.drawable.ic_calendar, NAV_CALENDAR)
    data object MyPage : BottomView(R.string.nav_my, R.drawable.ic_my, NAV_MY)
}

enum class MainView(val route : String){
    MATCH_DETAIL("MatchDetail"),
    PAY_DETAIL("PayDetail"),
}
enum class MyMatchDestination(val route: String, val label: String) {
    MATCH_HISTORY("matchHistory", "모임 내역"),
    MATCH_NOTICE("matchNotice", "안내문");
}

enum class MatchStatus(val route: String, val label: String) {
    MATCH_CANCEL("CANCELLED", "모임취소"),
    MATCH_COMPLETED("COMPLETED", "신청완료"),
    MATCH_CONFIRMED("CONFIRMED", "모임확정");

    companion object {
        fun fromRoute(route: String): MatchStatus =
            entries.find { it.route == route } ?: MATCH_CANCEL
    }
}

enum class MatchType(val route: String, val matchType: String) {
    RANDOM("RANDOM", "랜덤모임"), ONE_THING("ONE_THING", "원띵모임");

    companion object {
        fun fromRoute(route: String?): MatchType =
            entries.find { it.route == route } ?: RANDOM
    }
}

enum class LateType(val label: String, val minutes: Int) {
    LATE_10("10분", 10),
    LATE_20("20분", 20),
    LATE_30("30분 이상", 30);

    companion object {
        private fun fromLabel(label: String): LateType? {
            return entries.firstOrNull { it.label == label }
        }

        fun extractMinutes(label: String): Int? {
            return fromLabel(label)?.minutes
        }
    }
}

enum class CATEGORY(val displayName: String) {
    HEALTH("건강"),
    MONEY("돈"),
    LIFE("인생"),
    RELATIONSHIP("연애"),
    SELF_IMPROVEMENT("자기계발"),
    WORK("직장"),
    HOBBY("취미"),
    NONE("선택안함");

    companion object {
        fun fromRoute(route: String?): CATEGORY =
            CATEGORY.entries.find { it.displayName == route } ?: NONE
    }
}

val bottomNavItems = listOf(
    BottomView.Home,
    BottomView.Calendar,
    BottomView.MyPage
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