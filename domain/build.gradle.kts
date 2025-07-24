plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.oneThing.domain"
}

dependencies {
    implementation(libs.paging.runtime)
}
