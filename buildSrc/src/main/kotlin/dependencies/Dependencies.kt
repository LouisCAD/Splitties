/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection")

/**
 * Nested objects have a corresponding property to allow usage from groovy based gradle scripts.
 *
 * The task `$./gradlew refreshVersions` generates in `gradle.properties` values like
 *
 *    # for a dependency "com.example.group:name:$versionPlaceHolder"
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
@Suppress("MemberVisibilityCanBePrivate")
object Libs {
    /**
     * The actual dependencies versions come from "gradle.properties
     * Read the friendly documentation at https://github.com/jmfayard/buildSrcVersions/issues/77
     * **/
    const val versionPlaceHolder = "+"

    // Actual version comes from "gradle.properties". See ./build.gradle.kts Keep in sync with buildSrc/build.gradle.kts
    var kotlinVersion = "1.3.50"

    const val junit = "junit:junit:$versionPlaceHolder"
    const val roboElectric = "org.robolectric:robolectric:$versionPlaceHolder"
    const val timber = "com.jakewharton.timber:timber:$versionPlaceHolder"
    const val stetho = "com.facebook.stetho:stetho:$versionPlaceHolder"

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
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$versionPlaceHolder"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:$versionPlaceHolder"

        val ALL: List<String> = listOf(stdlibJdk7, testJunit)
    }

    object KotlinX {
        val coroutines = Coroutines

        /**
         * The actual dependency version comes from `gradle.properties`
         * from either `version.org.jetbrains.kotlinx=xxx` or `version.$NAME=xxx` or `version.org.jetbrains.kotlinx..$NAME=xxx`
         **/
        object Coroutines {
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionPlaceHolder"
            const val coreCommon = "org.jetbrains.kotlinx:kotlinx-coroutines-core-common:$versionPlaceHolder"
            const val coreNative = "org.jetbrains.kotlinx:kotlinx-coroutines-core-native:$versionPlaceHolder"
            const val coreJs = "org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$versionPlaceHolder"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$versionPlaceHolder"
            const val playServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:$versionPlaceHolder"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$versionPlaceHolder"

            val ALL: List<String> = listOf(core, coreCommon, coreNative, coreJs, android, playServices, test)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.androidx.$GROUP=xxx` or `version.androidx.$GROUP...$NAME=xxx`
     **/
    object AndroidX {
        val GROUPS = arrayOf(
            "android.arch.core", "android.arch.lifecycle", "android.arch.navigation", "android.arch.paging",
            "android.arch.persistence", "android.arch.persistence.room", "android.arch.work",
            "androidx.activity", "androidx.ads", "androidx.annotation", "androidx.appcompat",
            "androidx.arch.core", "androidx.asynclayoutinflater", "androidx.autofill",
            "androidx.benchmark", "androidx.biometric", "androidx.browser", "androidx.camera",
            "androidx.car", "androidx.cardview", "androidx.collection", "androidx.compose",
            "androidx.concurrent", "androidx.constraintlayout", "androidx.contentpager",
            "androidx.coordinatorlayout", "androidx.core", "androidx.cursoradapter", "androidx.customview",
            "androidx.databinding", "androidx.documentfile", "androidx.drawerlayout", "androidx.dynamicanimation",
            "androidx.emoji", "androidx.enterprise", "androidx.exifinterface", "androidx.fragment",
            "androidx.gridlayout", "androidx.heifwriter", "androidx.interpolator", "androidx.leanback",
            "androidx.legacy", "androidx.lifecycle", "androidx.loader", "androidx.localbroadcastmanager",
            "androidx.media", "androidx.media2", "androidx.mediarouter", "androidx.multidex",
            "androidx.navigation", "androidx.paging", "androidx.palette", "androidx.percentlayout",
            "androidx.preference", "androidx.print", "androidx.recommendation", "androidx.recyclerview",
            "androidx.remotecallback", "androidx.room", "androidx.savedstate", "androidx.security",
            "androidx.sharetarget", "androidx.slice", "androidx.slidingpanelayout", "androidx.sqlite",
            "androidx.swiperefreshlayout", "androidx.test", "androidx.test.espresso", "androidx.test.espresso.idling",
            "androidx.test.ext", "androidx.test.janktesthelper", "androidx.test.services",
            "androidx.test.uiautomator", "androidx.textclassifier", "androidx.transition", "androidx.tvprovider",
            "androidx.ui", "androidx.vectordrawable", "androidx.versionedparcelable", "androidx.viewpager",
            "androidx.viewpager2", "androidx.wear", "androidx.webkit", "androidx.work"
        )

        const val annotation = "androidx.annotation:annotation:$versionPlaceHolder"
        const val appCompat = "androidx.appcompat:appcompat:$versionPlaceHolder"
        const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:$versionPlaceHolder"
        const val browser = "androidx.browser:browser:$versionPlaceHolder"
        const val car = "androidx.car:car:$versionPlaceHolder"
        const val cardView = "androidx.cardview:cardview:$versionPlaceHolder"
        const val collection = "androidx.collection:collection:$versionPlaceHolder"
        const val collectionKtx = "androidx.collection:collection-ktx:$versionPlaceHolder"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:$versionPlaceHolder"
        const val constraintLayoutSolver = "androidx.constraintlayout:constraintlayout-solver:$versionPlaceHolder"
        const val contentPager = "androidx.contentpager:contentpager:$versionPlaceHolder"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:$versionPlaceHolder"
        const val core = "androidx.core:core:$versionPlaceHolder"
        const val coreKtx = "androidx.core:core-ktx:$versionPlaceHolder"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:$versionPlaceHolder"
        const val customView = "androidx.customview:customview:$versionPlaceHolder"
        const val documentFile = "androidx.documentfile:documentfile:$versionPlaceHolder"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:$versionPlaceHolder"
        const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:$versionPlaceHolder"
        const val emoji = "androidx.emoji:emoji:$versionPlaceHolder"
        const val emojiAppCompat = "androidx.emoji:emoji-appcompat:$versionPlaceHolder"
        const val emojiBundler = "androidx.emoji:emoji-bundled:$versionPlaceHolder"
        const val exifInterface = "androidx.exifinterface:exifinterface:$versionPlaceHolder"
        const val fragment = "androidx.fragment:fragment:$versionPlaceHolder"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:$versionPlaceHolder"
        const val gridLayout = "androidx.gridlayout:gridlayout:$versionPlaceHolder"
        const val heifWriter = "androidx.heifwriter:heifwriter:$versionPlaceHolder"
        const val interpolator = "androidx.interpolator:interpolator:$versionPlaceHolder"
        const val leanback = "androidx.leanback:leanback:$versionPlaceHolder"
        const val leanbackPreference = "androidx.leanback:leanback-preference:$versionPlaceHolder"
        const val loader = "androidx.loader:loader:$versionPlaceHolder"
        const val localBroadcastManager = "androidx.localbroadcastmanager:localbroadcastmanager:$versionPlaceHolder"
        const val media = "androidx.media:media:$versionPlaceHolder"
        const val mediaWidget = "androidx.media-widget:media-widget:1.0.0-alpha06"
        const val media2 = "androidx.media2:media2:$versionPlaceHolder"
        const val mediaRouter = "androidx.mediarouter:mediarouter:$versionPlaceHolder"
        const val multidex = "androidx.multidex:multidex:$versionPlaceHolder"
        const val multidexInstrumentation = "androidx.multidex:multidex-instrumentation:$versionPlaceHolder"
        const val palette = "androidx.palette:palette:$versionPlaceHolder"
        const val paletteKtx = "androidx.palette:palette-ktx:$versionPlaceHolder"
        const val percentLayout = "androidx.percentlayout:percentlayout:$versionPlaceHolder"
        const val preference = "androidx.preference:preference:$versionPlaceHolder"
        const val preferenceKtx = "androidx.preference:preference-ktx:$versionPlaceHolder"
        const val print = "androidx.print:print:$versionPlaceHolder"
        const val recommendation = "androidx.recommendation:recommendation:$versionPlaceHolder"
        const val recyclerView = "androidx.recyclerview:recyclerview:$versionPlaceHolder"
        const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:$versionPlaceHolder"
        const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:$versionPlaceHolder"
        const val sqlite = "androidx.sqlite:sqlite:$versionPlaceHolder"
        const val sqliteFramework = "androidx.sqlite:sqlite-framework:$versionPlaceHolder"
        const val sqliteKtx = "androidx.sqlite:sqlite-ktx:$versionPlaceHolder"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$versionPlaceHolder"
        const val transition = "androidx.transition:transition:$versionPlaceHolder"
        const val tvProvider = "androidx.tvprovider:tvprovider:$versionPlaceHolder"
        const val vectorDrawable = "androidx.vectordrawable:vectordrawable:$versionPlaceHolder"
        const val vectorDrawableAnimated = "androidx.vectordrawable:vectordrawable-animated:$versionPlaceHolder"
        const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:$versionPlaceHolder"
        const val viewPager = "androidx.viewpager:viewpager:$versionPlaceHolder"
        const val wear = "androidx.wear:wear:$versionPlaceHolder"
        const val webkit = "androidx.webkit:webkit:$versionPlaceHolder"

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
            const val common = "androidx.lifecycle:lifecycle-common:$versionPlaceHolder"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$versionPlaceHolder"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$versionPlaceHolder"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$versionPlaceHolder"
            const val liveData = "androidx.lifecycle:lifecycle-livedata:$versionPlaceHolder"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core:$versionPlaceHolder"
            const val process = "androidx.lifecycle:lifecycle-process:$versionPlaceHolder"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:$versionPlaceHolder"
            const val reactiveStreamsKtx =
                "androidx.lifecycle:lifecycle-reactivestreams-ktx:$versionPlaceHolder"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:$versionPlaceHolder"
            const val service = "androidx.lifecycle:lifecycle-service:$versionPlaceHolder"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$versionPlaceHolder"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$versionPlaceHolder"

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
            const val common = "androidx.room:room-common:$versionPlaceHolder"
            const val compiler = "androidx.room:room-compiler:$versionPlaceHolder"
            const val guava = "androidx.room:room-guava:$versionPlaceHolder"
            const val migration = "androidx.room:room-migration:$versionPlaceHolder"
            const val runtime = "androidx.room:room-runtime:$versionPlaceHolder"
            const val rxJava2 = "androidx.room:room-rxjava2:$versionPlaceHolder"
            const val testing = "androidx.room:room-testing:$versionPlaceHolder"

            val ALL: List<String> = listOf(common, compiler, guava, migration, runtime, rxJava2, testing)
        }


        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.paging=xxx` or `version.androidx.paging..$NAME=xxx`
         **/
        object Paging {
            const val common = "androidx.paging:paging-common:$versionPlaceHolder"
            const val runtime = "androidx.paging:paging-runtime:$versionPlaceHolder"
            const val rxJava2 = "androidx.paging:paging-rxjava2:$versionPlaceHolder"

            val ALL: List<String> = listOf(common, runtime, rxJava2)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.work=xxx` or `version.androidx.work..$NAME=xxx`
         **/
        object Work {
            const val runtime = "androidx.work:work-runtime:$versionPlaceHolder"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$versionPlaceHolder"
            const val testing = "androidx.work:work-testing:$versionPlaceHolder"

            val ALL: List<String> = listOf(runtime, runtimeKtx, testing)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.navigation=xxx` or `version.androidx.navigation..$NAME=xxx`
         **/
        object Navigation {
            const val common = "androidx.navigation:navigation-common:$versionPlaceHolder"
            const val commonKtx = "androidx.navigation:navigation-common-ktx:$versionPlaceHolder"
            const val fragment = "androidx.navigation:navigation-fragment:$versionPlaceHolder"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$versionPlaceHolder"
            const val runtime = "androidx.navigation:navigation-runtime:$versionPlaceHolder"
            const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$versionPlaceHolder"
            const val ui = "androidx.navigation:navigation-ui:$versionPlaceHolder"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$versionPlaceHolder"
            const val safeArgsGenerator = "androidx.navigation:navigation-safe-args-generator:$versionPlaceHolder"
            const val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$versionPlaceHolder"

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
            const val builders = "androidx.slice:slice-builders:$versionPlaceHolder"
            const val buildersKtx = "androidx.slice:slice-builders-ktx:$versionPlaceHolder"
            const val core = "androidx.slice:slice-core:$versionPlaceHolder"
            const val view = "androidx.slice:slice-view:$versionPlaceHolder"

            val ALL: List<String> = listOf(builders, buildersKtx, core, view)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.arch.core=xxx` or `version.androidx.arch.core..$NAME=xxx`
         **/
        object ArchCore {
            const val common = "androidx.arch.core:core-common:$versionPlaceHolder"
            const val runtime = "androidx.arch.core:core-runtime:$versionPlaceHolder"
            const val testing = "androidx.arch.core:core-testing:$versionPlaceHolder"

            val ALL: List<String> = listOf(common, runtime, testing)
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
         **/
        object Test {
            val espresso = Espresso
            const val core = "androidx.test:core:$versionPlaceHolder"
            const val coreKtx = "androidx.test:core-ktx:$versionPlaceHolder"
            const val monitor = "androidx.test:monitor:$versionPlaceHolder"
            const val orchestrator = "androidx.test:orchestrator:$versionPlaceHolder"
            const val rules = "androidx.test:rules:$versionPlaceHolder"
            const val runner = "androidx.test:runner:$versionPlaceHolder"

            val ext = Ext

            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
             **/
            object Ext {
                const val junit = "androidx.test.ext:junit:$versionPlaceHolder"
                const val junitKtx = "androidx.test.ext:junit-ktx:$versionPlaceHolder"
                const val truth = "androidx.test.ext:truth:$versionPlaceHolder"

                val ALL: List<String> = listOf(junit, junitKtx, truth)
            }

            const val jankTestHelper = "androidx.test.jank:janktesthelper:$versionPlaceHolder"
            const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:$versionPlaceHolder"

            const val services = "androidx.test.services:test-services:$versionPlaceHolder"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:$versionPlaceHolder"
            const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:$versionPlaceHolder"

            val ALL: List<String> = listOf(
                core,
                coreKtx,
                monitor,
                orchestrator,
                runner,
                rules,
                jankTestHelperV23,
                jankTestHelper,
                services,
                uiAutomator,
                uiAutomatorV18
            )

            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test.espresso=xxx` or `version.androidx.test.espresso..$NAME=xxx`
             **/
            object Espresso {
                val idling = Idling
                const val core = "androidx.test.espresso:espresso-core:$versionPlaceHolder"
                const val contrib = "androidx.test.espresso:espresso-contrib:$versionPlaceHolder"
                const val idlingResource =
                    "androidx.test.espresso:espresso-idling-resource:$versionPlaceHolder"
                const val intents = "androidx.test.espresso:espresso-intents:$versionPlaceHolder"
                const val accessibility =
                    "androidx.test.espresso:espresso-accessibility:$versionPlaceHolder"
                const val remote = "androidx.test.espresso:espresso-remote:$versionPlaceHolder"
                const val web = "androidx.test.espresso:espresso-web:$versionPlaceHolder"

                val ALL: List<String> = listOf(core, contrib, idlingResource, intents, accessibility, remote, web)

                /**
                 * The actual dependency version comes from `gradle.properties`
                 * from `version.androidx.test.espresso.idling=xxx` or `version.androidx.test.espresso.idling..$NAME=xxx`
                 **/
                object Idling {
                    const val concurrent =
                        "androidx.test.espresso.idling:idling-concurrent:$versionPlaceHolder"
                    const val net = "androidx.test.espresso.idling:idling-net:$versionPlaceHolder"

                    val ALL: List<String> = listOf(concurrent, net)
                }
            }
        }

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.legacy=xxx` or `version.androidx.legacy..$NAME=xxx`
         **/
        object Legacy {
            const val preferenceV14 = "androidx.legacy:legacy-preference-v14:$versionPlaceHolder"
            const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:$versionPlaceHolder"
            const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:$versionPlaceHolder"
            const val supportV13 = "androidx.legacy:legacy-support-v13:$versionPlaceHolder"
            const val supportV4 = "androidx.legacy:legacy-support-v4:$versionPlaceHolder"

            val ALL: List<String> = listOf(preferenceV14, supportCoreUi, supportCoreUtils, supportV13, supportV4)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.$NAME=xxx` or `version.com.google.android.$GROUP=xxx`
     **/
    object Google {
        const val material = "com.google.android.material:material:$versionPlaceHolder"
        const val wearable = "com.google.android.wearable:wearable:$versionPlaceHolder"
        const val supportWearable = "com.google.android.support:wearable:$versionPlaceHolder"

        val playServices = PlayServices

        val ALL: List<String> = listOf(material, wearable, supportWearable)

        /**
         * The actual dependency version comes from `gradle.properties`
         * from either `version.$NAME=xxx` or `version.com.google.android.gms=xxx`
         **/
        object PlayServices {
            const val base = "com.google.android.gms:play-services-base:$versionPlaceHolder"
            const val auth = "com.google.android.gms:play-services-auth:$versionPlaceHolder"
            const val location = "com.google.android.gms:play-services-location:$versionPlaceHolder"
            const val tasks = "com.google.android.gms:play-services-tasks:$versionPlaceHolder"
            const val vision = "com.google.android.gms:play-services-vision:$versionPlaceHolder"
            const val visionCommon = "com.google.android.gms:play-services-vision-common:$versionPlaceHolder"
            const val wearable = "com.google.android.gms:play-services-wearable:$versionPlaceHolder"

            val ALL: List<String> = listOf(base, auth, location, tasks, vision, visionCommon, wearable)
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.$NAME=xxx` or `version.com.squareup.okhttp=xxx` or `version.com.squreup.retrofit2=xxxx`
     **/
    object Square {
        const val okHttp = "com.squareup.okhttp3:okhttp:$versionPlaceHolder"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$versionPlaceHolder"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$versionPlaceHolder"
        const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:$versionPlaceHolder"
        const val moshi = "com.squareup.moshi:moshi:$versionPlaceHolder"

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
