@file:Suppress("SpellCheckingInspection")

/*
* Copyright (c) 2016. Louis Cognault Ayeva Derman
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

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version: String by extra { Versions.kotlin }
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.3.0-rc01")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.8.0")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.0")
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.6.2")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    val projectBuildTools_version by extra { "28.0.3" }
    val projectSdk_version by extra { 28 }

    // Libraries
    val groupId by extra { "com.louiscad.splitties" }
    val library_version by extra { "3.0.0-SNAPSHOT" }
    val isSnapshot by extra { library_version.endsWith("-SNAPSHOT") }
    val isRelease by extra { !isSnapshot }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
