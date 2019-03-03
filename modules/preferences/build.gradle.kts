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
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    metadataPublication(project)
    androidWithPublication(project)
    sourceSets {
        getByName("commonMain").dependencies {
            api(splitties("experimental"))
        }
        getByName("androidMain").dependencies {
            api(splitties("appctx"))
            api(splitties("mainthread"))

            api(Libs.kotlin.stdlibJdk7)
            compileOnly(Libs.kotlinX.coroutines.android)
        }
        getByName("androidTest").dependencies {
            implementation(Libs.kotlin.testJunit)
            implementation(Libs.androidX.test.coreKtx)
            implementation(Libs.androidX.test.ext.junit)
            implementation(Libs.androidX.test.espresso.core)
            implementation(Libs.kotlinX.coroutines.android)
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
