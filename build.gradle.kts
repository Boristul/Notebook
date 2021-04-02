plugins {
    id("io.gitlab.arturbosch.detekt") version com.boristul.buildsrc.Libs.Versions.detektVersion
    kotlin("plugin.serialization") version "1.4.10"
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(com.boristul.buildsrc.Libs.gradle)
        classpath(com.boristul.buildsrc.Libs.Kotlin.gradlePlugin)
        classpath(com.boristul.buildsrc.Libs.Kotlin.serialization)
        classpath(com.boristul.buildsrc.Libs.AndroidX.safeArgsPlugin)
        classpath(com.boristul.buildsrc.Libs.GoogleServices.googleServicesPlugin)
        classpath(com.boristul.buildsrc.Libs.GoogleServices.firebaseCrashlyticsPlugin)
        classpath(com.boristul.buildsrc.Libs.GoogleServices.firebasePerfPlugin)
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
    module.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
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

    input = files(*subprojects.map { subproject ->
        if (subproject.subprojects.isEmpty())
            "${subproject.name}/src"
        else
            subproject.subprojects.map {
                "${subproject.name}/${it.name}/src"
            }
    }.toTypedArray())

    reports { xml { enabled = false } }
    failFast = false
}

dependencies {
    detektPlugins(com.boristul.buildsrc.Libs.detektPlugin)
}
