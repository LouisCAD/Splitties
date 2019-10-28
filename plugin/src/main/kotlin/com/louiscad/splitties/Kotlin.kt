package com.louiscad.splitties

/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.org.jetbrains.kotlin=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlin..$NAME=xxx`
 **/
object Kotlin {
    val kotlinVersion = "1.3.50"
    val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
    val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"
}
