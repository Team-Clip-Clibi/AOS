import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}
val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { inputStream ->
        properties.load(inputStream)
    }
}

android {
    namespace = "com.sungil.onething"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sungil.onething"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val kakaoNativeKey: String = properties.getProperty("kakao_native_key", "")

        manifestPlaceholders["NATIVE_APP_KEY"] = kakaoNativeKey
        buildConfigField("String", "KAKAO_NATIVE_KEY", kakaoNativeKey)
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    //KAKAO
    implementation(libs.kakao.common)
    //firebase
    implementation(libs.firebase.auth)
    //Module
    implementation(project(":presenter"))
    implementation(project(":presenter:login"))
    implementation(project(":presenter:signup"))
    implementation(project(":presenter:main"))
    implementation(project(":presenter:main:editProfile"))
    implementation(project(":presenter:main:report"))
    implementation(project(":presenter:main:low"))
    implementation(project(":presenter:main:alarm"))
    implementation(project(":kakao"))
    implementation(project(":domain"))
    implementation(project(":database"))
    implementation(project(":data"))
    implementation(project(":network"))
    implementation(project(":fcm"))
    implementation(project(":device"))
    implementation(project(":core"))
    //roomDatabase
    implementation(libs.room.database.runtime)
    implementation(libs.room.database.ktx)
    kapt(libs.room.database.compiler)
}