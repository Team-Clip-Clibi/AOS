import java.util.Properties

plugins {
    id("one.thing.android.library")
}

val properties = Properties()
val localPropertiesFile = project.rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localPropertiesFile.inputStream().use { inputStream ->
        properties.load(inputStream)
    }
}

android {
    namespace = "com.sungil.login"

    defaultConfig {
        val notifyPermission: String = properties.getProperty("notifyPermssion", "")
        buildConfigField("String", "NOTIFY_PERMISSION_KEY", notifyPermission)
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttps)
    implementation(libs.firebase.crash)
    implementation(libs.coil.svg)
    implementation(libs.compose.nav)
    implementation(libs.kakao.common)
}
