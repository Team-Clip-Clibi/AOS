plugins {
    id("one.thing.android.library")
}

android {
    namespace = "com.clip.editprofile"
}

dependencies {
    implementation(libs.compose.nav)
    implementation(libs.jetbrains.kotlinx.serialization.json)
}
