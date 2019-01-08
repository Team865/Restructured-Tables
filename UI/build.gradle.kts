@file:Suppress("SpellCheckingInspection")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow")
    id("edu.sc.seis.launch4j")
    kotlin(module = "jvm") version "1.3.11"
}

repositories {
    mavenCentral()
    jcenter()
    maven(url = "http://dl.bintray.com/kyonifer/maven")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"

val kotlinVersion = "1.3.11"

dependencies {
    implementation(dependencyNotation = project(":CodeEditor"))
    implementation(dependencyNotation = project(":ContextModel"))

    // Java libraries
    implementation(group = "commons-io", name = "commons-io", version = "2.6")
    implementation(group = "com.github.sarxos", name = "webcam-capture", version = "0.3.12")
    implementation(group = "com.google.zxing", name = "core", version = "3.3.3")
    implementation(group = "com.google.zxing", name = "javase", version = "3.3.3")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-javafx", version = "2.4.0")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-fontawesome5-pack", version = "2.4.0")
    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.6")
    implementation(group = "org.controlsfx", name = "controlsfx", version = "8.40.14")

    // Kotlin libraries
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(kotlin("reflect", kotlinVersion))
    implementation(group = "com.kyonifer", name = "koma-core-ejml", version = "0.12")
    implementation(group = "de.mpicbg.scicomp", name = "krangl", version = "0.10.3")
    implementation(group = "com.beust", name = "klaxon", version = "3.0.1")
    testImplementation(kotlin("test", kotlinVersion))
}

val mainClassName0 = "ca.warp7.rt.core.Launcher"
application.mainClassName = mainClassName0

launch4j {
    mainClassName = mainClassName0
    icon = "$projectDir/src/main/resources/ca/warp7/rt/res/app-icon.ico"
    jar = "$buildDir/libs/UI-all.jar"
    outfile = "Restructured.exe"
}