package com.sungil.main


sealed class BottomView(val title: Int, val icon: Int = -1, val screenRoute: String) {
    data object Home : BottomView(R.string.nav_home, R.drawable.ic_home, NAV_HOME)
    data object MatchView : BottomView(R.string.nav_calendar, R.drawable.ic_calendar, MATCH)
    data object MyPage : BottomView(R.string.nav_my, R.drawable.ic_my, NAV_MY)
}

enum class MainView(val route: String) {
    MATCH_DETAIL("MatchDetail"),
    PAY_DETAIL("PayDetail"),
    REVIEW("review");
}

enum class MyMatchDestination(val route: String, val label: String) {
    MATCH_HISTORY("matchHistory", "모임 내역"),
    MATCH_NOTICE("matchNotice", "안내문");
}

enum class ReviewIcon(val image: Int, val content: Int, val buttonInt: Int, val value: String) {
    REVIEW_DISAPPOINTED(
        R.drawable.ic_disappointed,
        R.string.review_item_disappointed,
        REVIEW_DISAPPOINTED_BTN,
        REVIEW_DISAPPOINTED_VALUE
    ),
    REVIEW_SHAME(
        R.drawable.ic_shame,
        R.string.review_item_shame,
        REVIEW_SHAME_BTN,
        REVIEW_UNSATISFIED_VALUE
    ),
    REVIEW_NORMAL(
        R.drawable.ic_normal,
        R.string.review_item_normal,
        REVIEW_NORMAL_BTN,
        REVIEW_NORMAL_VALUE
    ),
    REVIEW_GOOD(R.drawable.ic_good, R.string.review_item_good, REVIEW_GOOD_BTN, REVIEW_GOOD_VALUE),
    REVIEW_BEST(R.drawable.ic_best, R.string.review_item_best, REVIEW_BEST_BTN, REVIEW_BEST_VALUE);

    companion object {
        fun fromButtonInt(buttonInt: Int): String {
            return entries.firstOrNull { it.buttonInt == buttonInt }?.value ?: "null"
        }
    }
}

enum class ReviewBadItem(val content: Int) {
    REVIEW_BAD_ITEM_BORING(R.string.review_bad_item_boring),
    REVIEW_BAD_ITEM_NEGATIVE(R.string.review_bad_item_negative),
    REVIEW_BAD_ITEM_HARD(R.string.review_bad_item_hard),
    REVIEW_BAD_ITEM_INCONVENIENCE(R.string.review_bad_item_inconvenience),
    REVIEW_BAD_ITEM_UNLIKE(R.string.review_bad_item_unlike)
}

enum class ReviewGoodItem(val content: Int) {
    REVIEW_GOOD_ITEM_FUNNY(R.string.review_good_item_funny),
    REVIEW_GOOD_ITEM_ACTIVELY(R.string.review_good_item_actively),
    REVIEW_GOOD_ITEM_SMOOTH(R.string.review_good_item_smooth),
    REVIEW_GOOD_ITEM_COMFORTABLE(R.string.review_good_item_comfortable),
    REVIEW_GOOD_ITEM_APPROPRIATE(R.string.review_good_item_appropriate)
}

enum class AllAttend(val content: Int, val value: Boolean) {
    ALL_ATTEND(R.string.review_not_attend_item_all_come, true),
    NOT_ATTEND(R.string.review_not_attend_item_not_all_come, false)
}

const val REVIEW_DISAPPOINTED_BTN = 1
const val REVIEW_SHAME_BTN = 2
const val REVIEW_NORMAL_BTN = 3
const val REVIEW_GOOD_BTN = 4
const val REVIEW_BEST_BTN = 5
const val REVIEW_SELECT_NOTHING = 0

const val REVIEW_DISAPPOINTED_VALUE = "DISAPPOINTED"
const val REVIEW_UNSATISFIED_VALUE = "UNSATISFIED"
const val REVIEW_NORMAL_VALUE = "NEUTRAL"
const val REVIEW_GOOD_VALUE = "GOOD"
const val REVIEW_BEST_VALUE = "EXCELLENT"
const val PAGE_MATCH_HISTORY = 0
const val PAGE_MATCH_NOTICE= 1

enum class MatchStatus(val route: String, val label: String) {
    MATCH_CANCEL("CANCELLED", "취소"),
    MATCH_COMPLETED("COMPLETED", "모임종료"),
    MATCH_CONFIRMED("CONFIRMED", "매칭확정"),
    MATCH_APPLIED("APPLIED" , "신청완료"),
    MATCH_NO_SHOW("NO_SHOW" ,"노쇼"),
    MATCH_WAIT_PAY("WAIT_FOR_PAY" , "결제대기");

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
    BottomView.MatchView,
    BottomView.MyPage
)

const val NAV_HOME = "home"
const val MATCH = "match"
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
const val MATCH_KEY_CANCELLED = "CANCELED"