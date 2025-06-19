plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.example.fcm"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    // 테스트용 의존성은 유지 가능
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    // Firebase
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.message)
}
