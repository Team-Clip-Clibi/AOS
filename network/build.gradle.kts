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
        val phoneNumberCheckFirst : String = properties.getProperty("phone_number_check_first" , "")
        val phoneNumberCheckLast : String = properties.getProperty("phone_number_check_last" , "")
        buildConfigField("String", "BASE_URL", baseUrl)
        buildConfigField("String" , "PHONE_NUMBER_CHECK_FIRST" , phoneNumberCheckFirst)
        buildConfigField("String" , "PHONE_NUMBER_CHECK_LAST", phoneNumberCheckLast)
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

}