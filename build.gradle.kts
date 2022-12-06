// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.3.1" apply(false)
    id("com.android.library") version "7.3.1" apply(false)
    id("org.jetbrains.kotlin.android")version "1.7.20" apply(false)
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}
buildscript {
    val kotlin_version by extra("1.7.20")
    val nav_version by extra("2.3.2")
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
    }
}
