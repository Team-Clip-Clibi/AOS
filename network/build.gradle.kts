import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.kotlin.serialization)
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { inputStream ->
        properties.load(inputStream)
    }
}
android {
    namespace = "com.sungil.network"
    compileSdk = 35

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val baseUrl: String = properties.getProperty("base_url", "")
        val phoneNumberCheckFirst: String = properties.getProperty("phone_number_check_first", "")
        val phoneNumberCheckLast: String = properties.getProperty("phone_number_check_last", "")
        val signUp: String = properties.getProperty("signUp", "")
        val nickNameCheck: String = properties.getProperty("nickNameCheck", "")
        val nameUlr: String = properties.getProperty("nameUrl", "")
        val nickNameSend: String = properties.getProperty("nickNameSendUrl", "")
        val userDetailUrl: String = properties.getProperty("userDetailUrl", "")
        val userPhoneNumber: String = properties.getProperty("userPhoneNumberUrl", "")
        val signInUrl: String = properties.getProperty("login", "")
        val userInfoUrl: String = properties.getProperty("userInfo", "")
        val fcmToken: String = properties.getProperty("fcmToken", "")
        val changeJob: String = properties.getProperty("changeJob", "")
        val updateRelationShip: String = properties.getProperty("changeRelationShip", "")
        val language: String = properties.getProperty("language", "")
        val signOut: String = properties.getProperty("signOut", "")
        val diet: String = properties.getProperty("diet", "")
        val report: String = properties.getProperty("report", "")
        val noti: String = properties.getProperty("noti", "")
        val banner: String = properties.getProperty("banner", "")
        val match: String = properties.getProperty("match", "")
        val refresh: String = properties.getProperty("refresh", "")
        val notification: String = properties.getProperty("notification", "")
        val unReadAlarm: String = properties.getProperty("unReadNotification", "")
        val readAlarm: String = properties.getProperty("readNotificaiton", "")
        val oneThingOrder: String = properties.getProperty("oneThingOreder", "")
        val payment: String = properties.getProperty("paymentUrl", "")
        val randomDuplicate: String = properties.getProperty("randomMatchDuPlicate", "")
        val randomOrder: String = properties.getProperty("randomOrder", "")
        val matchOverView: String = properties.getProperty("matchOverView", "")

        buildConfigField("String", "BASE_URL", baseUrl)
        buildConfigField("String", "PHONE_NUMBER_CHECK_FIRST", phoneNumberCheckFirst)
        buildConfigField("String", "PHONE_NUMBER_CHECK_LAST", phoneNumberCheckLast)
        buildConfigField("String", "USER_SIGNUP", signUp)
        buildConfigField("String", "NICK_NAME_URL", nickNameCheck)
        buildConfigField("String", "NICK_NAME_SEND_URL", nickNameSend)
        buildConfigField("String", "NAME_SEND_URL", nameUlr)
        buildConfigField("String", "USER_DETAIL_URL", userDetailUrl)
        buildConfigField("String", "USER_PHONE_SEND_URL", userPhoneNumber)
        buildConfigField("String", "LOGIN_URL", signInUrl)
        buildConfigField("String", "USERINFO_URL", userInfoUrl)
        buildConfigField("String", "UPDATE_FCM_TOKEN", fcmToken)
        buildConfigField("String", "CHANGE_JOB_URL", changeJob)
        buildConfigField("String", "UPDATE_RELEATION_URL", updateRelationShip)
        buildConfigField("String", "LANGUAGE_UPDATE_URL", language)
        buildConfigField("String", "SIGNOUT_URL", signOut)
        buildConfigField("String", "DIET_URL", diet)
        buildConfigField("String", "REPORT_URL", report)
        buildConfigField("String", "ANNOUNCEMENT_URL", noti)
        buildConfigField("String", "BANNER_URL", banner)
        buildConfigField("String", "MATCH_URL", match)
        buildConfigField("String", "REFRESH_URL", refresh)
        buildConfigField("String", "ONE_THING_NOTI_URL", notification)
        buildConfigField("String", "UN_READ_NOTIFY_URL", unReadAlarm)
        buildConfigField("String", "READ_NOTIFY_URL", readAlarm)
        buildConfigField("String", "ONETHING_ORDER_URL", oneThingOrder)
        buildConfigField("String", "PAYMENT_URL", payment)
        buildConfigField("String", "RANDOM_DUPLICATE_URL", randomDuplicate)
        buildConfigField("String", "RANDOM_ORDER_URL", randomOrder)
        buildConfigField("String", "MATCH_OVERVIEW_URL", matchOverView)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}
kapt {
    correctErrorTypes = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //firebase
    implementation(libs.firebase.auth)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //retrofit2
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter.kotlinx.serialization)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    //gson
    implementation(libs.google.gson)
    //firebase
    implementation(libs.firebase.crash)
}