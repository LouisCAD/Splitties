@file:Suppress("SpellCheckingInspection")

pluginManagement {
    repositories {
        // If enabled, it allows to test a plugin version released to mavenLocal()
        mavenLocal()
        gradlePluginPortal()
    }

    /**
     * This `resolutionStrategy` allows plugin versions to be configured from `gradle.properties
     * The convention is simply
     *     plugin.$PLUGINID=$PLUGIN_VERSION
     */
    val resolutionStrategyConfig: String? by extra
    resolutionStrategy.eachPlugin {
        val property = "plugin.${requested.id.id}"
        if (extra.has(property) && resolutionStrategyConfig != "false") {
            val version = extra.get(property) as String
            if (resolutionStrategyConfig == "verbose") println("ResolutionStrategy used version=$version from property=$property")
            useVersion(version)
        }
    }
}
/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

arrayOf(
    "activities",
    "alertdialog",
    "alertdialog-appcompat",
    "alertdialog-appcompat-coroutines",
    "appctx",
    "arch-lifecycle",
    "arch-room",
    "bitflags",
    "bundle",
    "checkedlazy",
    "collections",
    "coroutines",
    "dimensions",
    "exceptions",
    "experimental",
    "fragmentargs",
    "fragments",
    "initprovider",
    "intents",
    "lifecycle-coroutines",
    "mainhandler",
    "mainthread",
    "material-colors",
    "material-lists",
    "permissions",
    "preferences",
    "resources",
    "snackbar",
    "stetho-init",
    "systemservices",
    "toast",
    "typesaferecyclerview",
    "views",
    "views-appcompat",
    "views-cardview",
    "views-coroutines",
    "views-coroutines-material",
    "views-dsl",
    "views-dsl-appcompat",
    "views-dsl-constraintlayout",
    "views-dsl-coordinatorlayout",
    "views-dsl-ide-preview",
    "views-dsl-material",
    "views-dsl-recyclerview",
    "views-material",
    "views-recyclerview",
    "views-selectable",
    "views-selectable-appcompat",
    "views-selectable-constraintlayout"
).forEach { include(":modules:$it") }

arrayOf(
    "android-base",
    "android-base-with-views-dsl",
    "android-appcompat",
    "android-appcompat-with-views-dsl",
    "android-material-components",
    "android-material-components-with-views-dsl"
).forEach { include(":fun-packs:$it") }

arrayOf(
    "android-app"
).forEach { include(":samples:$it") }

include("plugin")
include("test-helpers")

enableFeaturePreview("GRADLE_METADATA")
