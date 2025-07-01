plugins {
    id("one.thing.android.xml.library")
}

android {
    namespace = "com.sungil.kakao"
}

dependencies {
    implementation(libs.kakao.common)
    implementation(project(":domain"))
}
