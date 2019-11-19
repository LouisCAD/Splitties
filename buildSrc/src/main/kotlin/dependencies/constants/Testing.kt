/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object Testing {

    /**
     * JUnit is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.
     *
     * Official website: [junit.org/junit5](https://junit.org/junit4/)
     */
    const val junit4 = "junit:junit:_"

    val junit = JunitJupiter
    val kotlinTest = KotlinTest
    val spek = Spek
    val strikt = Strikt

    val mockK = MockK
    val mockito = Mockito

    /**
     * Run unit tests in the JVM with the Android environment.
     *
     * GitHub page: [robolectric/robolectric](https://github.com/robolectric/robolectric)
     */
    const val roboElectric = "org.robolectric:robolectric:_"

    /**
     * The new major version of the programmer-friendly testing framework for Java
     *
     * Official website: [junit.org/junit5](https://junit.org/junit5/)
     */
    object JunitJupiter {
        private const val artifactPrefix = "org.junit.jupiter:junit-jupiter"

        const val junitJupiter = "$artifactPrefix:_"
        const val api = "$artifactPrefix-api:_"
        const val engine = "$artifactPrefix-engine:_"
        const val params = "$artifactPrefix-params:_"
        const val migrationSupport = "$artifactPrefix-migrationsupport:_"
    }

    /**
     * Powerful, elegant and flexible test framework for Kotlin
     *
     * GitHub page: [kotlintest/kotlintest](https://github.com/kotlintest/kotlintest)
     */
    object KotlinTest {
        private const val artifactBase = "io.kotlintest:kotlintest"

        val runner = Runner
        val plugins = Plugins
        val extensions = Extensions
        val assertions = Assertions

        const val core = "$artifactBase-core:_"
        const val datagen = "$artifactBase-datagen:_"


        object Runner {
            private const val artifactPrefix = "$artifactBase-runner"

            const val jvm = "$artifactPrefix-jvm:_"
            const val junit4 = "$artifactPrefix-junit4:_"
            const val junit5 = "$artifactPrefix-junit5:_"
            const val console = "$artifactPrefix-console:_"
        }

        object Plugins {
            private const val artifactPrefix = "$artifactBase-plugins"

            const val piTest = "$artifactPrefix-pitest:_"
        }

        object Extensions {
            private const val artifactPrefix = "$artifactBase-extensions"

            const val spring = "$artifactPrefix-spring:_"
            const val koin = "$artifactPrefix-koin:_"
            const val allure = "$artifactPrefix-allure:_"
            const val extensions = "$artifactPrefix:_"
        }

        object Assertions {
            private const val artifactPrefix = "$artifactBase-assertions"
            const val ktor = "$artifactPrefix-ktor:_"
            const val json = "$artifactPrefix-json:_"
            const val arrow = "$artifactPrefix-arrow:_"

            const val assertions = "$artifactPrefix:_"
        }
    }

    /**
     * A specification framework for Kotlin
     *
     * Official website :[spekframework.org](https://www.spekframework.org)
     * GitHub page: [spekframework/spek](https://github.com/spekframework/spek/)
     */
    object Spek {
        private const val artifactBase = "org.spekframework.spek2:spek"

        val dsl = Dsl
        val runner = Runner
        val runtime = Runtime

        object Dsl {
            private const val artifactPrefix = "$artifactBase-dsl"

            const val jvm = "$artifactPrefix-jvm:_"
            const val js = "$artifactPrefix-js:_"
            const val metadata = "$artifactPrefix-metadata:_"

            val native = Native

            object Native {
                private const val prefix = "$artifactPrefix-native"
                const val linux = "$prefix-linux:_"
                const val macos = "$prefix-macos:_"
                const val windows = "$prefix-windows:_"
            }
        }

        object Runner {
            private const val artifactPrefix = "$artifactBase-runner"

            const val junit5 = "$artifactPrefix-junit5:_"
        }

        object Runtime {
            private const val artifactPrefix = "$artifactBase-runtime"

            const val jvm = "$artifactPrefix-jvm:_"
            const val metadata = "$artifactPrefix-metadata:_"
        }
    }

    /**
     * Strikt is an assertion library for Kotlin intended for use with a test runner such as JUnit or Spek.
     *
     * Official website: [strikt.io](https://strikt.io)
     *
     * GitHub page: [robfletcher/strikt](https://github.com/robfletcher/strikt)
     */
    object Strikt {
        private const val artifactPrefix = "io.strikt:strikt"

        const val bom = "$artifactPrefix-bom:_"
        const val core = "$artifactPrefix-core:_"
        const val arrow = "$artifactPrefix-arrow:_"
        const val gradle = "$artifactPrefix-gradle:_"
        const val jackson = "$artifactPrefix-jackson:_"
        const val javaTime = "$artifactPrefix-java-time:_"
        const val protobuf = "$artifactPrefix-protobuf:_"
        const val string = "$artifactPrefix-string:_"
    }

    /**
     * Mocking library for Kotlin
     *
     * Official website: [mockk.io](http://mockk.io)
     *
     * GitHub page: [mockk/mockk](https://github.com/mockk/mockk).
     */
    object MockK {
        private const val artifactBase = "io.mockk:mockk"

        const val mockK = "$artifactBase:_"
        const val android = "$artifactBase-android:_"
        const val common = "$artifactBase-common:_"
    }

    /**
     * Tasty mocking framework for unit tests in Java
     *
     * Official website: [site.mockito.org](https://site.mockito.org)
     *
     * GitHub page: [mockito/mockito](https://github.com/mockito/mockito).
     */
    object Mockito {

        const val core = "org.mockito:mockito-core:_"
        const val android = "org.mockito:mockito-android:_"
        const val inline = "org.mockito:mockito-inline:_"
        const val errorProne = "org.mockito:mockito-errorprone:_"
        const val junitJupiter = "org.mockito:mockito-junit-jupiter:_"

        /**
         * Using Mockito with Kotlin
         * [More info here](https://github.com/nhaarman/mockito-kotlin)
         */
        const val kotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:_"
    }
}
