plugins {
    id("one.thing.android.library")
}

android {
    namespace = "com.oneThing.report"
}

dependencies {
    implementation(libs.compose.nav)
}
