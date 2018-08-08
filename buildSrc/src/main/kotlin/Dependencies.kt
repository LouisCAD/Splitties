/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
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

@Suppress("unused")
object Versions {
    const val kotlin = "1.2.60"
    const val junit = "4.12"
    const val kotlinxCoroutines = "0.24.0"
    const val splitties = "2.0.0-SNAPSHOT"
    const val okHttp = "3.10.0"
    const val retrofit = "2.4.0"
    const val wearOs = "2.3.0"

    val androidX = AndroidX // To be used with property v declared below.

    object AndroidX {
        const val core = "1.0.0-rc01"
        const val lifecycle = "2.0.0-rc01"
        const val room = "2.0.0-rc01"
        const val paging = "2.0.0-rc01"
        const val slice = "1.0.0-rc01"
        const val multidex = "2.0.0"
        const val palette = "1.0.0-rc01"
        const val preference = "1.0.0-rc01"
        const val recyclerView = "1.0.0-rc01"
        const val sqlite = "2.0.0-rc01"
        const val vectorDrawable = "1.0.0-rc01"
        const val legacy = "1.0.0-rc01"
        const val leanback = "1.0.0-rc01"
        const val emoji = "1.0.0-rc01"
        const val constraintLayout = "1.1.2"
        const val collection = "1.0.0-rc01"
        const val archCore = "2.0.0-rc01"
        const val archWork = "1.0.0-alpha06"
        const val espresso = "3.1.0-alpha4"
        const val test = "1.1.0-alpha4"
    }
}

private val v = Versions // Enables more concise usage.

/**
 * Nested objects have a corresponding property to allow usage from groovy based gradle scripts.
 */
@Suppress("unused")
object Libs {
    const val junit = "junit:junit:${Versions.junit}"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val stetho = "com.facebook.stetho:stetho:1.5.0"

