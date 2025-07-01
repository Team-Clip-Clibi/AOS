package convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import java.util.Properties

class DatabaseConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("com.google.dagger.hilt.android")
        pluginManager.apply("org.jetbrains.kotlin.kapt")
        pluginManager.apply("com.google.devtools.ksp")
        extensions.getByType<LibraryExtension>().apply {
            namespace = "com.sungil.database"
            compileSdk = 35
            defaultConfig {
                minSdk = 31
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
                val properties = Properties()
                val localPropsFile = rootProject.file("local.properties")
                if (localPropsFile.exists()) {
                    localPropsFile.inputStream().use { properties.load(it) }
                }
                val sharedPreKey: String = properties.getProperty("shredKey", "")
                val sharedTokenKey: String = properties.getProperty("tokenKey", "")
                val signKey: String = properties.getProperty("signUpKey", "")
                val fcmKey: String = properties.getProperty("fcmKey", "")
                val notify: String = properties.getProperty("notifiyKey", "")
                val inputMatchData: String = properties.getProperty("inputMatchData", "")

                buildConfigField("String", "SHARED_KEY", sharedPreKey)
                buildConfigField("String", "TOKEN_KEY", sharedTokenKey)
                buildConfigField("String", "signUpKey", signKey)
                buildConfigField("String", "fcmKey", fcmKey)
                buildConfigField("String", "NOTIFYKEY", notify)
                buildConfigField("String", "MATCH_KEY", inputMatchData)
            }
            buildFeatures.buildConfig = true
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
        extensions.getByType<KotlinAndroidProjectExtension>().apply {
            jvmToolchain(17)
        }
        dependencies {
            "implementation"(libs.findLibrary("androidx-core-ktx").get())
            "implementation"(libs.findLibrary("hilt-android").get())
            "kapt"(libs.findLibrary("hilt-compiler").get())
            "implementation"(libs.findLibrary("junit").get())
            "implementation"(libs.findLibrary("androidx-junit").get())
            "implementation"(libs.findLibrary("androidx-espresso-core").get())
            "implementation"(libs.findLibrary("room-database-runtime").get())
            "implementation"(libs.findLibrary("room-database-ktx").get())
            "ksp"(libs.findLibrary("room-database-compiler").get())
            "implementation"(libs.findLibrary("google-gson").get())
            "implementation"(libs.findLibrary("firebase-crash").get())
        }
    }
}