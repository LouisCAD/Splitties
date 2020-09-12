/*
 * Copyright 2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType.*
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
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
    }.ensureGroups("com.louiscad.splitties")
}

tasks.matching { it.name.startsWith("lint") }.configureEach { enabled = false }

android {
    setDefaults()
}

fun KotlinTarget.targetsSamePlatformAs(other: KotlinTarget): Boolean = when (this) {
    is KotlinNativeTarget -> other is KotlinNativeTarget && konanTarget == other.konanTarget
    else -> platformType == other.platformType
}

val KotlinTarget.mainSourceSet get() = when {
    platformType == androidJvm && compilations.isEmpty() -> {
        project.the<KotlinMultiplatformExtension>().sourceSets.androidMain
    }
    else -> compilations.getByName("main").defaultSourceSet
}

kotlin {
    android()
    jvm()
    js { browser(); nodejs() }

    macosX64()
    iosArm32(); iosArm64(); iosX64()
    watchosArm32(); watchosArm64(); watchosX86()

    mingw(x64 = true)
    linux(x64 = true)

    val group = "com.louiscad.splitties"
    val version = thisLibraryVersion

    sourceSets {
        commonMain {
            dependencies {
                // Needs to be put back if published with HMPP-only,
                // e.g. without this property: kotlin.mpp.enableCompatibilityMetadataVariant=true,
                // Otherwise, the dependencies task fails with an obscure error:
                // "Couldn't resolve metadata artifact for ModuleDependencyIdentifier(groupId=org.jetbrains.kotlinx, moduleId=kotlinx-coroutines-core) in configuration ':tools:publication-checker:iosArm32CompileKlibraries'"
                //TODO: Report the issue before HMPP becomes the default.
                //implementation(KotlinX.coroutines.core)
            }
        }
    }

    rootProject.project(":modules").subprojects {
        if (plugins.hasPlugin("org.jetbrains.kotlin.multiplatform").not()) return@subprojects
        val splitTargets = the<KotlinMultiplatformExtension>().targets.also {
            check(it.isNotEmpty())
        }
        targets.forEach { target ->
            if (target.platformType == common) return@forEach
            val sourceSet: KotlinSourceSet = target.mainSourceSet
            if (splitTargets.any { it.targetsSamePlatformAs(target) }) {
                sourceSet.dependencies {
                    implementation("$group:splitties-${project.name}") {
                        version { strictly(version) }
                    }
                }
            }
        }
    }
    rootProject.project(":fun-packs").subprojects {
        if (plugins.hasPlugin("org.jetbrains.kotlin.multiplatform").not()) return@subprojects
        val splitTargets = the<KotlinMultiplatformExtension>().targets.also {
            check(it.isNotEmpty())
        }
        targets.forEach { target ->
            if (target.platformType == common) return@forEach
            val sourceSet: KotlinSourceSet = target.mainSourceSet
            if (splitTargets.any { it.targetsSamePlatformAs(target) }) {
                sourceSet.dependencies {
                    implementation("$group:splitties-fun-pack-${project.name}") {
                        version { strictly(version) }
                    }
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
