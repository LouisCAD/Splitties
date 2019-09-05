@file:Suppress("SpellCheckingInspection")
// This sould be deleted once buildSrcVersions 0.5.0 is published
pluginManagement {
    repositories {
        val localGradleRepo = "/Users/jmfayard/akelius/buildSrcVersions/plugin/build/repository"
        if (File(localGradleRepo).exists()) maven { setUrl(localGradleRepo) }
        gradlePluginPortal()
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

include(":tests")

enableFeaturePreview("GRADLE_METADATA")
