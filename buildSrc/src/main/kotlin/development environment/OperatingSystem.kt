/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

sealed class OperatingSystem {
    sealed class Unix : OperatingSystem() {
        object Linux : Unix()
        object MacOS : Unix()
        object FreeBSD : Unix()
        object Solaris : Unix()
    }

    object Windows : OperatingSystem()
    object Unknown : OperatingSystem()
}

val OperatingSystem.isMacOS get() = this == OperatingSystem.Unix.MacOS
val OperatingSystem.isLinux get() = this == OperatingSystem.Unix.Linux
val OperatingSystem.isWindows get() = this == OperatingSystem.Windows
