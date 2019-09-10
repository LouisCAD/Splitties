/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.konan.target.HostManager

plugins {
    kotlin("multiplatform")
    `maven-publish`
}

kotlin {
    jvm()
    js()
    macos()
    ios()
    if (isRunningInIde.not() || HostManager.hostIsLinux) linuxX64()
    if (isRunningInIde.not() || HostManager.hostIsMingw) mingwX64()
    setupSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(kotlin("stdlib-common"))
            api(Libs.kotlinX.coroutines.coreCommon)
        }
        jvmMain.dependencies {
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.kotlinX.coroutines.core)
        }
        jsMain.dependencies {
            api(kotlin("stdlib-js"))
            api(Libs.kotlinX.coroutines.coreJs)
        }
        nativeMain {
            dependencies {
                api(Libs.kotlinX.coroutines.coreNative)
            }
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}
