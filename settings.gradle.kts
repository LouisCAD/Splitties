/*
 * Copyright 2019-2020 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.7")
}

plugins {
    id("com.gradle.enterprise").version("3.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

bootstrapRefreshVersions()

include {
    "samples" {
        "android-app"()
    }
    "fun-packs" {
        "android-base"()
        "android-base-with-views-dsl"()
        "android-appcompat"()
        "android-appcompat-with-views-dsl"()
        "android-material-components"()
        "android-material-components-with-views-dsl"()
    }
    "test-helpers"()
    if (extra.properties["splitties.bintray.check"].toString().toBoolean()) {
        "tools" { "publication-checker"() }
    }
    "modules" {
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
        "coroutines"()
        "dimensions"()
        "exceptions"()
        "experimental"()
        "fragmentargs"()
        "fragments"()
        "initprovider"()
        "intents"()
        "lifecycle-coroutines"()
        "mainhandler"()
        "mainthread"()
        "material-colors"()
        "material-lists"()
        "permissions"()
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
        "views-recyclerview"()
        "views-selectable"()
        "views-selectable-appcompat"()
        "views-selectable-constraintlayout"()
    }
}

//region include DSL
class ModuleParentScope(
    private val name: String,
    private val parent: ModuleParentScope? = null
) {

    operator fun String.invoke(block: (ModuleParentScope.() -> Unit)? = null) {
        check(startsWith(':').not())
        val moduleName = ":$this"
        val projectName = "$parentalPath$moduleName"
        include(projectName)
        block?.let { buildNode ->
            ModuleParentScope(
                name = moduleName,
                parent = this@ModuleParentScope
            ).buildNode()
        }
    }

    private val parentalPath: String =
        generateSequence(this) { it.parent }
            .map { it.name }.toList().reversed().joinToString("")

}

inline fun include(block: ModuleParentScope.() -> Unit) {
    ModuleParentScope("").block()
}
//endregion
