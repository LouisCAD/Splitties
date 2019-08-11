/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family.IOS
import org.jetbrains.kotlin.konan.target.Family.OSX

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults()
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    android()
    macos()
    ios()
    configure(targets) {
        if (this is KotlinNativeTarget && konanTarget.family.let { it == IOS || it == OSX }) {
            compilations.getByName("main").cinterops.create("kvo") {
                packageName("splitties.preferences.internal.kvo")
                headers("src/appleMain/objc/KeyValueObserver.h")
                headers("src/appleMain/objc/KeyValueObserverWrapper.h")
            }
        }
        configureMavenPublication()
    }
    setupNativeSourceSets()
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
            compileOnly(Libs.kotlinX.coroutines.coreCommon)
        }
        androidMain.dependencies {
            api(splitties("appctx"))
            api(splitties("mainthread"))

            api(Libs.kotlin.stdlibJdk7)
            compileOnly(Libs.kotlinX.coroutines.android)
        }
        nativeMain {
            dependencies {
                api(Libs.kotlinX.coroutines.coreNative)
            }
        }
        appleMain {
            dependencies {
                api(splitties("mainthread"))
            }
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
