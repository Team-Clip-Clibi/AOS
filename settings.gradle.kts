pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
        maven { url = java.net.URI("https://jitpack.io") }
    }
}

rootProject.name = "oneThing"
include(":app")
include(":presenter")
include(":presenter:login")
include(":kakao")
include(":domain")
include(":database")
include(":presenter:signup")
include(":data")
include(":network")
include(":fcm")
include(":device")
include(":core")
include(":presenter:main")
include(":presenter:main:editProfile")
include(":presenter:main:report")
include(":presenter:main:low")
include(":presenter:main:alarm")
include(":presenter:main:oneThingMatch")
include(":billing")
include(":presenter:pay_finish")
