/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import org.gradle.api.Project

fun Project.checkNoVersionRanges() {
    configurations.forEach { it ->
        it.resolutionStrategy {
            eachDependency {
                val version = requested.version ?: return@eachDependency
                check('+' !in version) {
                    "Version ranges are forbidden because they would make builds non reproducible."
                }
                check("SNAPSHOT" !in version) {
                    "Snapshot versions are forbidden because they would make builds non reproducible."
                }
            }
        }
    }

}
