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

object Versions {
    const val kotlin = "1.2.51"
    const val espresso = "3.1.0-alpha3"
    const val junit = "4.12"
    const val kotlinxCoroutines = "0.23.4"
}

object Libs {
    const val junit = "junit:junit:${Versions.junit}"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val stetho = "com.facebook.stetho:stetho:1.5.0"

    val kotlin = Kotlin
    val kotlinX = KotlinX
    val androidX = AndroidX
    val google = Google

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object KotlinX {
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
    }

    object AndroidX {
        const val annotation = "androidx.annotation:annotation:1.0.0-beta01"
        const val core = "androidx.core:core:1.0.0-beta01"
        const val fragment = "androidx.fragment:fragment:1.0.0-beta01"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.0.0-beta01"
        const val cardView = "androidx.cardview:cardview:1.0.0-beta01"
        const val appCompat = "androidx.appcompat:appcompat:1.0.0-beta01"
        const val lifecycle = "androidx.lifecycle:lifecycle-extensions:2.0.0-beta01"
        const val room = "androidx.room:room-runtime:2.0.0-beta01"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.0-alpha1"
        const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val testRunner = "androidx.test:runner:1.1.0-alpha3"
    }

    object Google {
        const val material = "com.google.android.material:material:1.0.0-beta01"
    }
}
