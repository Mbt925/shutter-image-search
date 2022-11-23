@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("plugins")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ShutterImageSearch"
include(":app")
include(":networking")
include(":service")
include(":design")
