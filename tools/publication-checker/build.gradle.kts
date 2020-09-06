/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import kotlin.time.*

buildscript {
    repositories { mavenCentral() }
    dependencies.classpath(Square.okHttp3.okHttp)
    dependencies.classpath(Square.retrofit2.retrofit)
}

plugins {
    id("com.android.library")
    kotlin("multiplatform")
}

fun bintrayProperty(keySuffix: String): String = project.property("splitties.bintray.$keySuffix") as String

val bintrayUsername = bintrayProperty("user")
val bintrayApiKey = project.property("bintray_api_key") as String
val isDevVersion = project.isDevVersion
val bintrayRepoName = bintrayProperty(if (isDevVersion) "repo.dev" else "repo.release")
val bintrayPackageName = bintrayProperty("package")

repositories {
    maven("https://dl.bintray.com/$bintrayUsername/$bintrayRepoName/") {
        name = "bintray/$bintrayUsername/$bintrayRepoName"
        credentials {
            username = bintrayUsername
            password = bintrayApiKey
        }
    }
}

tasks.matching { it.name.startsWith("lint") }.configureEach { enabled = false }

android {
    setDefaults()
}

kotlin {
    android()
    jvm()
    js()

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    mingw(x64 = true)
    linux(x64 = true)

    sourceSets {
        val group = "com.louiscad.splitties"
        val version = thisLibraryVersion

        commonMain.dependencies {

            rootProject.project(":modules").subprojects.filter {
                it.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
            }.forEach {
                implementation("$group:splitties-${it.name}") {
                    version { strictly(version) }
                }
            }

            rootProject.project(":fun-packs").subprojects.filter {
                it.plugins.hasPlugin("org.jetbrains.kotlin.multiplatform")
            }.forEach {
                implementation("$group:splitties-fun-pack-${it.name}") {
                    version { strictly(version) }
                }
            }
        }

        androidMain.dependencies {

            rootProject.project(":modules").subprojects.filter {
                it.plugins.hasPlugin("org.jetbrains.kotlin.android")
            }.forEach {
                implementation("$group:splitties-${it.name}") {
                    version { strictly(version) }
                }
            }

            rootProject.project(":fun-packs").subprojects.filter {
                it.plugins.hasPlugin("org.jetbrains.kotlin.android")
            }.forEach {
                implementation("$group:splitties-fun-pack-${it.name}") {
                    version { strictly(version) }
                }
            }
        }
    }
}

rootProject.tasks.register("publishBintrayRelease") {
    group = "publishing"
    description = "Publishes the artifacts uploaded on bintray for this version"

    dependsOn(tasks.named("build")) // Ensure publish happens after concurrent build check.

    @Suppress("experimental_is_not_enabled")
    @OptIn(ExperimentalTime::class)
    doFirst {

        // These durations might need to be raised for larger projects...
        // ...(but at this point, isn't the project too large?)
        val requestReadTimeout = 10.minutes
        val retryBackOff = 30.seconds
        val giveUpAfter = 1.hours

        val subject = bintrayUsername
        val repo = bintrayRepoName
        val version = thisLibraryVersion
        val `package` = bintrayPackageName

        val request = okhttp3.Request.Builder()
            .header(
                name = "Authorization",
                value = okhttp3.Credentials.basic(username = bintrayUsername, password = bintrayApiKey)
            )
            // Bintray API reference: https://bintray.com/docs/api/#_publish_discard_uploaded_content
            .url("https://bintray.com/api/v1/content/$subject/$repo/$`package`/$version/publish")
            .post("""{"publish_wait_for_secs":-1}""".toRequestBody("application/json".toMediaType()))
            .build()
        val httpClient = okhttp3.OkHttpClient.Builder()
            .readTimeout(requestReadTimeout.toJavaDuration())
            .build()

        /**
         * If [isLastAttempt] is true, any failure will be thrown as an exception.
         */
        fun attemptPublishing(isLastAttempt: Boolean = false): Boolean {
            println("Attempting bintray publish")
            try {
                httpClient.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        println(response.body?.string())
                        return true
                    }
                    if (isLastAttempt.not()) when (val code = response.code) {
                        408, 405 -> {
                            logger.error("Publish attempt failed (http $code)")
                            logger.info(response.body?.string() ?: "")
                            return false
                        }
                    }
                    throw retrofit2.HttpException(Response.error<Any?>(response.code, response.body!!))
                }
            } catch (e: java.io.IOException) {
                if (isLastAttempt) throw e
                logger.error("Publish attempt failed with ${e.javaClass.simpleName}: ${e.message}")
                return false
            }
        }

        println("Will attempt bintray publishing on ${request.url}")

        val deadline = TimeSource.Monotonic.markNow() + giveUpAfter
        do {
            val didSucceed = attemptPublishing(isLastAttempt = deadline.hasPassedNow())
            if (didSucceed.not()) Thread.sleep(retryBackOff.toLongMilliseconds())
        } while (didSucceed.not())

        println("Bintray publishing successful!")
    }
}
