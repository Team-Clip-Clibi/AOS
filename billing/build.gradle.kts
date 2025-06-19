import java.util.Properties

plugins {
    id("one.thing.android.xml.library")
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

    defaultConfig {
        val userIdKey: String = properties.getProperty("userIdKey", "")
        val orderKey: String = properties.getProperty("orderKey", "")
        val amountKey: String = properties.getProperty("amountKey", "")
        val matchKey: String = properties.getProperty("matchKey", "")

        buildConfigField("String", "KEY_ORDER", orderKey)
        buildConfigField("String", "KEY_USER", userIdKey)
        buildConfigField("String", "KEY_AMOUNT", amountKey)
        buildConfigField("String", "KEY_MATCH", matchKey)
    }
}

dependencies {
    implementation(libs.pay.api)
    implementation(project(":domain"))
    implementation(project(":core"))
}
