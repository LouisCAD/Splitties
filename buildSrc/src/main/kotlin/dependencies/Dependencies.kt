/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

/**
 * Nested objects have a corresponding property to allow usage from groovy based gradle scripts.
 *
 * The task `$./gradlew refreshVersions` generates in `gradle.properties` values like
 *
 *    # for a dependency "com.example.group:name:1.0.0"
 *    version.com.example.group..name=1.0.0
 *    version.com.example.group=1.0.0
 *    version.name=1.0.0
 *
 * It also finds automatically available updates.
 *
 * If any of those three Gradle property is defined, the plugin https://github.com/jmfayard/buildSrcVersions
 * will overwrite the version defined in this file by the value from gradle.properties
 *
 * Read the friendly documentation at https://github.com/jmfayard/buildSrcVersions/issues/77
 */
object Libs {
    // Actual version comes from "gradle.properties". Keep in sync with buildSrc/build.gradle.kts
    var kotlinVersion = "1.3.50"

    const val junit = "junit:junit:4.12"
    const val roboElectric = "org.robolectric:robolectric:4.3"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val stetho = "com.facebook.stetho:stetho:1.5.0"

    val ALL: List<String> = listOf(junit, roboElectric, timber, stetho)

    val kotlin = Kotlin
    val kotlinX = KotlinX
    val androidX = AndroidX
    val google = Google

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.org.jetbrains.kotlin=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlin..$NAME=xxx`
     **/
    object Kotlin {
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.50"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:1.3.50"

        val ALL: List<String> = listOf(stdlibJdk7, testJunit)
    }

    object KotlinX {
        val coroutines = Coroutines

        /**
         * The actual dependency version comes from `gradle.properties`
         * from either `version.org.jetbrains.kotlinx=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlinx..$NAME=xxx`
         **/
        object Coroutines {
            private const val artifactPrefix = "org.jetbrains.kotlinx:kotlinx-coroutines"
            const val core = "$artifactPrefix-core:1.3.1"
            const val coreCommon = "$artifactPrefix-core-common:1.3.1"
            const val coreNative = "$artifactPrefix-core-native:1.3.1"
            const val coreJs = "$artifactPrefix-core-js:1.3.1"
            const val android = "$artifactPrefix-android:1.3.1"
            const val playServices = "$artifactPrefix-play-services:1.3.1"
            const val test = "$artifactPrefix-test:1.3.1"

            val ALL: List<String> = listOf(core, coreCommon, coreNative, coreJs, android, playServices, test)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.androidx.$GROUP=xxx` or `version.androidx.$GROUP...$NAME=xxx`
     **/
    object AndroidX {

