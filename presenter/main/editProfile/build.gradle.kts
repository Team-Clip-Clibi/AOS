plugins {
    id("one.thing.android.library")
}

android {
    namespace = "com.oneThing.editprofile"
}

dependencies {
    implementation(libs.compose.nav)
    implementation(libs.jetbrains.kotlinx.serialization.json)
}
