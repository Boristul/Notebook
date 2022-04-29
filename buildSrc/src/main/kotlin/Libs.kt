package com.boristul.libs

object Libs {

    const val gradle = "com.android.tools.build:gradle:7.1.3"
    const val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-formatting:${Versions.detektVersion}"

    object Versions {
        const val detektVersion = "1.18.1"
        const val kotlinVersion = "1.6.21"
    }

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val serialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlinVersion}"
    }

    object Local {
        const val uiKit = ":uikit"
        const val repository = ":core:repository"
        const val database = ":core:database"
        const val utils = ":utils"
        const val entity = ":entity"
    }

    object AndroidX {
        private const val lifecycleVersion = "2.4.1"

        const val core = "androidx.core:core-ktx:1.7.0"

        const val appCompat = "androidx.appcompat:appcompat:1.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleLivedata = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val preference = "androidx.preference:preference:1.2.0"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.1.0-beta01"
    }

    object GoogleServices {
        const val googleServicesPlugin = "com.google.gms:google-services:4.3.10"
        const val firebaseCrashlyticsPlugin = "com.google.firebase:firebase-crashlytics-gradle:2.8.1"
        const val firebasePerfPlugin = "com.google.firebase:perf-plugin:1.4.1"

        const val firebaseBom = "com.google.firebase:firebase-bom:26.1.0"
        val googleServicesDeps = listOf(
            "com.google.firebase:firebase-analytics-ktx",
            "com.google.firebase:firebase-crashlytics-ktx",
            "com.google.firebase:firebase-perf-ktx",
            "com.google.firebase:firebase-inappmessaging-display",
            "com.google.firebase:firebase-messaging-ktx",
            "com.google.android.gms:play-services-auth:19.0.0",
            "com.google.api-client:google-api-client-android:1.26.0",
            "com.google.http-client:google-http-client-gson:1.26.0",
            "com.google.apis:google-api-services-drive:v3-rev136-1.25.0"
        )
    }

    object Core {
        const val jodaTime = "joda-time:joda-time:2.10.14"
        const val lottie = "com.airbnb.android:lottie:3.5.0"
        const val material = "com.google.android.material:material:1.7.0-alpha01"
        const val swipeLayout = "com.daimajia.swipelayout:library:1.2.0@aar"
        const val viewBinding = "com.android.databinding:viewbinding:7.1.3"
    }

    object Hilt {
        private const val hiltVersion = "2.41"
        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
    }

    object Navigation {
        private const val navigationVersion = "2.5.0-beta01"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationArgsPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2"
    }

    object Room {
        private const val roomVersion = "2.4.2"

        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }
}
