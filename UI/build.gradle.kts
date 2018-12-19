@file:Suppress("SpellCheckingInspection")

plugins {
    id("java")
    id("application")
    id("com.github.johnrengelman.shadow")
    id("edu.sc.seis.launch4j")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":CodeEditor"))
    implementation(group = "commons-io", name = "commons-io", version = "2.6")
    implementation(group = "com.github.sarxos", name = "webcam-capture", version = "0.3.12")
    implementation(group = "com.google.zxing", name = "core", version = "3.3.3")
    implementation(group = "com.google.zxing", name = "javase", version = "3.3.3")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-javafx", version = "2.4.0")
    implementation(group = "org.kordamp.ikonli", name = "ikonli-fontawesome5-pack", version = "2.4.0")
    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.6")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.8.5")
    implementation(group = "org.controlsfx", name = "controlsfx", version = "8.40.12")
}

val mainClassName0 = "ca.warp7.rt.java.Main"
application.mainClassName = mainClassName0

launch4j {
    mainClassName = mainClassName0
    icon = "$projectDir/src/main/resources/app-icon.ico"
    jar = "$buildDir/libs/UI-all.jar"
}