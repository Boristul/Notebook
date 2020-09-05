plugins {
    id("io.gitlab.arturbosch.detekt") version ("1.12.0")
}

buildscript {
    val kotlin_version by extra("1.4.0")
    repositories {
        google()
        jcenter()
    }
    dependencies {
        val kotlinVersion: String by project

        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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
    input = files(*subprojects.map { "${it.name}/src" }.toTypedArray())
    reports { xml { enabled = false } }
    failFast = false
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.12.0")
}
