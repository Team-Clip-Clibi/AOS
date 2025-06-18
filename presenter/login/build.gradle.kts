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
    // 필요한 경우 추가 개별 의존성만 작성
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(libs.glide.compose)
    implementation("io.coil-kt.coil3:coil-compose:3.2.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.2.0")
    implementation(libs.firebase.crash)
    implementation(libs.compose.nav)
}
