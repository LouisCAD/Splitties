import de.fayard.refreshVersions.core.versionFor

/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    publish
}

android {
    setDefaults()
    namespace = "splitties.permissions.compose"
    buildFeatures.compose = true
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
}

kotlin {
    androidTarget()

    configure(targets) { configureMavenPublication() }
    sourceSets {
        androidMain.dependencies {
            api(splitties("permissions", "core"))
            api(splitties("compose", "callable-state"))
            api(AndroidX.compose.runtime)
            api(AndroidX.activity.compose)
            implementation(KotlinX.coroutines.core)
        }
        all {
            languageSettings.apply {
                optIn("splitties.experimental.ExperimentalSplittiesApi")
            }
        }
    }
}
