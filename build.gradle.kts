import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    kotlin("plugin.serialization") version "1.4.10"
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        val kotlinVersion: String by project
        val hiltVersion: String by project

        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.5")
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.0")
        classpath("com.google.firebase:perf-plugin:1.4.0")

        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.18.1")
}
