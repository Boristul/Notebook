package com.boristul.buildsrc

object Libs {

    const val gradle = "com.android.tools.build:gradle:4.1.3"
    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detektVersion}"

    object Versions {
        const val detektVersion = "1.16.0"
    }

    object Kotlin {
        const val kotlinVersion = "1.4.32"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
    }

    object AndroidX {
        const val safeArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.4"
    }

    object GoogleServices {
        const val googleServicesPlugin = "com.google.gms:google-services:4.3.5"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics-gradle:2.5.2"
        const val firebasePerfPlugin = "com.google.firebase:perf-plugin:1.3.5"
    }
}
