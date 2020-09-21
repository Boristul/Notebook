plugins {
    id("io.gitlab.arturbosch.detekt") version ("1.12.0")
}

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        val kotlinVersion: String by project

        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0")
        classpath("com.google.gms:google-services:4.3.3")
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

    //input = files(subprojects.flatMap { subProject ->
    //    println("1: ${subProject.name}")
    //    subProject.allprojects.map{
    //        println("2: ${subProject.name}")
    //        "${it.name}/src"
    //    } + "${subProject.name}/src"
    //}.toTypedArray())
    reports { xml { enabled = false } }
    failFast = false
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.12.0")
}
