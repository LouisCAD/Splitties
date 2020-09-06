/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

import org.gradle.api.NamedDomainObjectContainer
import org.jetbrains.kotlin.gradle.dsl.KotlinTargetContainerWithPresetFunctions
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
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


val NamedDomainObjectContainer<KotlinSourceSet>.commonMain: KotlinSourceSet
    inline get() = getByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME)

fun NamedDomainObjectContainer<KotlinSourceSet>.commonMain(
    configureAction: KotlinSourceSet.() -> Unit
): KotlinSourceSet = getByName(KotlinSourceSet.COMMON_MAIN_SOURCE_SET_NAME, configureAction)


val NamedDomainObjectContainer<KotlinSourceSet>.jvmMain: KotlinSourceSet
    inline get() = getByName("jvmMain")

val NamedDomainObjectContainer<KotlinSourceSet>.jsMain: KotlinSourceSet
    inline get() = getByName("jsMain")

val NamedDomainObjectContainer<KotlinSourceSet>.androidMain: KotlinSourceSet
    inline get() = getByName("androidMain")
