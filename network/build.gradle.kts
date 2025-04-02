import java.util.Properties
import kotlin.math.sign

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
}