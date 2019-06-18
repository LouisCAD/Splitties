/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTargetPreset
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.KonanTarget

fun KotlinTargetContainerWithPresetFunctions.macos() {
    if (os.isMacOS) macosX64()
}

fun KotlinTargetContainerWithPresetFunctions.ios() {
    if (os.isMacOS.not()) return
    if (isRunningInIde) {
        if (use32bitsInIde) iosArm32() else iosX64()
    } else iosAll()
}

private fun KotlinTargetContainerWithPresetFunctions.iosAll() {
    iosX64()
    iosArm64()
    iosArm32()
}

fun KotlinMultiplatformExtension.setupNativeSourceSets() {
    val nativeTargets = targets.filter { it.platformType == KotlinPlatformType.native }
    val nativeTargetsFamilies: Set<Family> = nativeTargets.map {
        (it.preset as KotlinNativeTargetPreset).konanTarget.family
    }.toSet()
    val supportMacOS = Family.OSX in nativeTargetsFamilies
    val supportIos = Family.IOS in nativeTargetsFamilies
    val supportAndroidNative = Family.ANDROID in nativeTargetsFamilies
    sourceSets.apply {
        if (isRunningInIde) {
            if (supportIos) sourceSets.getByName(iosMainSourceSetNameForIde) {
                kotlin.srcDir("src/iosMain/nativeMain/kotlin")
                kotlin.srcDir("src/iosMain/appleMain/kotlin")
                if (use32bitsInIde.not()) {
                    kotlin.srcDir("src/iosMain/apple64Main/kotlin")
                }
                kotlin.srcDir("src/iosMain/kotlin")
            }
            if (supportMacOS) sourceSets.getByName(macosMainSourceSetNameForIde) {
                kotlin.srcDir("src/macosMain/nativeMain/kotlin")
                kotlin.srcDir("src/macosMain/appleMain/kotlin")
                kotlin.srcDir("src/macosMain/apple64Main/kotlin")
                kotlin.srcDir("src/macosMain/kotlin")
            }
            if (supportAndroidNative) sourceSets.getByName(androidNativeMainSourceSetNameForIde) {
                kotlin.srcDir("src/androidNativeMain/nativeMain/kotlin")
                kotlin.srcDir("src/androidNativeMain/kotlin")
            }
        } else {
            createMainAndTest("native")
            if (supportMacOS || supportIos) {
                createMainAndTest("apple", dependsOn = "native")
                val (apple64Main, apple64Test) = createMainAndTest("apple64", dependsOn = "apple")
                if (supportIos) {
                    val iosTargets = nativeTargets.filter {
                        (it.preset as KotlinNativeTargetPreset).konanTarget.family == Family.IOS
                    }
                    val (main, test) = createMainAndTest("ios", dependsOn = "apple")
                    iosTargets.forEach { target ->
                        val konanTarget = (target.preset as KotlinNativeTargetPreset).konanTarget
                        if (konanTarget != KonanTarget.IOS_ARM32) {
                            target.compilations.main.defaultSourceSet.dependsOn(apple64Main)
                            target.compilations.test.defaultSourceSet.dependsOn(apple64Test)
                        }
                        target.compilations.main.defaultSourceSet.dependsOn(main)
                        target.compilations.test.defaultSourceSet.dependsOn(test)
                    }
                }
                if (supportMacOS) {
                    val macosTargets = nativeTargets.filter {
                        (it.preset as KotlinNativeTargetPreset).konanTarget.family == Family.OSX
                    }
                    val (main, test) = createMainAndTest("macos", dependsOn = "apple")
                    macosTargets.forEach { target ->
                        target.compilations.main.defaultSourceSet {
                            dependsOn(main)
                            dependsOn(apple64Main)
                        }
                        target.compilations.test.defaultSourceSet {
                            dependsOn(test)
                            dependsOn(apple64Test)
                        }
                    }
                }
            }
            if (supportAndroidNative) {
                val androidNativeTargets = nativeTargets.filter {
                    (it.preset as KotlinNativeTargetPreset).konanTarget.family == Family.ANDROID
                }
                val (main, test) = createMainAndTest("androidNative", dependsOn = "native")
                androidNativeTargets.forEach { target ->
                    target.compilations.main.defaultSourceSet.dependsOn(main)
                    target.compilations.test.defaultSourceSet.dependsOn(test)
                }
            }
            //TODO: Add support for Linux, Mingw, WASM and Zephyr.
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
    os.isMacOS.not() -> null
    isRunningInIde -> getByName(iosMainSourceSetNameForIde)
    else -> findByName("iosMain")
}?.apply(configureAction)


fun NamedDomainObjectContainer<KotlinSourceSet>.macosMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet? = when {
    os.isMacOS.not() -> null
    isRunningInIde -> getByName(macosMainSourceSetNameForIde)
    else -> findByName("macosMain")
}?.apply(configureAction)


fun NamedDomainObjectContainer<KotlinSourceSet>.appleMain(
    configureAction: KotlinSourceSet.() -> Unit
) {
    when {
        os.isMacOS.not() -> return
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
