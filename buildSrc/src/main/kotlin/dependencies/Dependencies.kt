/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

/**
 * Nested objects have a corresponding property to allow usage from groovy based gradle scripts.
 */
@Suppress("unused")
object Libs {
    const val junit = "junit:junit:_"
    const val roboElectric = "org.robolectric:robolectric:_"
    const val timber = "com.jakewharton.timber:timber:_"
    const val stetho = "com.facebook.stetho:stetho:_"

    val kotlin = Kotlin
    val kotlinX = KotlinX
    val androidX = AndroidX
    val google = Google

    object Kotlin {
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:_"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:_"
    }

    object KotlinX {
        val coroutines = Coroutines

        object Coroutines {
            private const val version = "_"
            private const val artifactPrefix = "org.jetbrains.kotlinx:kotlinx-coroutines"
            const val core = "$artifactPrefix-core:$version"
            const val coreCommon = "$artifactPrefix-core-common:$version"
            const val coreNative = "$artifactPrefix-core-native:$version"
            const val coreJs = "$artifactPrefix-core-js:$version"
            const val android = "$artifactPrefix-android:$version"
            const val playServices = "$artifactPrefix-play-services:$version"
            const val test = "$artifactPrefix-test:$version"
        }
    }

    /**
     * As of 2019 November the 11th, AndroidX is made of 70 sub-families of artifacts with their own version.
     */
    object AndroidX {
        const val annotation = "androidx.annotation:annotation:_"
        const val appCompat = "androidx.appcompat:appcompat:_"
        const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:_"
        const val browser = "androidx.browser:browser:_"
        const val car = "androidx.car:car:_"
        const val cardView = "androidx.cardview:cardview:_"
        const val collection = "androidx.collection:collection:_"
        const val collectionKtx = "androidx.collection:collection-ktx:_"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:_"
        const val constraintLayoutSolver =
            "androidx.constraintlayout:constraintlayout-solver:_"
        const val contentPager = "androidx.contentpager:contentpager:_"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:_"
        const val core = "androidx.core:core:_"
        const val coreKtx = "androidx.core:core-ktx:_"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:_"
        const val customView = "androidx.customview:customview:_"
        const val documentFile = "androidx.documentfile:documentfile:_"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:_"
        const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:_"
        const val emoji = "androidx.emoji:emoji:_"
        const val emojiAppCompat = "androidx.emoji:emoji-appcompat:_"
        const val emojiBundler = "androidx.emoji:emoji-bundled:_"
        const val exifInterface = "androidx.exifinterface:exifinterface:_"
        const val fragment = "androidx.fragment:fragment:_"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:_"
        const val gridLayout = "androidx.gridlayout:gridlayout:_"
        const val heifWriter = "androidx.heifwriter:heifwriter:_"
        const val interpolator = "androidx.interpolator:interpolator:_"
        const val leanback = "androidx.leanback:leanback:_"
        const val leanbackPreference =
            "androidx.leanback:leanback-preference:_"
        const val loader = "androidx.loader:loader:_"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:_"
        const val media = "androidx.media:media:_"
        const val mediaWidget = "androidx.media-widget:media-widget:_"
        const val media2 = "androidx.media2:media2:_"
        const val mediaRouter = "androidx.mediarouter:mediarouter:_"
        const val multidex = "androidx.multidex:multidex:_"
        const val multidexInstrumentation =
            "androidx.multidex:multidex-instrumentation:_"
        const val palette = "androidx.palette:palette:_"
        const val paletteKtx = "androidx.palette:palette-ktx:_"
        const val percentLayout = "androidx.percentlayout:percentlayout:_"
        const val preference = "androidx.preference:preference:_"
        const val preferenceKtx = "androidx.preference:preference-ktx:_"
        const val print = "androidx.print:print:_"
        const val recommendation = "androidx.recommendation:recommendation:_"
        const val recyclerView = "androidx.recyclerview:recyclerview:_"
        const val recyclerViewSelection =
            "androidx.recyclerview:recyclerview-selection:_"
        const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:_"
        const val sqlite = "androidx.sqlite:sqlite:_"
        const val sqliteFramework = "androidx.sqlite:sqlite-framework:_"
        const val sqliteKtx = "androidx.sqlite:sqlite-ktx:_"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:_"
        const val transition = "androidx.transition:transition:_"
        const val tvProvider = "androidx.tvprovider:tvprovider:_"
        const val vectorDrawable =
            "androidx.vectordrawable:vectordrawable:_"
        const val vectorDrawableAnimated =
            "androidx.vectordrawable:vectordrawable-animated:_"
        const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:_"
        const val viewPager = "androidx.viewpager:viewpager:_"
        const val wear = "androidx.wear:wear:_"
        const val webkit = "androidx.webkit:webkit:_"

        val lifecycle = Lifecycle
        val room = Room
        val paging = Paging
        val work = Work
        val navigation = Navigation
        val slice = Slice
        val archCore = ArchCore
        val test = Test
        val legacy = Legacy

        object Lifecycle {
            const val common = "androidx.lifecycle:lifecycle-common:_"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:_"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:_"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:_"
            const val liveData = "androidx.lifecycle:lifecycle-livedata:_"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-kt:_"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core:_"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:_"
            const val process = "androidx.lifecycle:lifecycle-process:_"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:_"
            const val reactiveStreamsKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:_"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:_"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:_"
            const val service = "androidx.lifecycle:lifecycle-service:_"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:_"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:_"
            const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:_"
        }

