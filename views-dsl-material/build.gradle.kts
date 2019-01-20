/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(ProjectVersions.androidSdk)
    buildToolsVersion(ProjectVersions.androidBuildTools)
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(ProjectVersions.androidSdk)
        versionCode = 1
        versionName = ProjectVersions.thisLibrary
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    sourceSets.forEach { it.java.srcDir("src/${it.name}/kotlin") }
}

dependencies {
    api(project(":views-dsl"))
    api(project(":views-dsl-appcompat"))
    api(project(":views-dsl-coordinatorlayout"))
    api(project(":views-dsl-recyclerview"))
    api(project(":views-material"))
    api(project(":initprovider"))

    api(Libs.kotlin.stdlibJdk7)
    api(Libs.androidX.annotation)
    api(Libs.google.material)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().whenTaskAdded {
    kotlinOptions {
        freeCompilerArgs += "-Xuse-experimental=kotlin.contracts.ExperimentalContracts"
        freeCompilerArgs += "-Xuse-experimental=splitties.experimental.InternalSplittiesApi"
    }
}

apply {
    from("../publish.gradle")
}
