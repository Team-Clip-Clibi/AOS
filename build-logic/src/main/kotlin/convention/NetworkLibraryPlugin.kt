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

class NetworkLibraryPlugin : Plugin<Project> {
    override fun apply(project: Project) = with(project) {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
        pluginManager.apply("com.android.library")
        pluginManager.apply("org.jetbrains.kotlin.android")
        pluginManager.apply("org.jetbrains.kotlin.kapt")
        pluginManager.apply("com.google.dagger.hilt.android")
        extensions.getByType<LibraryExtension>().apply {
            namespace = "com.sungil.network"
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
                val baseUrl: String = properties.getProperty("base_url", "")
                val phoneNumberCheckFirst: String =
                    properties.getProperty("phone_number_check_first", "")
                val phoneNumberCheckLast: String =
                    properties.getProperty("phone_number_check_last", "")
                val signUp: String = properties.getProperty("signUp", "")
                val nickNameCheck: String = properties.getProperty("nickNameCheck", "")
                val nameUlr: String = properties.getProperty("nameUrl", "")
                val nickNameSend: String = properties.getProperty("nickNameSendUrl", "")
                val userDetailUrl: String = properties.getProperty("userDetailUrl", "")
                val userPhoneNumber: String = properties.getProperty("userPhoneNumberUrl", "")
                val signInUrl: String = properties.getProperty("login", "")
                val userInfoUrl: String = properties.getProperty("userInfo", "")
                val fcmToken: String = properties.getProperty("fcmToken", "")
                val changeJob: String = properties.getProperty("changeJob", "")
                val updateRelationShip: String = properties.getProperty("changeRelationShip", "")
                val language: String = properties.getProperty("language", "")
                val signOut: String = properties.getProperty("signOut", "")
                val diet: String = properties.getProperty("diet", "")
                val report: String = properties.getProperty("report", "")
                val noti: String = properties.getProperty("noti", "")
                val banner: String = properties.getProperty("banner", "")
                val match: String = properties.getProperty("match", "")
                val refresh: String = properties.getProperty("refresh", "")
                val notification: String = properties.getProperty("notification", "")
                val unReadAlarm: String = properties.getProperty("unReadNotification", "")
                val readAlarm: String = properties.getProperty("readNotificaiton", "")
                val oneThingOrder: String = properties.getProperty("oneThingOreder", "")
                val payment: String = properties.getProperty("paymentUrl", "")
                val randomDuplicate: String = properties.getProperty("randomMatchDuPlicate", "")
                val randomOrder: String = properties.getProperty("randomOrder", "")
                val matchOverView: String = properties.getProperty("matchOverView", "")
                val notifyUpdate: String = properties.getProperty("notifyUpdate", "")
                val matchList: String = properties.getProperty("matchings", "")
                val matchNotice: String = properties.getProperty("matchNotice", "")
                val review: String = properties.getProperty("review", "")
                val matchProgress: String = properties.getProperty("matchProgress", "")
                val progress: String = properties.getProperty("progress", "")
                val versionCheck: String = properties.getProperty("versionCheck", "")
                val postPone: String = properties.getProperty("postpone", "")

                buildConfigField("String", "BASE_URL", baseUrl)
                buildConfigField("String", "PHONE_NUMBER_CHECK_FIRST", phoneNumberCheckFirst)
                buildConfigField("String", "PHONE_NUMBER_CHECK_LAST", phoneNumberCheckLast)
                buildConfigField("String", "USER_SIGNUP", signUp)
                buildConfigField("String", "NICK_NAME_URL", nickNameCheck)
                buildConfigField("String", "NICK_NAME_SEND_URL", nickNameSend)
                buildConfigField("String", "NAME_SEND_URL", nameUlr)
                buildConfigField("String", "USER_DETAIL_URL", userDetailUrl)
                buildConfigField("String", "USER_PHONE_SEND_URL", userPhoneNumber)
                buildConfigField("String", "LOGIN_URL", signInUrl)
                buildConfigField("String", "USERINFO_URL", userInfoUrl)
                buildConfigField("String", "UPDATE_FCM_TOKEN", fcmToken)
                buildConfigField("String", "CHANGE_JOB_URL", changeJob)
                buildConfigField("String", "UPDATE_RELEATION_URL", updateRelationShip)
                buildConfigField("String", "LANGUAGE_UPDATE_URL", language)
                buildConfigField("String", "SIGNOUT_URL", signOut)
                buildConfigField("String", "DIET_URL", diet)
                buildConfigField("String", "REPORT_URL", report)
                buildConfigField("String", "ANNOUNCEMENT_URL", noti)
                buildConfigField("String", "BANNER_URL", banner)
                buildConfigField("String", "MATCH_URL", match)
                buildConfigField("String", "REFRESH_URL", refresh)
                buildConfigField("String", "ONE_THING_NOTI_URL", notification)
                buildConfigField("String", "UN_READ_NOTIFY_URL", unReadAlarm)
                buildConfigField("String", "READ_NOTIFY_URL", readAlarm)
                buildConfigField("String", "ONETHING_ORDER_URL", oneThingOrder)
                buildConfigField("String", "PAYMENT_URL", payment)
                buildConfigField("String", "RANDOM_DUPLICATE_URL", randomDuplicate)
                buildConfigField("String", "RANDOM_ORDER_URL", randomOrder)
                buildConfigField("String", "MATCH_OVERVIEW_URL", matchOverView)
                buildConfigField("String", "NOTIFY_UPDATE_URL", notifyUpdate)
                buildConfigField("String", "MATCH_ING_URL", matchList)
                buildConfigField("String", "MATCH_NOTICE_URL", matchNotice)
                buildConfigField("String", "MATCH_REVIEW_URL", review)
                buildConfigField("String", "MATCH_PROGRESS_MATCH_URL", matchProgress)
                buildConfigField("String", "MATCH_PROGRESS_PROGRESS_URL", progress)
                buildConfigField("String", "VERSION_CHECK_URL", versionCheck)
                buildConfigField("String", "REVIEW_POSTPONE_URL", postPone)

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
            "implementation"(libs.findLibrary("androidx-appcompat").get())
            "implementation"(libs.findLibrary("material").get())
            "testImplementation"(libs.findLibrary("junit").get())
            "testImplementation"(libs.findLibrary("androidx-junit").get())
            "implementation"(libs.findLibrary("androidx-espresso-core").get())
            "implementation"(libs.findLibrary("firebase-auth").get())
            "implementation"(libs.findLibrary("hilt-android").get())
            "kapt"(libs.findLibrary("hilt-compiler").get())
            "implementation"(libs.findLibrary("squareup-retrofit2-retrofit").get())
            "implementation"(
                libs.findLibrary("squareup-retrofit2-converter-kotlinx-serialization").get()
            )
            "implementation"(libs.findLibrary("squareup-okhttp3-logging-interceptor").get())
            "implementation"(libs.findLibrary("jetbrains-kotlinx-serialization-json").get())
            "implementation"(libs.findLibrary("google-gson").get())
            "implementation"(libs.findLibrary("firebase-crash").get())
        }
    }
}