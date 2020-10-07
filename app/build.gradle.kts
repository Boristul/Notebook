plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.boristul.notebook"
        versionCode = 1
        versionName = "0.0.0"

        minSdkVersion(21)
        targetSdkVersion(30)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("signing/debug.jks")
            storePassword = "debugdebug"
            keyAlias = "debug"
            keyPassword = "debugdebug"
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = checkNotNull(signingConfigs.findByName("debug"))
        }
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

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }

    configurations {
        all {
            exclude(module = "httpclient")
        }
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
    implementation(project(":uikit"))
    implementation(project(":core:repository"))
    implementation(project(":utils"))
    implementation(project(":entity"))
    // endregion

    // region Navigation
    val navigationVersion: String by project
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.preference:preference:1.1.1")
    // endregion

    // region Google services
    implementation("com.google.firebase:firebase-analytics:17.6.0")
    implementation("com.google.android.gms:play-services-auth:18.1.0")
    implementation("com.google.api-client:google-api-client-android:1.26.0")
    implementation("com.google.http-client:google-http-client-gson:1.26.0")
    implementation("com.google.apis:google-api-services-drive:v3-rev136-1.25.0")
    // endregion

    // region Core
    val kodeinVersion: String by project
    implementation("org.kodein.di:kodein-di-jvm:$kodeinVersion")
    implementation("org.kodein.di:kodein-di-framework-android-x:$kodeinVersion")
    // endregion

    // region androidX
    implementation("androidx.core:core-ktx:1.3.2")
    // endregion
}
