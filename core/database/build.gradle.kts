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
    val compileSdkVersion: String by project
    val targetSdkVersion: String by project

    compileSdk = compileSdkVersion.toInt()

    defaultConfig {
        minSdk = 21
        targetSdk = targetSdkVersion.toInt()

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
    implementation(project(":entity"))
    // endregion

    // region Core
    val hiltVersion: String by project
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // endregion

    // region AndroidX
    val roomVersion: String by project
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    // endregion
}
