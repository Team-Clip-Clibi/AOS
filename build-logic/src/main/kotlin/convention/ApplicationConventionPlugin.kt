package convention


import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import java.util.Properties

class ApplicationConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        pluginManager.apply("com.android.application")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.kapt")
        pluginManager.apply("com.google.dagger.hilt.android")
        pluginManager.apply("com.google.gms.google-services")
        pluginManager.apply("com.google.firebase.crashlytics")
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")
        pluginManager.apply("com.google.devtools.ksp")

        extensions.getByType<BaseAppModuleExtension>().apply {
            namespace = "com.sungil.onething"
            compileSdk = 35

            defaultConfig.apply {
                applicationId = "com.sungil.onething"
                minSdk = 31
                targetSdk = 35
                versionCode = 1
                versionName = "1.0.10"
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                vectorDrawables.useSupportLibrary = true

                val properties = Properties()
                val localProperties = rootProject.file("local.properties")
                if (localProperties.exists()) {
                    localProperties.inputStream().use { properties.load(it) }
                }

                val kakaoNativeKey: String = properties.getProperty("kakao_native_key", "")
                manifestPlaceholders["NATIVE_APP_KEY"] = kakaoNativeKey
                buildConfigField("String", "KAKAO_NATIVE_KEY", kakaoNativeKey)
            }

            buildTypes.getByName("release").apply {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            buildFeatures.apply {
                compose = true
                buildConfig = true
            }

            composeOptions.kotlinCompilerExtensionVersion = "1.5.13"

            packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        extensions.getByType<KotlinAndroidProjectExtension>().apply {
            jvmToolchain(17)
        }

        dependencies {
            // androidx compose
            "implementation"(libs.findLibrary("androidx-core-ktx").get())
            "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
            "implementation"(libs.findLibrary("androidx-activity-compose").get())
            "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
            "implementation"(libs.findLibrary("androidx-ui").get())
            "implementation"(libs.findLibrary("androidx-ui-graphics").get())
            "implementation"(libs.findLibrary("androidx-ui-tooling-preview").get())
            "implementation"(libs.findLibrary("androidx-material3").get())
            "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
            "debugImplementation"(libs.findLibrary("androidx-ui-test-manifest").get())
            "androidTestImplementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
            "androidTestImplementation"(libs.findLibrary("androidx-ui-test-junit4").get())

            // test
            "testImplementation"(libs.findLibrary("junit").get())
            "androidTestImplementation"(libs.findLibrary("androidx-junit").get())
            "androidTestImplementation"(libs.findLibrary("androidx-espresso-core").get())
            "debugImplementation"(libs.findLibrary("androidx-ui-tooling").get())
            "debugImplementation"(libs.findLibrary("androidx-ui-test-manifest").get())
            // hilt
            "implementation"(libs.findLibrary("hilt-android").get())
            "kapt"(libs.findLibrary("hilt-compiler").get())

            // kakao
            "implementation"(libs.findLibrary("kakao-common").get())

            // firebase
            "implementation"(libs.findLibrary("firebase-auth").get())
            "implementation"(libs.findLibrary("firebase-crash").get())

            // room
            "implementation"(libs.findLibrary("room-database-runtime").get())
            "implementation"(libs.findLibrary("room-database-ktx").get())
            "ksp"(libs.findLibrary("room-database-compiler").get())

            // pay
            "implementation"(libs.findLibrary("pay-api").get())

            // modules
            listOf(
                ":presenter",
                ":presenter:login",
                ":presenter:signup",
                ":presenter:main",
                ":presenter:main:editProfile",
                ":presenter:main:report",
                ":presenter:main:low",
                ":presenter:main:alarm",
                ":presenter:main:oneThingMatch",
                ":presenter:main:first_match",
                ":presenter:main:random",
                ":presenter:pay_finish",
                ":presenter:main:guide",
                ":kakao",
                ":domain",
                ":database",
                ":data",
                ":network",
                ":fcm",
                ":device",
                ":core",
                ":billing",
            ).forEach {
                add("implementation", project(it))
            }
        }
    }
}
