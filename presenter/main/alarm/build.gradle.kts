plugins {
    id("one.thing.android.library")
}

android {
    namespace = "com.oneThing.alarm"
}

dependencies {
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)
}
