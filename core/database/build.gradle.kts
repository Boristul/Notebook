import com.boristul.buildsrc.Libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("plugin.parcelize")
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

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    implementation(project(":entity"))
    // endregion

    // region Core
    implementation(Libs.Kodein.kodein)
    // endregion

    // region AndroidX
    val roomVersion: String by project
    kapt(Libs.Room.roomCompiler)
    implementation(Libs.Room.roomKtx)
    // endregion
}
