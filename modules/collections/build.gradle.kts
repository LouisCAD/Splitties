/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvm()
    js { useCommonJs() }
    macos()
    ios(supportArm32 = true)
    linux(x64 = true)
    mingw(x64 = true)
    configure(targets) { configureMavenPublication() }
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(Kotlin.stdlib.common)
        }
        jvmMain.dependencies {
            api(Kotlin.stdlib.jdk7)
        }
        jsMain.dependencies {
            api(Kotlin.stdlib.js)
        }
    }
}

publishing {
    setupAllPublications(project)
}
