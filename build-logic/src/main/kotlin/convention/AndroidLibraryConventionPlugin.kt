package convention

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.kapt")
        pluginManager.apply("com.google.dagger.hilt.android")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        extensions.getByType<LibraryExtension>().apply {
            compileSdk = 35
            defaultConfig {
                minSdk = 31
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
            }
            buildFeatures.buildConfig = true
            buildFeatures.compose = true
            composeOptions.kotlinCompilerExtensionVersion = "1.5.13"
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        extensions.getByType<KotlinAndroidProjectExtension>().apply {
            jvmToolchain(17)
        }

        dependencies {
            add("implementation", "androidx.core:core-ktx:1.15.0")
            add("implementation", "androidx.appcompat:appcompat:1.7.0")
            add("implementation", "androidx.activity:activity-compose:1.10.1")
            add("implementation", platform("androidx.compose:compose-bom:2024.04.01"))
            add("implementation", "androidx.compose.ui:ui")
            add("implementation", "androidx.compose.ui:ui-graphics")
            add("implementation", "androidx.compose.ui:ui-tooling-preview")
            add("implementation", "androidx.compose.material3:material3:1.3.2")

            add("testImplementation", "junit:junit:4.13.2")
            add("androidTestImplementation", "androidx.test.ext:junit:1.2.1")
            add("androidTestImplementation", "androidx.test.espresso:espresso-core:3.6.1")

            add("implementation", "com.google.dagger:hilt-android:2.53")
            add("kapt", "com.google.dagger:hilt-android-compiler:2.53")

            add("implementation", project(":domain"))
            add("implementation", project(":core"))
        }
    }
}