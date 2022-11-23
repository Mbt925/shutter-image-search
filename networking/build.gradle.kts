@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlinx.serializationPlugin)
}

android {
    namespace = "com.mbt925.shutterimagesearch.networking"
    compileSdk = 33

    defaultConfig {
        minSdk = 26
        buildConfigField("String", "API_TOKEN", getLocalKey("API_TOKEN"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

fun getLocalKey(key: String): String = gradleLocalProperties(rootDir).getProperty(key)

dependencies {
    api(libs.squareup.retrofit.library)
    implementation(libs.squareup.okhttp3.loggingInterceptor)
    api(libs.kotlinx.serialization.json)
    api(libs.squareup.retrofit.converter.kotlinxSerialization)
    implementation(libs.koin.core)
}
