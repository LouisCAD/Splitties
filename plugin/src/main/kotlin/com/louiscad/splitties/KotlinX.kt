package com.louiscad.splitties

object KotlinX {
    val coroutines = Coroutines

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.org.jetbrains.kotlinx=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlinx..$NAME=xxx`
     **/
    object Coroutines {
        private const val version = "+"
        private const val artifactPrefix = "org.jetbrains.kotlinx:kotlinx-coroutines"
        const val core = "$artifactPrefix-core:$version"
        const val coreCommon = "$artifactPrefix-core-common:$version"
        const val coreNative = "$artifactPrefix-core-native:$version"
        const val coreJs = "$artifactPrefix-core-js:$version"
        const val android = "$artifactPrefix-android:$version"
        const val playServices = "$artifactPrefix-play-services:$version"
        const val test = "$artifactPrefix-test:$version"
    }
}
