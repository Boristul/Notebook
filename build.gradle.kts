import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version com.boristul.libs.Libs.Versions.detektVersion
    kotlin("plugin.serialization") version com.boristul.libs.Libs.Versions.kotlinVersion
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(com.boristul.libs.Libs.gradle)
        classpath(com.boristul.libs.Libs.Kotlin.gradlePlugin)
        classpath(com.boristul.libs.Libs.Kotlin.serialization)
        classpath(com.boristul.libs.Libs.Navigation.navigationArgsPlugin)
        classpath(com.boristul.libs.Libs.GoogleServices.googleServicesPlugin)
        classpath(com.boristul.libs.Libs.GoogleServices.firebaseCrashlyticsPlugin)
        classpath(com.boristul.libs.Libs.GoogleServices.firebasePerfPlugin)

        classpath(com.boristul.libs.Libs.Hilt.hiltPlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

subprojects.forEach { module ->
    module.tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
            @Suppress("SuspiciousCollectionReassignment")
            freeCompilerArgs += listOf(
                //"-XXLanguage:+InlineClasses",
                "-Xnew-inference"
            )
        }
    }
}

detekt {
    config = files("detekt-config.yml")

    source = files(*subprojects.map { subproject ->
        if (subproject.subprojects.isEmpty())
            "${subproject.name}/src"
        else
            subproject.subprojects.map {
                "${subproject.name}/${it.name}/src"
            }
    }.toTypedArray())

    reports { xml { enabled = false } }
}

dependencies {
    detektPlugins(com.boristul.libs.Libs.detektPlugin)
}
