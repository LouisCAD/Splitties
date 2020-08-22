/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import de.fayard.refreshVersions.bootstrapRefreshVersions

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven(url = "https://dl.bintray.com/kotlin/kotlin-eap")
        maven(url = "https://dl.bintray.com/jmfayard/maven")
    }
}

buildscript {
    repositories {
        gradlePluginPortal()
        maven(url = "https://dl.bintray.com/jmfayard/maven")
    }
    dependencies.classpath("de.fayard.refreshVersions:refreshVersions:0.9.5")
}

plugins {
    id("com.gradle.enterprise").version("3.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = settings.extra.properties["buildScan.termsOfServiceAgree"] as String?
    }
}

bootstrapRefreshVersions()

arrayOf(
    "activities",
    "alertdialog",
    "alertdialog-appcompat",
    "alertdialog-appcompat-coroutines",
    "alertdialog-material",
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

include("test-helpers")
