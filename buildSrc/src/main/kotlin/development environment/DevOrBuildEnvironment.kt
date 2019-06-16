/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch")

import java.util.Locale

val isRunningInIde: Boolean = System.getProperty("idea.active") == "true"
val os: OperatingSystem = when {
    "mac os x" in osName || "darwin" in osName || "osx" in osName -> OperatingSystem.Unix.MacOS
    "sunos" in osName || "solaris" in osName -> OperatingSystem.Unix.Solaris
    "windows" in osName -> OperatingSystem.Windows
    "linux" in osName -> OperatingSystem.Unix.Linux
    "freebsd" in osName -> OperatingSystem.Unix.FreeBSD
    else -> OperatingSystem.Unknown
}

private val osName: String get() = System.getProperty("os.name").toLowerCase(Locale.ROOT)
