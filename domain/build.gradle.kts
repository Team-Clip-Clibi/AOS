plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.clip.domain"
}

dependencies {
    implementation(libs.paging.runtime)
}
