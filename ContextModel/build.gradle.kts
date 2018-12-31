@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    kotlin(module = "jvm") version "1.3.11"
}

repositories {
    mavenCentral()
    jcenter()
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val kotlinVersion = "1.3.11"

dependencies {
    implementation(fileTree("${rootProject.projectDir}/libs"))
    // Kotlin libraries
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation(group = "com.kyonifer", name = "koma-core-ejml", version = "0.12")
    implementation(group = "de.mpicbg.scicomp", name = "krangl", version = "0.10.3")
    implementation(group = "com.beust", name = "klaxon", version = "3.0.1")
    testImplementation(kotlin("test", kotlinVersion))
    testImplementation(project)
}