/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")


pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

plugins {
    id("com.gradle.enterprise").version("3.15")
    id("de.fayard.refreshVersions") version "0.60.3"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}


include {
    "samples" {
        "android-app"()
    }
    "fun-packs"(childrenPrefix = "splitties-fun-pack-") {
        "android-base"()
        "android-base-with-views-dsl"()
        "android-appcompat"()
        "android-appcompat-with-views-dsl"()
        "android-material-components"()
        "android-material-components-with-views-dsl"()
    }
    "test-helpers"()

    val checkPublication = "check_publication".let { key ->
        extra.properties[key] ?: System.getenv(key)
    } == "true"

    if (checkPublication) "tools" { "publication-checker"() }

    "modules"(childrenPrefix = "splitties-") {
        "activities"()
        "alertdialog"()
        "alertdialog-appcompat"()
        "alertdialog-appcompat-coroutines"()
        "alertdialog-material"()
        "appctx"()
        "arch-lifecycle"()
        "arch-room"()
        "bitflags"()
        "bundle"()
        "checkedlazy"()
        "collections"()
        "compose" {
            "callable-state"()
        }
        "coroutines"()
        "dimensions"()
        "exceptions"()
        "experimental"()
        "fragmentargs"()
        "fragments"()
        "intents"()
        "lifecycle-coroutines"()
        "mainhandler"()
        "mainthread"()
        "material-colors"()
        "material-lists"()
        "permissions" {
            "core"()
            "compose"()
            "fragment"()
        }
        "preferences"()
        "resources"()
        "snackbar"()
        "stetho-init"()
        "systemservices"()
        "toast"()
        "typesaferecyclerview"()
        "views"()
        "views-appcompat"()
        "views-cardview"()
        "views-coroutines"()
        "views-coroutines-material"()
        "views-dsl"()
        "views-dsl-appcompat"()
        "views-dsl-constraintlayout"()
        "views-dsl-coordinatorlayout"()
        "views-dsl-ide-preview"()
        "views-dsl-material"()
        "views-dsl-recyclerview"()
        "views-material"()
        "views-recyclerview" {
            "compose"()
        }
        "views-selectable"()
        "views-selectable-appcompat"()
        "views-selectable-constraintlayout"()
    }
}

//region include DSL
class ModuleParentScope(
    private val name: String,
    private val parent: ModuleParentScope? = null,
    private val prefix: String
) {

    operator fun String.invoke(
        childrenPrefix: String = "",
        block: (ModuleParentScope.() -> Unit)? = null
    ) {
        check(startsWith(':').not())
        if (childrenPrefix.isNotEmpty()) check(block !== null)
        val moduleName = ":$this"
        val projectPath = "$parentalPath$moduleName"
        include(projectPath)
        val projectName = if (prefix.isNotEmpty()) {
            project(projectPath).let {
                val newName = "$prefix${it.name}"
                it.name = newName
                ":$newName"
            }
        } else moduleName
        block?.let { buildNode ->
            ModuleParentScope(
                name = projectName,
                parent = this@ModuleParentScope,
                prefix = when {
                    childrenPrefix.isNotEmpty() -> childrenPrefix
                    prefix.isEmpty() -> ""
                    else -> "$prefix$this-"
                }
            ).buildNode()
        }
    }

    private val parentalPath: String =
        generateSequence(this) { it.parent }
            .map { it.name }.toList().reversed().joinToString("")

}

inline fun include(prefix: String = "", block: ModuleParentScope.() -> Unit) {
    ModuleParentScope(name = "", prefix = prefix).block()
}
//endregion
