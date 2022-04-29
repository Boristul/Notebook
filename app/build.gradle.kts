import com.boristul.libs.Config
import com.boristul.libs.Libs
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

    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.boristul.notebook"
        versionCode = Config.versionCode
        versionName = Config.versionName

        minSdk = 21
        targetSdk = Config.targetSdk

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
    implementation(project(Libs.Local.uiKit))
    implementation(project(Libs.Local.repository))
    implementation(project(Libs.Local.utils))
    implementation(project(Libs.Local.entity))
    // endregion

    // region AndroidX
    implementation(Libs.AndroidX.core)
    implementation(Libs.Navigation.navigationFragment)
    implementation(Libs.Navigation.navigationUI)
    implementation(Libs.AndroidX.preference)
    implementation(Libs.AndroidX.viewPager2)
    implementation(Libs.AndroidX.lifecycleLivedata)
    // endregion

    // region Lifecycle
    implementation(Libs.AndroidX.lifecycleRuntime)
    // endregion

    // region Core
    implementation(Libs.Hilt.hiltAndroid)
    kapt(Libs.Hilt.hiltCompiler)

    implementation(Libs.Core.jodaTime)
    // endregion

    // region UI
    implementation(Libs.Core.viewBinding)
    // endregion

    // region Google services
    implementation(platform(Libs.GoogleServices.firebaseBom))
    Libs.GoogleServices.googleServicesDeps.forEach {
        implementation(it)
    }
    // endregion
}
