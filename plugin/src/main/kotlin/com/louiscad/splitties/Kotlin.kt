package com.louiscad.splitties

/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.org.jetbrains.kotlin=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlin..$NAME=xxx`
 **/
object Kotlin {
    private const val kotlinVersion = "1.3.50"
    const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
}
