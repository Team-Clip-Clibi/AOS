plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.clip.device"
}

dependencies {

    implementation(libs.androidx.work.runtime.ktx)
}
