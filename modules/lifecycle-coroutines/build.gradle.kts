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
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

android {
    setDefaults()
}

kotlin {
    metadataPublication(project)
    androidWithPublication(project)
    sourceSets {
        getByName("androidMain").dependencies {
            api(splitties("experimental"))
            implementation(splitties("mainhandler"))
            implementation(splitties("mainthread"))
            api(Libs.kotlin.stdlibJdk7)
            api(Libs.kotlinX.coroutines.android)
            api(Libs.androidX.lifecycle.common)
        }
        matching { it.name.startsWith("android") }.all {
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

    bintray {
        setupPublicationsUpload(project, publishing, skipMetadataPublication = true)
    }
}

dependencies {
    arrayOf(
        Libs.kotlinX.coroutines.test,
        Libs.kotlin.testJunit,
        Libs.androidX.lifecycle.runtime
    ).forEach {
        testImplementation(it)
        androidTestImplementation(it)
    }
    androidTestImplementation(Libs.androidX.test.runner)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().whenTaskAdded {
    kotlinOptions.freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
}
