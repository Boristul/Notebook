plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        versionCode = 1
        versionName = "0.0.0"

        minSdkVersion(21)
        targetSdkVersion(30)

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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
    }
}

dependencies {
    // region Kotlin
    val kotlinVersion: String by project
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    // endregion

    // region Local
    implementation(project(":utils"))
    // endregion

    // region AndroidX
    implementation("androidx.preference:preference:1.1.1")
    api("androidx.core:core-ktx:1.3.2")
    api("androidx.appcompat:appcompat:1.2.0")
    api("androidx.constraintlayout:constraintlayout:2.0.4")
    // endregion


    // region UI
    api("com.daimajia.swipelayout:library:1.2.0@aar")
    api("com.google.android.material:material:1.3.0-beta01")
    implementation("net.cachapa.expandablelayout:expandablelayout:2.9.2")
    api("com.airbnb.android:lottie:3.5.0")
    // endregion
}
