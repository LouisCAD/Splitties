/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager

fun KotlinTargetContainerWithPresetFunctions.macos() {
    if (HostManager.hostIsMac) macosX64()
}

fun KotlinTargetContainerWithPresetFunctions.ios() {
    if (HostManager.hostIsMac.not()) return
    if (isRunningInIde) {
        if (use32bitsInIde) iosArm32() else iosX64()
    } else iosAll()
}

private fun KotlinTargetContainerWithPresetFunctions.iosAll() {
    iosX64()
    iosArm64()
    iosArm32()
}

fun KotlinMultiplatformExtension.setupSourceSets() {
    val nativeTargets = targets.filterIsInstance<KotlinNativeTarget>()
    if (nativeTargets.isEmpty()) return

    val minGWTargets = nativeTargets.filterFamily(Family.MINGW)
    val macOSTargets = nativeTargets.filterFamily(Family.OSX)
    val iosTargets = nativeTargets.filterFamily(Family.IOS)
    val linuxTargets = nativeTargets.filterFamily(Family.LINUX)
    val androidNativeTargets = nativeTargets.filterFamily(Family.ANDROID)

    sourceSets.apply {
        if (isRunningInIde) {
            iosTargets.forEach { target ->
                target.mainSourceSet {
                    kotlin.srcDir("src/iosMain/allButAndroidMain/kotlin")
                    kotlin.srcDir("src/iosMain/nativeMain/kotlin")
                    kotlin.srcDir("src/iosMain/appleMain/kotlin")
                    if (target.konanTarget.architecture.bitness == 64) {
                        kotlin.srcDir("src/iosMain/apple64Main/kotlin")
                    }
                    kotlin.srcDir("src/iosMain/kotlin")
                }
            }
            macOSTargets.forEach { target ->
                target.mainSourceSet {
                    kotlin.srcDir("src/macosMain/allButAndroidMain/kotlin")
                    kotlin.srcDir("src/macosMain/nativeMain/kotlin")
                    kotlin.srcDir("src/macosMain/appleMain/kotlin")
                    kotlin.srcDir("src/macosMain/apple64Main/kotlin")
                    kotlin.srcDir("src/macosMain/kotlin")
                }
            }
            androidNativeTargets.forEach { target ->
                target.mainSourceSet {
                    kotlin.srcDir("src/androidNativeMain/allButAndroidMain/kotlin") // Android != AndroidNative.
                    kotlin.srcDir("src/androidNativeMain/nativeMain/kotlin")
                    kotlin.srcDir("src/androidNativeMain/kotlin")
                }
            }
            linuxTargets.forEach { target ->
                target.mainSourceSet {
                    kotlin.srcDir("src/linuxMain/allButAndroidMain/kotlin")
                    kotlin.srcDir("src/linuxMain/nativeMain/kotlin")
                    kotlin.srcDir("src/linuxMain/kotlin")
                }
            }
        } else {
            createMainAndTest("allButAndroid")
            createMainAndTest("native", dependsOn = "allButAndroid")

            val supportIos = iosTargets.isNotEmpty()
            val supportMacos = macOSTargets.isNotEmpty()
            if (supportMacos || supportIos) {
                createMainAndTest("apple", dependsOn = "native")
                val (apple64Main, apple64Test) = createMainAndTest("apple64", dependsOn = "apple")
                if (supportMacos) {
                    val (main, test) = createMainAndTest("macos", dependsOn = "apple")
                    macOSTargets.forEach { target ->
                        target.mainSourceSet.dependsOn(main, apple64Main)
                        target.testSourceSet.dependsOn(test, apple64Test)
                    }
                }
                if (supportIos) {
                    val (main, test) = createMainAndTest("ios", dependsOn = "apple")
                    iosTargets.forEach { target ->
                        if (target.konanTarget.architecture.bitness == 64) {
                            target.mainSourceSet.dependsOn(apple64Main)
                            target.testSourceSet.dependsOn(apple64Test)
                        }
                        target.mainSourceSet.dependsOn(main)
                        target.testSourceSet.dependsOn(test)
                    }
                }
            }
            if (linuxTargets.isNotEmpty()) {
                val (main, test) = createMainAndTest("linux", dependsOn = "native")
                createAndLinkBitnessSpecificSourceSet("linux", bitness = 64, targets = linuxTargets)
                createAndLinkBitnessSpecificSourceSet("linux", bitness = 32, targets = linuxTargets)
                linuxTargets.forEach { target ->
                    target.mainSourceSet.dependsOn(main)
                    target.testSourceSet.dependsOn(test)
                }
            }
            if (minGWTargets.isNotEmpty()) {
                val (main, test) = createMainAndTest("mingw", dependsOn = "native")
                minGWTargets.forEach { target ->
                    target.mainSourceSet.dependsOn(main)
                    target.testSourceSet.dependsOn(test)
                }
            }
            if (androidNativeTargets.isNotEmpty()) {
                val (main, test) = createMainAndTest("androidNative", dependsOn = "native")
                androidNativeTargets.forEach { target ->
                    target.mainSourceSet.dependsOn(main)
                    target.testSourceSet.dependsOn(test)
                }
            }
            //TODO: Add support for WASM and Zephyr.
        }
    }
}

