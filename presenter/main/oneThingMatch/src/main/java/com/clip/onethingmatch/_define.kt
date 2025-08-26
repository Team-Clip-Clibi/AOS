package com.clip.onethingmatch


const val NAV_PAY = "pay"

const val NAV_INTRO = "intro"
const val NAV_SUBJECT = "subject"
const val NAV_CATEGORY = "category"
const val NAV_LOCATION = "location"
const val NAV_DATE = "date"
const val NAV_BUDGET = "budget"
const val NAV_TMI = "tmi"
const val NAV_BEFORE_PAY = "beforePay"

enum class CATEGORY(val displayName: String, val imageId: Int = -1) {
    HEALTH("건강", R.drawable.ic_health),
    MONEY("돈", R.drawable.ic_money),
    LIFE("인생", R.drawable.ic_life),
    RELATIONSHIP("연애", R.drawable.ic_love),
    SELF_IMPROVEMENT("자기계발", R.drawable.ic_growth),
    WORK("직장", R.drawable.ic_job),
    HOBBY("취미", R.drawable.ic_hobby),
    NONE("선택안함");

    override fun toString(): String = displayName
}

enum class Location(val displayName: String) {
    HONGDAE_HAPJEONG("홍대/합정"),
    GANGNAM("강남"),
    NONE("선택안함"),;

    override fun toString(): String = displayName
}

enum class Budget(val displayName: String) {
    LOW("10,000~30,000원"),
    MEDIUM("30,000~50,000원"),
    HIGH("50,000~70,000원"),
    RANGE_NONE("None");

    override fun toString(): String = displayName
}

const val ERROR_NETWORK_ERROR ="network Error"
const val ERROR_RE_LOGIN ="reLogin"
const val ERROR_ORDER_TIME_LATE = "order time is not valid"
const val ERROR_USER_ID_NULL = "user id is null"
const val NAV_LOGIN = "Login"