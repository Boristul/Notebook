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
    // region Navigation
    implementation(Libs.Navigation.navigationFragment)
    // endregion

    // region AndroidX
    implementation(Libs.AndroidX.lifecycleLivedata)
    implementation(Libs.AndroidX.lifecycleRuntime)
    // endregion

    // region Core
    implementation(Libs.Core.jodaTime)
    // endregion
}
