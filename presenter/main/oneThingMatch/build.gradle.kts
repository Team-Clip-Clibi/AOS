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
    namespace = "com.sungil.onethingmatch"

    defaultConfig {
        val userIdKey: String = properties.getProperty("userIdKey", "")
        val orderKey: String = properties.getProperty("orderKey", "")
        val amountKey: String = properties.getProperty("amountKey", "")
        val oneThing: String = properties.getProperty("oneThing", "")
        val matchKey: String = properties.getProperty("matchKey", "")

        buildConfigField("String", "KEY_ORDER", orderKey)
        buildConfigField("String", "KEY_USER", userIdKey)
        buildConfigField("String", "KEY_AMOUNT", amountKey)
        buildConfigField("String", "MATCH_INFO", oneThing)
        buildConfigField("String", "KEY_MATCH", matchKey)
    }
}

dependencies {
    implementation(libs.compose.nav)
}
