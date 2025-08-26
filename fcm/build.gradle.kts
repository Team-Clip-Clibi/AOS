plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.clip.fcm"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.message)
}
