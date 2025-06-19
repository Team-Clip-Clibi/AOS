plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.example.fcm"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.message)
}
