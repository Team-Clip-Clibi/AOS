import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { inputStream ->
        properties.load(inputStream)
    }
}

android {
    namespace = "com.sungil.billing"
    compileSdk = 35

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        val userIdKey: String = properties.getProperty("userIdKey", "")
        val orderKey: String = properties.getProperty("orderKey", "")
        val amountKey: String = properties.getProperty("amountKey", "")
        val matchKey: String = properties.getProperty("matchKey", "")

        buildConfigField("String", "KEY_ORDER", orderKey)
        buildConfigField("String", "KEY_USER", userIdKey)
        buildConfigField("String", "KEY_AMOUNT", amountKey)
        buildConfigField("String", "KEY_MATCH", matchKey)
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    //payApi
    implementation(libs.pay.api)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //project
    implementation(project(":domain"))
    implementation(project(":core"))
}