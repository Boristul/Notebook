import com.boristul.libs.Config
import com.boristul.libs.Libs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = 21
        targetSdk = Config.targetSdk

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
    implementation(project(Libs.Local.utils))
    // endregion

    // region AndroidX
    implementation(Libs.AndroidX.preference)
    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    api(Libs.AndroidX.constraintLayout)
    // endregion


    // region UI
    api(Libs.Core.material)
    api(Libs.Core.swipeLayout)
    api(Libs.Core.lottie)
    // endregion
}
