import com.boristul.libs.Config
import com.boristul.libs.Libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.parcelize")
    kotlin("kapt")
}

kapt {
    correctErrorTypes = true
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = 21
        targetSdk = Config.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(Libs.Local.entity))
    // endregion

    // region Core
    implementation(Libs.Hilt.hiltAndroid)
    kapt(Libs.Hilt.hiltCompiler)
    implementation(Libs.Core.jodaTime)
    // endregion

    // region AndroidX
    implementation(Libs.Room.roomKtx)
    kapt(Libs.Room.roomCompiler)
    // endregion
}