        const val annotation = "androidx.annotation:annotation:1.0.0"
        const val appCompat = "androidx.appcompat:appcompat:1.0.2"
        const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:1.0.0"
        const val browser = "androidx.browser:browser:1.0.0"
        const val car = "androidx.car:car:1.0.0-alpha5"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val collection = "androidx.collection:collection:1.0.0"
        const val collectionKtx = "androidx.collection:collection-ktx:1.0.0"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:1.1.3"
        const val constraintLayoutSolver =
            "androidx.constraintlayout:constraintlayout-solver:1.1.3"
        const val contentPager = "androidx.contentpager:contentpager:1.0.0"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0"
        const val core = "androidx.core:core:1.0.1"
        const val coreKtx = "androidx.core:core-ktx:1.0.1"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:1.0.0"
        const val customView = "androidx.customview:customview:1.0.0"
        const val documentFile = "androidx.documentfile:documentfile:1.0.0"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:1.0.0"
        const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:1.0.0"
        const val emoji = "androidx.emoji:emoji:1.0.0"
        const val emojiAppCompat = "androidx.emoji:emoji-appcompat:1.0.0"
        const val emojiBundler = "androidx.emoji:emoji-bundled:1.0.0"
        const val exifInterface = "androidx.exifinterface:exifinterface:1.0.0"
        const val fragment = "androidx.fragment:fragment:1.0.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.0.0"
        const val gridLayout = "androidx.gridlayout:gridlayout:1.0.0"
        const val heifWriter = "androidx.heifwriter:heifwriter:1.0.0"
        const val interpolator = "androidx.interpolator:interpolator:1.0.0"
        const val leanback = "androidx.leanback:leanback:1.0.0"
        const val leanbackPreference =
            "androidx.leanback:leanback-preference:1.0.0"
        const val loader = "androidx.loader:loader:1.0.0"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
        const val media = "androidx.media:media:1.0.0"
        const val mediaWidget = "androidx.media-widget:media-widget:1.0.0-alpha5"
        const val media2 = "androidx.media2:media2:1.0.0-alpha02"
        const val mediaRouter = "androidx.mediarouter:mediarouter:1.0.0"
        const val multidex = "androidx.multidex:multidex:2.0.0"
        const val multidexInstrumentation =
            "androidx.multidex:multidex-instrumentation:2.0.0"
        const val palette = "androidx.palette:palette:1.0.0"
        const val paletteKtx = "androidx.palette:palette-ktx:1.0.0"
        const val percentLayout = "androidx.percentlayout:percentlayout:1.0.0"
        const val preference = "androidx.preference:preference:1.0.0"
        const val preferenceKtx = "androidx.preference:preference-ktx:1.0.0"
        const val print = "androidx.print:print:1.0.0"
        const val recommendation = "androidx.recommendation:recommendation:1.0.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
        const val recyclerViewSelection =
            "androidx.recyclerview:recyclerview-selection:1.0.0"
        const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:1.0.0"
        const val sqlite = "androidx.sqlite:sqlite:2.0.0"
        const val sqliteFramework = "androidx.sqlite:sqlite-framework:2.0.0"
        const val sqliteKtx = "androidx.sqlite:sqlite-ktx:2.0.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
        const val transition = "androidx.transition:transition:1.0.0"
        const val tvProvider = "androidx.tvprovider:tvprovider:1.0.0"
        const val vectorDrawable =
            "androidx.vectordrawable:vectordrawable:1.0.0"
        const val vectorDrawableAnimated =
            "androidx.vectordrawable:vectordrawable-animated:1.0.0"
        const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:1.0.0"
        const val viewPager = "androidx.viewpager:viewpager:1.0.0"
        const val wear = "androidx.wear:wear:1.0.0"
        const val webkit = "androidx.webkit:webkit:1.0.0"

        val ALL: List<String> = listOf(
                annotation, appCompat, asyncLayoutInflater, browser,
                car, cardView, collection, collectionKtx, constraintLayout,
                constraintLayoutSolver, contentPager, coordinatorLayout, core, coreKtx,
                cursorAdapter, customView, documentFile, drawerLayout, dynamicAnimation,
                emoji, emojiAppCompat, emojiBundler, exifInterface, fragment,
                fragmentKtx, gridLayout, heifWriter, interpolator, leanback,
                leanbackPreference, loader, localBroadcastManager, media,
                mediaWidget, media2, mediaRouter, multidex, multidexInstrumentation,
                palette, percentLayout, paletteKtx, preference, preferenceKtx, print,
                recommendation, recyclerView, recyclerViewSelection, slidingPaneLayout,
                sqlite, sqliteFramework, sqliteKtx, swipeRefreshLayout, transition,
                tvProvider, vectorDrawable, vectorDrawableAnimated, versionedParcelable,
                wear, webkit, viewPager
        )

        val lifecycle = Lifecycle
        val room = Room
        val paging = Paging
        val work = Work
        val navigation = Navigation
        val slice = Slice
        val archCore = ArchCore
        val test = Test
        val legacy = Legacy

        /**
         * The actual dependency version comes from `gradle.properties`
         * Either `version.androidx.lifecycle` or `version.androidx.lifecycle...$NAME`
         **/
        object Lifecycle {
            const val common = "androidx.lifecycle:lifecycle-common:2.0.0"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.0.0"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:2.0.0"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.0.0"
            const val liveData = "androidx.lifecycle:lifecycle-livedata:2.0.0"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core:2.0.0"
            const val process = "androidx.lifecycle:lifecycle-process:2.0.0"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:2.0.0"
            const val reactiveStreamsKtx =
                "androidx.lifecycle:lifecycle-reactivestreams-ktx:2.0.0"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:2.0.0"
            const val service = "androidx.lifecycle:lifecycle-service:2.0.0"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:2.0.0"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.0.0"

            val ALL: List<String> = listOf(
                    common, commonJava8, compiler, extensions, liveDataCore, liveData,
                    process, reactiveStreams, reactiveStreamsKtx, runtime,
                    service, viewModel, viewModelKtx
            )
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from either `version.androidx.room` or `version.androidx.room...$NAME`
         **/
        object Room {
            const val common = "androidx.room:room-common:2.0.0"
            const val compiler = "androidx.room:room-compiler:2.0.0"
            const val guava = "androidx.room:room-guava:2.0.0"
            const val migration = "androidx.room:room-migration:2.0.0"
            const val runtime = "androidx.room:room-runtime:2.0.0"
            const val rxJava2 = "androidx.room:room-rxjava2:2.0.0"
            const val testing = "androidx.room:room-testing:2.0.0"