    val kotlin = Kotlin
    val kotlinX = KotlinX
    val androidX = AndroidX
    val google = Google
    val splitties = Splitties

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object KotlinX {
        val coroutines = Coroutines

        object Coroutines {
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinxCoroutines}"
        }
    }

    object AndroidX {
        const val annotation = "androidx.annotation:annotation:1.0.0-rc01"
        const val appCompat = "androidx.appcompat:appcompat:1.0.0-rc01"
        const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:1.0.0-rc01"
        const val browser = "androidx.browser:browser:1.0.0-rc01"
        const val car = "androidx.car:car:1.0.0-alpha5"
        const val cardView = "androidx.cardview:cardview:1.0.0-rc01"
        const val collection = "androidx.collection:collection:${v.androidX.collection}"
        const val collectionKtx = "androidx.collection:collection-ktx:${v.androidX.collection}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${v.androidX.constraintLayout}"
        const val constraintLayoutSolver = "androidx.constraintlayout:constraintlayout-solver:${v.androidX.constraintLayout}"
        const val contentPager = "androidx.contentpager:contentpager:1.0.0-rc01"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0-rc01"
        const val core = "androidx.core:core:${v.androidX.core}"
        const val coreKtx = "androidx.core:core-ktx:${v.androidX.core}"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:1.0.0-rc01"
        const val customView = "androidx.customview:customview:1.0.0-rc01"
        const val documentFile = "androidx.documentfile:documentfile:1.0.0-rc01"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:1.0.0-rc01"
        const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:1.0.0-rc01"
        const val emoji = "androidx.emoji:emoji:${v.androidX.emoji}"
        const val emojiAppCompat = "androidx.emoji:emoji-appcompat:${v.androidX.emoji}"
        const val emojiBundler = "androidx.emoji:emoji-bundled:${v.androidX.emoji}"
        const val exifInterface = "androidx.exifinterface:exifinterface:1.0.0-rc01"
        const val fragment = "androidx.fragment:fragment:1.0.0-rc01"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.0.0-rc01"
        const val gridLayout = "androidx.gridlayout:gridlayout:1.0.0-rc01"
        const val heifWriter = "androidx.heifwriter:heifwriter:1.0.0-rc01"
        const val interpolator = "androidx.interpolator:interpolator:1.0.0-rc01"
        const val leanback = "androidx.leanback:leanback:${v.androidX.leanback}"
        const val leanbackPreference = "androidx.leanback:leanback-preference:${v.androidX.leanback}"
        const val loader = "androidx.loader:loader:1.0.0-rc01"
        const val localBroadcastManager = "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0-rc01"
        const val media = "androidx.media:media:1.0.0-rc01"
        const val mediaWidget = "androidx.media-widget:media-widget:1.0.0-alpha5"
        const val media2 = "androidx.media2:media2:1.0.0-alpha02"
        const val mediaRouter = "androidx.mediarouter:mediarouter:1.0.0-alpha5"
        const val multidex = "androidx.multidex:multidex:${v.androidX.multidex}"
        const val multidexInstrumentation = "androidx.multidex:multidex-instrumentation:${v.androidX.multidex}"
        const val palette = "androidx.palette:palette:${v.androidX.palette}"
        const val paletteKtx = "androidx.palette:palette-ktx:${v.androidX.palette}"
        const val percentLayout = "androidx.percentlayout:percentlayout:1.0.0-rc01"
        const val preference = "androidx.preference:preference:${v.androidX.preference}"
        const val preferenceKtx = "androidx.preference:preference-ktx:${v.androidX.preference}"
        const val print = "androidx.print:print:1.0.0-rc01"
        const val recommendation = "androidx.recommendation:recommendation:1.0.0-rc01"
        const val recyclerView = "androidx.recyclerview:recyclerview:${v.androidX.recyclerView}"
        const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:${v.androidX.recyclerView}"
        const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:1.0.0-rc01"
        const val sqlite = "androidx.sqlite:sqlite:${v.androidX.sqlite}"
        const val sqliteFramework = "androidx.sqlite:sqlite-framework:${v.androidX.sqlite}"
        const val sqliteKtx = "androidx.sqlite:sqlite-ktx:${v.androidX.sqlite}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0-rc01"
        const val transition = "androidx.transition:transition:1.0.0-rc01"
        const val tvProvider = "androidx.tvprovider:tvprovider:1.0.0-rc01"
        const val vectorDrawable = "androidx.vectordrawable:vectordrawable:${v.androidX.vectorDrawable}"
        const val vectorDrawableAnimated = "androidx.vectordrawable:vectordrawable-animated:${v.androidX.vectorDrawable}"
        const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:1.0.0-rc01"
        const val viewPager = "androidx.viewpager:viewpager:1.0.0-rc01"
        const val wear = "androidx.wear:wear:1.0.0-rc01"
        const val webkit = "androidx.webkit:webkit:1.0.0-rc01"

        val lifecycle = Lifecycle
        val room = Room
        val paging = Paging
        val work = Work
        val slice = Slice
        val archCore = ArchCore
        val test = Test
        val legacy = Legacy

        object Lifecycle {
            const val common = "androidx.lifecycle:lifecycle-common:${v.androidX.lifecycle}"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:${v.androidX.lifecycle}"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:${v.androidX.lifecycle}"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:${v.androidX.lifecycle}"
            const val liveData = "androidx.lifecycle:lifecycle-livedata:${v.androidX.lifecycle}"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core:${v.androidX.lifecycle}"
            const val process = "androidx.lifecycle:lifecycle-process:${v.androidX.lifecycle}"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:${v.androidX.lifecycle}"
            const val reactiveStreamsKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:${v.androidX.lifecycle}"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:${v.androidX.lifecycle}"
            const val service = "androidx.lifecycle:lifecycle-service:${v.androidX.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:${v.androidX.lifecycle}"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${v.androidX.lifecycle}"
        }

        object Room {
            const val common = "androidx.room:room-common:${v.androidX.room}"
            const val compiler = "androidx.room:room-compiler:${v.androidX.room}"
            const val guava = "androidx.room:room-guava:${v.androidX.room}"
            const val migration = "androidx.room:room-migration:${v.androidX.room}"
            const val runtime = "androidx.room:room-runtime:${v.androidX.room}"
            const val rxJava2 = "androidx.room:room-rxjava2:${v.androidX.room}"
            const val testing = "androidx.room:room-testing:${v.androidX.room}"
        }

        object Paging {
            const val common = "androidx.paging:paging-common:${v.androidX.paging}"
            const val runtime = "androidx.paging:paging-runtime:${v.androidX.paging}"
            const val rxJava2 = "androidx.paging:paging-rxjava2:${v.androidX.paging}"
        }

        object Work {
            const val firebase = "android.arch.work:work-firebase:${v.androidX.archWork}"
            const val runtime = "android.arch.work:work-runtime:${v.androidX.archWork}"
            const val runtimeKtx = "android.arch.work:work-runtime-ktx:${v.androidX.archWork}"
            const val testing = "android.arch.work:work-testing:${v.androidX.archWork}"
        }

        object Slice {
            const val builders = "androidx.slice:slice-builders:${v.androidX.slice}"
            const val buildersKtx = "androidx.slice:slice-builders-ktx:1.0.0-alpha5"
            const val core = "androidx.slice:slice-core:${v.androidX.slice}"
            const val view = "androidx.slice:slice-view:${v.androidX.slice}"
        }

        object ArchCore {
            const val common = "androidx.arch.core:core-common:${v.androidX.archCore}"
            const val runtime = "androidx.arch.core:core-runtime:${v.androidX.archCore}"
            const val testing = "androidx.arch.core:core-testing:${v.androidX.archCore}"
        }

        object Test {
            val espresso = Espresso
            const val core = "androidx.test:core:${v.androidX.test}"
            const val monitor = "androidx.test:monitor:${v.androidX.test}"
            const val orchestrator = "androidx.test:orchestrator:${v.androidX.test}"
            const val rules = "androidx.test:rules:${v.androidX.test}"
            const val runner = "androidx.test:runner:${v.androidX.test}"

            const val ext = "androidx.test.ext:truth:1.0.0-alpha4"

            const val jankTestHelper = "androidx.test.jank:janktesthelper:1.0.1-alpha4"
            const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:1.0.1-alpha1"

            const val services = "androidx.test.services:test-services:1.1.0-alpha4"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0-alpha4"
            const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1"

            object Espresso {
                val idling = Idling
                const val core = "androidx.test.espresso:espresso-core:${v.androidX.espresso}"
                const val contrib = "androidx.test.espresso:espresso-contrib:${v.androidX.espresso}"
                const val idlingResource = "androidx.test.espresso:espresso-idling-resource:${v.androidX.espresso}"
                const val intents = "androidx.test.espresso:espresso-intents:${v.androidX.espresso}"
                const val accessibility = "androidx.test.espresso:espresso-accessibility:${v.androidX.espresso}"
                const val remote = "androidx.test.espresso:espresso-remote:${v.androidX.espresso}"
                const val web = "androidx.test.espresso:espresso-web:${v.androidX.espresso}"

                object Idling {
                    const val concurrent = "androidx.test.espresso.idling:idling-concurrent:${v.androidX.espresso}"
                    const val net = "androidx.test.espresso.idling:idling-net:${v.androidX.espresso}"
                }
            }
        }

        object Legacy {
            const val preferenceV14 = "androidx.legacy:legacy-preference-v14:${v.androidX.legacy}"
            const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:${v.androidX.legacy}"
            const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:${v.androidX.legacy}"
            const val supportV13 = "androidx.legacy:legacy-support-v13:${v.androidX.legacy}"
            const val supportV4 = "androidx.legacy:legacy-support-v4:${v.androidX.legacy}"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.0.0-rc01"
        const val wearable = "com.google.android.wearable:wearable:${Versions.wearOs}"
        const val supportWearable = "com.google.android.support:wearable:${Versions.wearOs}"

        val playServices = PlayServices

        object PlayServices {
            const val wearable = "com.google.android.gms:play-services-wearable:15.0.1"
        }
    }

    object Square {
    }

    object Splitties {
        private const val base = "com.louiscad.splitties:splitties"
        const val activities = "$base-activities:${Versions.splitties}"
        const val alertDialog = "$base-alertdialog:${Versions.splitties}"
        const val alertDialogAppcompat = "$base-alertdialog-appcompat:${Versions.splitties}"
        const val appCtx = "$base-appctx:${Versions.splitties}"
        const val archLifecycle = "$base-arch-lifecycle:${Versions.splitties}"
        const val archRoom = "$base-arch-room:${Versions.splitties}"
        const val bitFlags = "$base-bitflags:${Versions.splitties}"
        const val bundle = "$base-bundle:${Versions.splitties}"
        const val checkedLazy = "$base-checkedlazy:${Versions.splitties}"
        const val collections = "$base-collections:${Versions.splitties}"
        const val exceptions = "$base-exceptions:${Versions.splitties}"
        const val fragmentArgs = "$base-fragmentargs:${Versions.splitties}"
        const val fragments = "$base-fragments:${Versions.splitties}"
        const val initProvider = "$base-initprovider:${Versions.splitties}"
        const val intents = "$base-intents:${Versions.splitties}"
        const val mainHandler = "$base-mainhandler:${Versions.splitties}"
        const val materialColors = "$base-material-colors:${Versions.splitties}"
        const val materialLists = "$base-material-lists:${Versions.splitties}"
        const val preferences = "$base-preferences:${Versions.splitties}"
        const val resources = "$base-resources:${Versions.splitties}"
        const val selectableViews = "$base-selectableviews:${Versions.splitties}"
        const val selectableViewsAppcompat = "$base-selectableviews-appcompat:${Versions.splitties}"
        const val selectableViewsConstraintLayout = "$base-selectableviews-constraintlayout:${Versions.splitties}"
        const val snackbar = "$base-snackbar:${Versions.splitties}"
        const val stethoInit = "$base-stetho-init:${Versions.splitties}"
        const val systemServices = "$base-systemservices:${Versions.splitties}"
        const val toast = "$base-toast:${Versions.splitties}"
        const val typesaferecyclerview = "$base-typesaferecyclerview:${Versions.splitties}"
        const val uiThread = "$base-uithread:${Versions.splitties}"
        const val viewDsl = "$base-viewdsl:${Versions.splitties}"
        const val viewDslAppcompat = "$base-viewdsl-appcompat:${Versions.splitties}"
        const val viewDslAppcompatStyles = "$base-viewdsl-appcompat-styles:${Versions.splitties}"
        const val viewDslConstraintLayout = "$base-viewdsl-constraintlayout:${Versions.splitties}"
        const val viewDslIdePreview = "$base-viewdsl-ide-preview:${Versions.splitties}"
        const val viewDslMaterial = "$base-viewdsl-material:${Versions.splitties}"
        const val viewDslRecyclerView = "$base-viewdsl-recyclerview:${Versions.splitties}"
        const val views = "$base-views:${Versions.splitties}"
        const val viewsAppCompat = "$base-views-appcompat:${Versions.splitties}"
        const val viewsCardView = "$base-views-cardview:${Versions.splitties}"
        const val viewsMaterial = "$base-views-material:${Versions.splitties}"
        const val viewsRecyclerView = "$base-views-recyclerview:${Versions.splitties}"
    }
}
