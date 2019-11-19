@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object Kotlin {

    val stdlib = Stdlib
    val test = Test

    object Stdlib {
        const val jdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:_"
        const val jdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:_"
        const val js = "org.jetbrains.kotlin:kotlin-stdlib-js:_"
        const val common = "org.jetbrains.kotlin:kotlin-stdlib-common:_"
    }

    object Test {
        const val annotationsCommon = "org.jetbrains.kotlin:kotlin-test-annotations-common:_"
        const val common = "org.jetbrains.kotlin:kotlin-test-common:_"
        const val js = "org.jetbrains.kotlin:kotlin-test-js:_"
        const val jsRunner = "org.jetbrains.kotlin:kotlin-test-js-runner:_"

        const val junit = "org.jetbrains.kotlin:kotlin-test-junit:_"
        const val junit5 = "org.jetbrains.kotlin:kotlin-test-junit5:_"
        const val testng = "org.jetbrains.kotlin:kotlin-testng:_"
    }
}