            val ALL: List<String> = listOf(common, compiler, guava, migration, runtime, rxJava2, testing)
        }


        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.paging=xxx` or `version.androidx.paging..$NAME=xxx`
         **/
        object Paging {
            const val common = "androidx.paging:paging-common:2.0.0"
            const val runtime = "androidx.paging:paging-runtime:2.0.0"
            const val rxJava2 = "androidx.paging:paging-rxjava2:2.0.0"

            val ALL: List<String> = listOf(common, runtime, rxJava2)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.work=xxx` or `version.androidx.work..$NAME=xxx`
         **/
        object Work {
            const val runtime = "androidx.work:work-runtime:2.0.0"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:2.0.0"
            const val testing = "androidx.work:work-testing:2.0.0"

            val ALL: List<String> = listOf(runtime, runtimeKtx, testing)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.navigation=xxx` or `version.androidx.navigation..$NAME=xxx`
         **/
        object Navigation {
            const val common = "androidx.navigation:navigation-common:2.0.0"
            const val commonKtx = "androidx.navigation:navigation-common-ktx:2.0.0"
            const val fragment = "androidx.navigation:navigation-fragment:2.0.0"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.0.0"
            const val runtime = "androidx.navigation:navigation-runtime:2.0.0"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:2.0.0"
            const val ui = "androidx.navigation:navigation-ui:2.0.0"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:2.0.0"
            const val safeArgsGenerator = "androidx.navigation:navigation-safe-args-generator:2.0.0"
            const val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:2.0.0"

            val ALL: List<String> = listOf(
                    common, commonKtx, fragmentKtx, fragment, runtime, runtimeKtx,
                    ui, uiKtx, safeArgsGenerator, safeArgsGradlePlugin
            )
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.slice=xxx` or `version.androidx.slice..$NAME=xxx`
         **/
        object Slice {
            const val builders = "androidx.slice:slice-builders:1.0.0"
            const val buildersKtx = "androidx.slice:slice-builders-ktx:1.0.0-alpha6"
            const val core = "androidx.slice:slice-core:1.0.0"
            const val view = "androidx.slice:slice-view:1.0.0"

            val ALL: List<String> = listOf(builders, buildersKtx, core, view)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.arch.core=xxx` or `version.androidx.arch.core..$NAME=xxx`
         **/
        object ArchCore {
            const val common = "androidx.arch.core:core-common:2.0.0"
            const val runtime = "androidx.arch.core:core-runtime:2.0.0"
            const val testing = "androidx.arch.core:core-testing:2.0.0"

            val ALL: List<String> = listOf(common, runtime, testing)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
         **/
        object Test {
            val espresso = Espresso
            const val core = "androidx.test:core:1.2.0"
            const val coreKtx = "androidx.test:core-ktx:1.2.0"
            const val monitor = "androidx.test:monitor:1.2.0"
            const val orchestrator = "androidx.test:orchestrator:1.2.0"
            const val rules = "androidx.test:rules:1.2.0"
            const val runner = "androidx.test:runner:1.2.0"

            val ext = Ext

            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
             **/
            object Ext {
                const val junit = "androidx.test.ext:junit:1.1.1"
                const val junitKtx = "androidx.test.ext:junit-ktx:1.1.1"
                const val truth = "androidx.test.ext:truth:1.2.0"

                val ALL: List<String> = listOf(junit, junitKtx, truth)
            }

            const val jankTestHelper = "androidx.test.jank:janktesthelper:1.0.1"
            const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:1.0.1-alpha1"

            const val services = "androidx.test.services:test-services:1.2.0"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"
            const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1"

            val ALL: List<String> = listOf(core, coreKtx, monitor, orchestrator, runner, rules, jankTestHelperV23, jankTestHelper, services, uiAutomator, uiAutomatorV18)

            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test.espresso=xxx` or `version.androidx.test.espresso..$NAME=xxx`
             **/
            object Espresso {
                val idling = Idling
                const val core = "androidx.test.espresso:espresso-core:3.1.1"
                const val contrib = "androidx.test.espresso:espresso-contrib:3.1.1"
                const val idlingResource =
                    "androidx.test.espresso:espresso-idling-resource:3.1.1"
                const val intents = "androidx.test.espresso:espresso-intents:3.1.1"
                const val accessibility =
                    "androidx.test.espresso:espresso-accessibility:3.1.1"
                const val remote = "androidx.test.espresso:espresso-remote:3.1.1"
                const val web = "androidx.test.espresso:espresso-web:3.1.1"

