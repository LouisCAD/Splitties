/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import com.android.build.gradle.BaseExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.jetbrains.kotlin.gradle.dsl.KotlinCommonOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.plugin.KotlinTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget
import org.jetbrains.kotlin.konan.target.Family
import org.jetbrains.kotlin.konan.target.HostManager

fun KotlinTargetContainerWithPresetFunctions.linux(
    x64: Boolean = false,
    arm32Hfp: Boolean = false,
    arm64: Boolean = false,
    mips32: Boolean = false,
    mipsel32: Boolean = false
) {
    if (HostManager.hostIsLinux.not() && isRunningInIde) return
    if (x64) linuxX64()
    if (arm32Hfp) linuxArm32Hfp()
    if (arm64) linuxArm64()
    if (mips32) linuxMips32()
    if (mipsel32) linuxMipsel32()
}

fun KotlinTargetContainerWithPresetFunctions.mingw(
    x64: Boolean = false,
    x86: Boolean = false
) {
    if (HostManager.hostIsMingw.not() && isRunningInIde) return
    if (x64) mingwX64()
    if (x86) mingwX86()
}

fun KotlinTargetContainerWithPresetFunctions.macos() {
    if (HostManager.hostIsMac.not() && isRunningInIde) return
    macosX64()
}

fun KotlinTargetContainerWithPresetFunctions.ios(supportArm32: Boolean) {
    if (HostManager.hostIsMac.not() && isRunningInIde) return
    if (isRunningInIde) {
        if (supportArm32 && use32bitsInIde) iosArm32() else iosX64()
    } else iosAll()
}

private fun KotlinTargetContainerWithPresetFunctions.iosAll() {
    iosX64()
    iosArm64()
    iosArm32()
}

private fun NamedDomainObjectContainer<KotlinSourceSet>.createMainAndTest(
    name: String,
    dependsOn: String = "common",
    configureAction: KotlinSourceSet.(isTestSourceSet: Boolean) -> Unit = {}
) = createMainAndTest(name, listOf(dependsOn), configureAction)

private fun NamedDomainObjectContainer<KotlinSourceSet>.createMainAndTest(
    name: String,
    dependsOn: List<String>,
    configureAction: KotlinSourceSet.(isTestSourceSet: Boolean) -> Unit = {}
): Pair<KotlinSourceSet, KotlinSourceSet> {
    require(dependsOn.all { it.endsWith("Main").not() })
    require(dependsOn.all { it.endsWith("Test").not() })
    val mainSourceSet = create("${name}Main").apply {
        dependsOn.forEach { dependsOn(getByName("${it}Main")) }
        configureAction(false)
    }
    val testSourceSet = create("${name}Test").apply {
        dependsOn.forEach { getByName("${it}Test") }
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

private fun KotlinMultiplatformExtension.setupAndroidTestSourceSetsAndDependencies() {
    val androidTargets = targets.filterIsInstance<KotlinAndroidTarget>()
    if (androidTargets.isEmpty()) return
    val project = androidTargets.first().project
    project.configure<BaseExtension> {
        sourceSets.getByName("androidTest") { java.srcDir("src/androidInstrumentedTest/kotlin") }
    }
    project.configurations.matching { it.name.startsWith("androidTest") }.all {
        val configurationName = name
        this.dependencies.all {
            val dependency = this
            val isRoboElectric = dependency.group == "org.robolectric"
            if (isRoboElectric.not()) project.dependencies {
                configurationName.replaceFirst("androidTest", newValue = "test")(dependency)
            }
        }
    }
    project.dependencies {
        "androidTestImplementation"("androidx.test:runner:_")
        "testImplementation"("org.robolectric:robolectric:_")
    }
}

private val KotlinTarget.mainSourceSet
    get() = if (this is KotlinAndroidTarget) {
        project.the<KotlinMultiplatformExtension>().sourceSets.androidMain
    } else compilations.main.defaultSourceSet

private inline fun KotlinTarget.mainSourceSet(configure: KotlinSourceSet.() -> Unit) {
    mainSourceSet.configure()
}

private val KotlinTarget.testSourceSet
    get() = if (this is KotlinAndroidTarget) {
        project.the<KotlinMultiplatformExtension>().sourceSets.androidTest
    } else compilations.test.defaultSourceSet

private inline fun KotlinTarget.testSourceSet(configure: KotlinSourceSet.() -> Unit) {
    testSourceSet.configure()
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

val NamedDomainObjectContainer<KotlinSourceSet>.androidDebug: KotlinSourceSet
    inline get() = getByName("androidDebug")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidDebug(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName("androidDebug").apply(configureAction)

val NamedDomainObjectContainer<KotlinSourceSet>.androidTest: KotlinSourceSet
    inline get() = getByName("androidTest")

fun NamedDomainObjectContainer<KotlinSourceSet>.androidTest(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName("androidTest").apply(configureAction)


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

fun NamedDomainObjectContainer<KotlinSourceSet>.allJvmMain(
    configureAction: KotlinSourceSet.() -> Unit
) {
    when {
        isRunningInIde -> {
            findByName("androidMain")?.apply(configureAction)
            findByName("jvmMain")?.apply(configureAction)
        }
        else -> findByName("allJvmMain")?.apply(configureAction)
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
