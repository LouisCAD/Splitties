/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

@Suppress("GradlePluginVersion")
dependencies {
    compileOnly(gradleKotlinDsl())
    implementation("com.android.tools.build:gradle:_")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:_")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:_")
}
