@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin(module = "jvm") version "1.3.21"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "http://dl.bintray.com/kyonifer/maven")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val kotlinVersion = "1.3.21"

buildDir = File(rootProject.projectDir, "build/${project.name}")

dependencies {
    // Kotlin libraries
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    testImplementation(kotlin("test", kotlinVersion))
    testImplementation(project)
}