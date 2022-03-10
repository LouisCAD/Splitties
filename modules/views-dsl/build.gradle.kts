/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults(generateBuildConfig = true)
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
            api(AndroidX.annotation)
            implementation(splitties("collections"))
            implementation(splitties("exceptions"))
            implementation(splitties("appctx"))
        }
        all {
            languageSettings.apply {
                optIn("kotlin.RequiresOptIn")
                optIn("kotlin.contracts.ExperimentalContracts")
                optIn("splitties.experimental.InternalSplittiesApi")
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.useOldBackend = true //TODO: Remove when https://youtrack.jetbrains.com/issue/KT-44972 is addressed.
    // See this comment on why it's needed: https://youtrack.jetbrains.com/issue/KT-44972#focus=Comments-27-5014161.0-0
}
