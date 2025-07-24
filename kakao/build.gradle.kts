plugins {
    id("one.thing.android.xml.library")
}

android {
    namespace = "com.oneThing.kakao"
}

dependencies {
    implementation(libs.kakao.common)
    implementation(project(":domain"))
}
