plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.example.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":database"))
    implementation(project(":network"))
    implementation(project(":fcm"))
    implementation(project(":device"))
    // Retrofit, OkHttp, Serialization
    implementation(libs.squareup.retrofit2.retrofit)
    implementation(libs.squareup.retrofit2.converter.kotlinx.serialization)
    implementation(libs.squareup.okhttp3.logging.interceptor)
    implementation(libs.jetbrains.kotlinx.serialization.json)
    // Paging
    implementation(libs.paging.runtime)
}