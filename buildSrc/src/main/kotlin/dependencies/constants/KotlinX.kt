@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object KotlinX {
    val coroutines = Coroutines

    object Coroutines {
        private const val version = "_"
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