        object Room {
            const val common = "androidx.room:room-common:_"
            const val compiler = "androidx.room:room-compiler:_"
            const val guava = "androidx.room:room-guava:_"
            const val migration = "androidx.room:room-migration:_"
            const val runtime = "androidx.room:room-runtime:_"
            const val rxJava2 = "androidx.room:room-rxjava2:_"
            const val testing = "androidx.room:room-testing:_"
        }

        object Paging {
            const val common = "androidx.paging:paging-common:_"
            const val runtime = "androidx.paging:paging-runtime:_"
            const val rxJava2 = "androidx.paging:paging-rxjava2:_"
        }

        object Work {
            const val runtime = "androidx.work:work-runtime:_"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:_"
            const val testing = "androidx.work:work-testing:_"
        }

        object Navigation {
            private const val artifactPrefix = "androidx.navigation:navigation"
            const val common = "$artifactPrefix-common:_"
            const val commonKtx = "$artifactPrefix-common-ktx:_"
            const val fragment = "$artifactPrefix-fragment:_"
            const val fragmentKtx = "$artifactPrefix-fragment-ktx:_"
            const val runtime = "$artifactPrefix-runtime:_"
            const val runtimeKtx = "$artifactPrefix-runtime-ktx:_"
            const val ui = "$artifactPrefix-ui:_"
            const val uiKtx = "$artifactPrefix-ui-ktx:_"
            const val safeArgsGenerator = "$artifactPrefix-safe-args-generator:_"
            const val safeArgsGradlePlugin = "$artifactPrefix-safe-args-gradle-plugin:_"
        }

        object Slice {
            const val builders = "androidx.slice:slice-builders:_"
            const val buildersKtx = "androidx.slice:slice-builders-ktx:_"
            const val core = "androidx.slice:slice-core:_"
            const val view = "androidx.slice:slice-view:_"
        }

        object ArchCore {
            const val common = "androidx.arch.core:core-common:_"
            const val runtime = "androidx.arch.core:core-runtime:_"
            const val testing = "androidx.arch.core:core-testing:_"
        }

        object Test {
            val espresso = Espresso
            private const val runnerVersion = "_"
            private const val rulesVersion = runnerVersion
            private const val monitorVersion = runnerVersion
            private const val orchestratorVersion = runnerVersion
            private const val coreVersion = "_"
            const val core = "androidx.test:core:$coreVersion"
            const val coreKtx = "androidx.test:core-ktx:$coreVersion"
            const val monitor = "androidx.test:monitor:$monitorVersion"
            const val orchestrator = "androidx.test:orchestrator:$orchestratorVersion"
            const val rules = "androidx.test:rules:$rulesVersion"
            const val runner = "androidx.test:runner:$runnerVersion"

            val ext = Ext

            object Ext {
                private const val extJunitVersion = "_"
                const val junit = "androidx.test.ext:junit:$extJunitVersion"
                const val junitKtx = "androidx.test.ext:junit-ktx:$extJunitVersion"
                const val truth = "androidx.test.ext:truth:$coreVersion"
            }

            const val jankTestHelper = "androidx.test.jank:janktesthelper:_"
            const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:_"

            const val services = "androidx.test.services:test-services:$runnerVersion"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:_"
            const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:_"

            object Espresso {
                val idling = Idling
                const val core = "androidx.test.espresso:espresso-core:_"
                const val contrib = "androidx.test.espresso:espresso-contrib:_"
                const val idlingResource =
                    "androidx.test.espresso:espresso-idling-resource:_"
                const val intents = "androidx.test.espresso:espresso-intents:_"
                const val accessibility =
                    "androidx.test.espresso:espresso-accessibility:_"
                const val remote = "androidx.test.espresso:espresso-remote:_"
                const val web = "androidx.test.espresso:espresso-web:_"

                object Idling {
                    const val concurrent =
                        "androidx.test.espresso.idling:idling-concurrent:_"
                    const val net = "androidx.test.espresso.idling:idling-net:_"
                }
            }
        }

        object Legacy {
            const val preferenceV14 = "androidx.legacy:legacy-preference-v14:_"
            const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:_"
            const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:_"
            const val supportV13 = "androidx.legacy:legacy-support-v13:_"
            const val supportV4 = "androidx.legacy:legacy-support-v4:_"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:_"
        private const val wearOsVersion = "_"
        const val wearable = "com.google.android.wearable:wearable:$wearOsVersion"
        const val supportWearable = "com.google.android.support:wearable:$wearOsVersion"

        val playServices = PlayServices

        object PlayServices {
            private const val artifactPrefix = "com.google.android.gms:play-services"
            const val base = "$artifactPrefix-base:_"
            const val auth = "$artifactPrefix-auth:_"
            const val location = "$artifactPrefix-location:_"
            const val tasks = "$artifactPrefix-tasks:_"
            private const val visionVersion = "_"
            const val vision = "$artifactPrefix-vision:$visionVersion"
            const val visionCommon = "$artifactPrefix-vision-common:$visionVersion"
            const val wearable = "$artifactPrefix-wearable:_"
        }
    }

    object Square {
        private const val okHttpVersion = "_"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        private const val retrofitversion = "_"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitversion"
        const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitversion"
        private const val moshiVersion = "_"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    }
}