                val ALL: List<String> = listOf(core, contrib, idlingResource, intents, accessibility, remote, web)

                /**
                 * The actual dependency version comes from `gradle.properties`
                 * from `version.androidx.test.espresso.idling=xxx` or `version.androidx.test.espresso.idling..$NAME=xxx`
                 **/
                object Idling {
                    const val concurrent =
                        "androidx.test.espresso.idling:idling-concurrent:3.1.1"
                    const val net = "androidx.test.espresso.idling:idling-net:3.1.1"

                    val ALL: List<String> = listOf(concurrent, net)
                }
            }
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.legacy=xxx` or `version.androidx.legacy..$NAME=xxx`
         **/
        object Legacy {
            const val preferenceV14 = "androidx.legacy:legacy-preference-v14:1.0.0"
            const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:1.0.0"
            const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:1.0.0"
            const val supportV13 = "androidx.legacy:legacy-support-v13:1.0.0"
            const val supportV4 = "androidx.legacy:legacy-support-v4:1.0.0"

            val ALL: List<String> = listOf(preferenceV14, supportCoreUi, supportCoreUtils, supportV13, supportV4)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.$NAME=xxx` or `version.com.google.android.$GROUP=xxx`
     **/
    object Google {
        const val material = "com.google.android.material:material:1.0.0"
        const val wearable = "com.google.android.wearable:wearable:2.4.0"
        const val supportWearable = "com.google.android.support:wearable:2.4.0"

        val playServices = PlayServices

        val ALL: List<String> = listOf(material, wearable, supportWearable)

        /**
         * The actual dependency version comes from `gradle.properties`
         * from either `version.$NAME=xxx` or `version.com.google.android.gms=xxx`
         **/
        object PlayServices {
            const val base = "com.google.android.gms:play-services-base:16.0.1"
            const val auth = "com.google.android.gms:play-services-auth:16.0.1"
            const val location = "com.google.android.gms:play-services-location:16.0.0"
            const val tasks = "com.google.android.gms:play-services-tasks:16.0.1"
            const val vision = "com.google.android.gms:play-services-vision:17.0.2"
            const val visionCommon = "com.google.android.gms:play-services-vision-common:17.0.2"
            const val wearable = "com.google.android.gms:play-services-wearable:16.0.1"

            val ALL: List<String> = listOf(base, auth, location, tasks, vision, visionCommon, wearable)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.$NAME=xxx` or `version.com.squareup.okhttp=xxx` or `version.com.squreup.retrofit2=xxxx`
     **/
    object Square {
        const val okHttp = "com.squareup.okhttp3:okhttp:3.12.0"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.12.0"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:2.5.0"
        const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:2.5.0"
        const val moshi = "com.squareup.moshi:moshi:1.5.0"

        val ALL: List<String> = listOf(okHttp, okHttpLoggingInterceptor, retrofit2ConverterMoshi, retrofit2, moshi)
    }

    @Suppress("unused")
    val ALL_RECURSIVE: List<String> = listOf(
            ALL, Kotlin.ALL, AndroidX.ALL,
            Kotlin.ALL, KotlinX.Coroutines.ALL,
            Square.ALL, Google.PlayServices.ALL, Google.ALL,
            AndroidX.Lifecycle.ALL, AndroidX.Room.ALL,
            AndroidX.Paging.ALL, AndroidX.Work.ALL,
            AndroidX.Navigation.ALL, AndroidX.Slice.ALL,
            AndroidX.ArchCore.ALL, AndroidX.Test.ALL, AndroidX.Legacy.ALL,
            AndroidX.Test.ALL, AndroidX.Test.Espresso.ALL,
            AndroidX.Test.Espresso.Idling.ALL, AndroidX.Test.Ext.ALL
    ).flatten()

    @Suppress("unused")
    private val ALL_ACCESSORS = listOf(
            Google.playServices, androidX, google, AndroidX.room, AndroidX.lifecycle,
            AndroidX.paging, AndroidX.work, AndroidX.navigation, AndroidX.slice, AndroidX.archCore,
            AndroidX.test, AndroidX.legacy,
            AndroidX.Test.espresso, AndroidX.Test.Espresso.idling
    )
}
