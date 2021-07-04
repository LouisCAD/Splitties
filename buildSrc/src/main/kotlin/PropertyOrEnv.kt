/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.Project

fun Project.propertyOrEnv(key: String): String {
    return findProperty(key) as String?
        ?: System.getenv(key)
        ?: error("Didn't find any value for the key \"$key\" in Project properties or environment variables.")
}

fun Project.propertyOrEnvOrNull(key: String): String? {
    return findProperty(key) as String? ?: System.getenv(key)
}
