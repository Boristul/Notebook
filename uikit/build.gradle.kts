plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    val compileSdkVersion: String by project
    val targetSdkVersion: String by project

    compileSdk = compileSdkVersion.toInt()

    defaultConfig {
        minSdk = 21
        targetSdk = targetSdkVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

dependencies {
    // region Local
    implementation(project(":utils"))
    // endregion

    // region AndroidX
    implementation("androidx.preference:preference:1.2.0")
    api("androidx.core:core-ktx:1.7.0")
    api("androidx.appcompat:appcompat:1.4.1")
    api("androidx.constraintlayout:constraintlayout:2.1.3")
    // endregion


    // region UI
    api("com.google.android.material:material:1.7.0-alpha01")
    api("com.daimajia.swipelayout:library:1.2.0@aar")
    api("com.airbnb.android:lottie:3.5.0")
    // endregion
}
