import java.util.Properties

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    kotlin("android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

hilt {
    enableAggregatingTask = true
}

kapt {
    correctErrorTypes = true
}

android {
    val compileSdkVersion: String by project
    val targetSdkVersion: String by project

    compileSdk = compileSdkVersion.toInt()

    defaultConfig {
        applicationId = "com.boristul.notebook"
        versionCode = 1
        versionName = "0.0.0"

        minSdk = 21
        targetSdk = targetSdkVersion.toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures.viewBinding = true

    signingConfigs {
        val localProperties = File("${rootDir.path}/local.properties").run {
            if (exists()) Properties().apply { load(inputStream()) } else null
        }
        val environment = System.getenv()
        fun get(env: String, local: String) = environment[env] ?: run {
            project.logger.warn("WARNING: No $env environmental variable")
            localProperties?.getProperty(local) ?: run {
                project.logger.warn("WARNING: No $local local property")
                null
            }
        }

        data class Keystore(
            val storeFile: File,
            val storePassword: String,
            val keyAlias: String,
            val keyPassword: String
        )

        fun getDebugKeystore(): Keystore? {
            return Keystore(
                rootProject.file("signing/debug.jks"),
                get("DEBUG_KEYSTORE_PASSWORD", "signing.debugKeystorePassword") ?: return null,
                get("DEBUG_KEY_ALIAS", "signing.debugKeystoreAlias") ?: return null,
                get("DEBUG_KEY_PASSWORD", "signing.debugKeyPassword") ?: return null
            )
        }

        getDebugKeystore()?.let { keystore ->
            getByName("debug") {
                storeFile = keystore.storeFile
                storePassword = keystore.storePassword
                keyAlias = keystore.keyAlias
                keyPassword = keystore.keyPassword
            }
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

    packagingOptions {
        resources.excludes.add("META-INF/DEPENDENCIES")
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
    // region Local
    implementation(project(":uikit"))
    implementation(project(":core:repository"))
    implementation(project(":utils"))
    implementation(project(":entity"))
    // endregion

    // region AndroidX
    val navigationVersion: String by project
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")
    implementation("androidx.preference:preference:1.1.1")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta01")
    // endregion

    // region Lifecycle
    val lifecycleVersion: String by project
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    // endregion

    // region Core
    val hiltVersion: String by project
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-android-compiler:$hiltVersion")
    // endregion

    // region Google services
    implementation(platform("com.google.firebase:firebase-bom:26.1.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")
    implementation("com.google.firebase:firebase-perf-ktx")
    implementation("com.google.firebase:firebase-inappmessaging-display")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.android.gms:play-services-auth:19.2.0")
    implementation("com.google.api-client:google-api-client-android:1.26.0")
    implementation("com.google.http-client:google-http-client-gson:1.26.0")
    implementation("com.google.apis:google-api-services-drive:v3-rev136-1.25.0")
    // endregion
}
