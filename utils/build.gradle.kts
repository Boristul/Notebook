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
    val kotlinVersion: String by project
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    // endregion

    // region Navigation
    val navigationVersion: String by project
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    // endregion

    // region AndroidX
    val lifecycleVersion: String by project
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // endregion

    // region Core
    implementation("joda-time:joda-time:2.10.10")
    // endregion
}