private fun NamedDomainObjectContainer<KotlinSourceSet>.createMainAndTest(
    name: String,
    dependsOn: String = "common",
    configureAction: KotlinSourceSet.(isTestSourceSet: Boolean) -> Unit = {}
): Pair<KotlinSourceSet, KotlinSourceSet> {
    require(dependsOn.endsWith("Main").not())
    require(dependsOn.endsWith("Test").not())
    val mainSourceSet = create("${name}Main").apply {
        dependsOn(getByName("${dependsOn}Main"))
        configureAction(false)
    }
    val testSourceSet = create("${name}Test").apply {
        dependsOn(mainSourceSet)
        getByName("${dependsOn}Test")
        configureAction(true)
    }
    return mainSourceSet to testSourceSet
}

private fun NamedDomainObjectContainer<KotlinSourceSet>.createAndLinkBitnessSpecificSourceSet(
    dependsOn: String,
    bitness: Int,
    targets: List<KotlinNativeTarget>
) {
    if (targets.any { it.konanTarget.architecture.bitness == bitness }) {
        val (main, test) = createMainAndTest("$dependsOn$bitness", dependsOn = dependsOn)
        targets.forEach { target ->
            if (target.konanTarget.architecture.bitness != bitness) return@forEach
            target.mainSourceSet.dependsOn(main)
            target.testSourceSet.dependsOn(test)
        }
    }
}

private fun List<KotlinNativeTarget>.filterFamily(family: Family) = filter {
    it.konanTarget.family == family
}

private val KotlinTarget.mainSourceSet get() = compilations.main.defaultSourceSet
private inline fun KotlinTarget.mainSourceSet(configure: KotlinSourceSet.() -> Unit) {
    compilations.main.defaultSourceSet.configure()
}

private val KotlinTarget.testSourceSet get() = compilations.test.defaultSourceSet
private inline fun KotlinTarget.testSourceSet(configure: KotlinSourceSet.() -> Unit) {
    compilations.test.defaultSourceSet.configure()
}

private fun KotlinSourceSet.dependsOn(vararg others: KotlinSourceSet) {
    others.forEach { dependsOn(it) }
}

private val NamedDomainObjectContainer<out KotlinCompilation<KotlinCommonOptions>>.main: KotlinCompilation<KotlinCommonOptions>
    get() = getByName("main")

private val NamedDomainObjectContainer<out KotlinCompilation<KotlinCommonOptions>>.test: KotlinCompilation<KotlinCommonOptions>
    get() = getByName("test")


val NamedDomainObjectContainer<KotlinSourceSet>.commonMain: KotlinSourceSet
    inline get() = getByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME)

fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME, configureAction)


val NamedDomainObjectContainer<KotlinSourceSet>.jvmMain: KotlinSourceSet
    inline get() = getByName("jvmMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.jvmMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName("jvmMain").apply(configureAction)


val NamedDomainObjectContainer<KotlinSourceSet>.jsMain: KotlinSourceSet
    inline get() = getByName("jsMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.jsMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName("jsMain").apply(configureAction)


val NamedDomainObjectContainer<KotlinSourceSet>.androidMain: KotlinSourceSet
    inline get() = getByName("androidMain")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName("androidMain").apply(configureAction)


fun NamedDomainObjectContainer<KotlinSourceSet>.iosMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet? = when {
    HostManager.hostIsMac.not() -> null
    isRunningInIde -> getByName(iosMainSourceSetNameForIde)
    else -> findByName("iosMain")
}?.apply(configureAction)


fun NamedDomainObjectContainer<KotlinSourceSet>.macosMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet? = when {
    HostManager.hostIsMac.not() -> null
    isRunningInIde -> getByName(macosMainSourceSetNameForIde)
    else -> findByName("macosMain")
}?.apply(configureAction)


fun NamedDomainObjectContainer<KotlinSourceSet>.appleMain(
    configureAction: KotlinSourceSet.() -> Unit
) {
    when {
        HostManager.hostIsMac.not() -> return
        isRunningInIde -> {
            findByName(iosMainSourceSetNameForIde)?.apply(configureAction)
            findByName(macosMainSourceSetNameForIde)?.apply(configureAction)
        }
        else -> findByName("appleMain")?.apply(configureAction)
    }
}

fun NamedDomainObjectContainer<KotlinSourceSet>.nativeMain(
    configureAction: KotlinSourceSet.() -> Unit
) {
    when {
        isRunningInIde -> {
            findByName(iosMainSourceSetNameForIde)?.apply(configureAction)
            findByName(macosMainSourceSetNameForIde)?.apply(configureAction)
            findByName(androidNativeMainSourceSetNameForIde)?.apply(configureAction)
        }
        else -> findByName("nativeMain")?.apply(configureAction)
    }
}


fun NamedDomainObjectContainer<KotlinSourceSet>.androidNativeMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet? = when {
    isRunningInIde -> getByName(androidNativeMainSourceSetNameForIde)
    else -> findByName("androidNativeMain")
}?.apply(configureAction)


private const val macosMainSourceSetNameForIde = "macosX64Main"

private val use32bitsInIde get() = true // Use getter to prevent "always true" warning.

private val iosMainSourceSetNameForIde = "ios${if (use32bitsInIde) "Arm32" else "X64"}Main"

private val androidNativeMainSourceSetNameForIde = use32bitsInIde.let { is32bits ->
    "androidNativeArm${if (is32bits) "32" else "64"}"
}
