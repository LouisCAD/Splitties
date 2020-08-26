/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
            api(splitties("experimental"))
            implementation(splitties("collections"))
            api(KotlinX.coroutines.core)
        }
        jvmMain.dependencies {
            api(Kotlin.stdlib.jdk7)
        }
        jsMain.dependencies {
            api(Kotlin.stdlib.js)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.RequiresOptIn")
                useExperimentalAnnotation("splitties.experimental.ExperimentalSplittiesApi")
            }
        }
    }
}

publishing {
    setupAllPublications(project)
}
