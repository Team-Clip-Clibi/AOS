import java.util.Properties

plugins {
    id("one.thing.android.library")
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
    namespace = "com.sungil.main"

    defaultConfig {
        val matchKey: String = properties.getProperty("matchKey", "")
        val oneThing: String = properties.getProperty("oneThing", "")
        val random: String = properties.getProperty("random", "")

        buildConfigField("String", "ONE_THING", oneThing)
        buildConfigField("String", "RANDOM", random)
        buildConfigField("String", "KEY_MATCH", matchKey)
    }
}

dependencies {
    implementation(libs.compose.nav)
    implementation(libs.androidx.compose.material)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
    implementation(libs.coil.svg)
    implementation(libs.coil.compose)
    implementation(libs.coil.okhttps)
    implementation(project(":domain"))
    implementation(project(":core"))
    implementation(project(":presenter:main:editProfile"))
    implementation(project(":presenter:main:report"))
    implementation(project(":presenter:main:alarm"))
    implementation(project(":presenter:main:oneThingMatch"))
}
