import com.boristul.buildsrc.Libs

plugins {
    id("com.android.library")
    kotlin("android")
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
    implementation(kotlin("stdlib-jdk8", Libs.Kotlin.kotlinVersion))
    // endregion

    // region Local
    implementation(project(":utils"))
    // endregion

    // region AndroidX
    implementation(Libs.AndroidX.preference)
    api(Libs.AndroidX.core)
    api(Libs.AndroidX.appCompat)
    api(Libs.AndroidX.constraintLayout)
    // endregion


    // region UI
    api(Libs.Core.swipeLayout)
    api(Libs.Core.material)
    api(Libs.Core.lottie)
    // endregion
}
