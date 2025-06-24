plugins {
    id("one.thing.android.library")
}

android {
    namespace = "com.oneThing.guide"

}

dependencies {
    implementation(libs.material)
    implementation(libs.compose.nav)
    implementation(libs.androidx.compose.material)
    implementation(libs.jetbrains.kotlinx.serialization.json)
}