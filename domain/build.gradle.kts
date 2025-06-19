plugins {
    id("one.thing.hilt.library")
}

android {
    namespace = "com.sungil.domain"
}

dependencies {
    implementation(libs.paging.runtime)
}
