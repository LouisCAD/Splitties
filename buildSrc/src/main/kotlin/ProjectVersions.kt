/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

object ProjectVersions {
    const val androidBuildTools = "28.0.3"
    const val androidSdk = 28
    const val thisLibrary = "3.0.0-alpha06"
}

val isDevVersion = ProjectVersions.thisLibrary.contains("-dev-")
