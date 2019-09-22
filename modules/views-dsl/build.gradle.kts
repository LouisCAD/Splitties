/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
}

android {
    setDefaults()
}

kotlin {
    android()
    configure(targets) { configureMavenPublication(publishReleaseVariantOnly = false) }
    sourceSets {
        commonMain.dependencies {
            api(splitties("experimental"))
        }
        androidMain.dependencies {
            api(splitties("views"))
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.androidX.annotation)
            implementation(splitties("collections"))
            implementation(splitties("exceptions"))
        }
        androidDebug.dependencies {
            implementation(splitties("appctx"))
        }
        all {
            languageSettings.apply {
                enableLanguageFeature("InlineClasses")
                useExperimentalAnnotation("kotlin.Experimental")
                useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
                useExperimentalAnnotation("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }
}
