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
    namespace = "splitties.views.recyclerview.compose"
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
            api(AndroidX.compose.foundation)
            api(AndroidX.compose.runtime)
            implementation(splitties("views-recyclerview"))
            implementation(splitties("views-dsl-recyclerview"))
            implementation(splitties("mainthread"))
            implementation(AndroidX.recyclerView)
            implementation(KotlinX.coroutines.core)
        }
    }
}
