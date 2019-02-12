@file:Suppress("SpellCheckingInspection")

/*
* Copyright (c) 2016. Louis Cognault Ayeva Derman
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

listOf(
    ":activities",
    ":alertdialog",
    ":alertdialog-appcompat",
    ":appctx",
    ":arch-lifecycle",
    ":arch-room",
    ":bitflags",
    ":bundle",
    ":checkedlazy",
    ":collections",
    ":dimensions",
    ":exceptions",
    ":experimental",
    ":fragmentargs",
    ":fragments",
    ":initprovider",
    ":intents",
    ":lifecycle-coroutines",
    ":mainhandler",
    ":mainthread",
    ":material-colors",
    ":material-lists",
    ":preferences",
    ":resources",
    ":snackbar",
    ":stetho-init",
    ":systemservices",
    ":toast",
    ":typesaferecyclerview",
    ":views",
    ":views-coroutines",
    ":views-appcompat",
    ":views-cardview",
    ":views-dsl",
    ":views-dsl-appcompat",
    ":views-dsl-constraintlayout",
    ":views-dsl-coordinatorlayout",
    ":views-dsl-ide-preview",
    ":views-dsl-material",
    ":views-dsl-recyclerview",
    ":views-material",
    ":views-recyclerview",
    ":views-selectable",
    ":views-selectable-appcompat",
    ":views-selectable-constraintlayout"
).forEach { include(":modules$it") }
include(":sample")

