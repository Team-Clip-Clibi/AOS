// build-logic/build.gradle.kts
plugins {
    `kotlin-dsl`
}

group = "one.thing.buildlogic"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("androidLibraryConvention") {
            id = "one.thing.android.library"
            implementationClass = "convention.AndroidLibraryConventionPlugin"
        }
        create("androidXmlConvention"){
            id = "one.thing.android.xml.library"
            implementationClass = "convention.AndroidXmlLibraryConventionPlugin"
        }
        create("hiltConvention"){
            id = "one.thing.hilt.library"
            implementationClass = "convention.HiltLibraryConventionPlugin"
        }
        create("databaseConvention"){
            id = "one.thing.database.library"
            implementationClass = "convention.DatabaseConventionPlugin"
        }
        create("designSystemConvention"){
            id = "one.thing.design.library"
            implementationClass = "convention.DesignSystemLibraryPlugin"
        }
        create("networkConvention"){
            id = "one.thing.network.library"
            implementationClass = "convention.NetworkLibraryPlugin"
        }
        create("applicationConvention"){
            id ="one.thing.android.application"
            implementationClass = "convention.ApplicationConventionPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    compileOnly("com.android.tools.build:gradle:8.6.0")
    compileOnly("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:2.1.0")
}