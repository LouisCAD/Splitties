/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@Suppress("unused")
object Versions {
    const val kotlin = "1.3.31" // Don't forget to update in buildSrc/build.gradle.kts too!
}

/**
 * Nested objects have a corresponding property to allow usage from groovy based gradle scripts.
 */
@Suppress("unused")
object Libs {
    const val junit = "junit:junit:4.12"
    const val roboElectric = "org.robolectric:robolectric:4.2"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val stetho = "com.facebook.stetho:stetho:1.5.0"

    val kotlin = Kotlin
    val kotlinX = KotlinX
    val androidX = AndroidX
    val google = Google

    object Kotlin {
        const val stdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
        const val testJunit = "org.jetbrains.kotlin:kotlin-test-junit:${Versions.kotlin}"
    }

    object KotlinX {
        val coroutines = Coroutines

        object Coroutines {
            private const val version = "1.2.1"
            private const val artifactPrefix = "org.jetbrains.kotlinx:kotlinx-coroutines"
            const val core = "$artifactPrefix-core:$version"
            const val coreCommon = "$artifactPrefix-core-common:$version"
            const val android = "$artifactPrefix-android:$version"
            const val playServices = "$artifactPrefix-play-services:$version"
            const val test = "$artifactPrefix-test:$version"
        }
    }

    object AndroidX {
        private object Versions {
            const val core = "1.0.1"
            const val multidex = "2.0.0"
            const val palette = "1.0.0"
            const val preference = "1.0.0"
            const val recyclerView = "1.0.0"
            const val sqlite = "2.0.0"
            const val vectorDrawable = "1.0.0"
            const val leanback = "1.0.0"
            const val emoji = "1.0.0"
            const val constraintLayout = "1.1.3"
            const val collection = "1.0.0"
        }
        private val versions = Versions
        const val annotation = "androidx.annotation:annotation:1.0.0"
        const val appCompat = "androidx.appcompat:appcompat:1.0.2"
        const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:1.0.0"
        const val browser = "androidx.browser:browser:1.0.0"
        const val car = "androidx.car:car:1.0.0-alpha5"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val collection = "androidx.collection:collection:${versions.collection}"
        const val collectionKtx = "androidx.collection:collection-ktx:${versions.collection}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${versions.constraintLayout}"
        const val constraintLayoutSolver =
            "androidx.constraintlayout:constraintlayout-solver:${versions.constraintLayout}"
        const val contentPager = "androidx.contentpager:contentpager:1.0.0"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.0.0"
        const val core = "androidx.core:core:${versions.core}"
        const val coreKtx = "androidx.core:core-ktx:${versions.core}"
        const val cursorAdapter = "androidx.cursoradapter:cursoradapter:1.0.0"
        const val customView = "androidx.customview:customview:1.0.0"
        const val documentFile = "androidx.documentfile:documentfile:1.0.0"
        const val drawerLayout = "androidx.drawerlayout:drawerlayout:1.0.0"
        const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:1.0.0"
        const val emoji = "androidx.emoji:emoji:${versions.emoji}"
        const val emojiAppCompat = "androidx.emoji:emoji-appcompat:${versions.emoji}"
        const val emojiBundler = "androidx.emoji:emoji-bundled:${versions.emoji}"
        const val exifInterface = "androidx.exifinterface:exifinterface:1.0.0"
        const val fragment = "androidx.fragment:fragment:1.0.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.0.0"
        const val gridLayout = "androidx.gridlayout:gridlayout:1.0.0"
        const val heifWriter = "androidx.heifwriter:heifwriter:1.0.0"
        const val interpolator = "androidx.interpolator:interpolator:1.0.0"
        const val leanback = "androidx.leanback:leanback:${versions.leanback}"
        const val leanbackPreference =
            "androidx.leanback:leanback-preference:${versions.leanback}"
        const val loader = "androidx.loader:loader:1.0.0"
        const val localBroadcastManager =
            "androidx.localbroadcastmanager:localbroadcastmanager:1.0.0"
        const val media = "androidx.media:media:1.0.0"
        const val mediaWidget = "androidx.media-widget:media-widget:1.0.0-alpha5"
        const val media2 = "androidx.media2:media2:1.0.0-alpha02"
        const val mediaRouter = "androidx.mediarouter:mediarouter:1.0.0"
        const val multidex = "androidx.multidex:multidex:${versions.multidex}"
        const val multidexInstrumentation =
            "androidx.multidex:multidex-instrumentation:${versions.multidex}"
        const val palette = "androidx.palette:palette:${versions.palette}"
        const val paletteKtx = "androidx.palette:palette-ktx:${versions.palette}"
        const val percentLayout = "androidx.percentlayout:percentlayout:1.0.0"
        const val preference = "androidx.preference:preference:${versions.preference}"
        const val preferenceKtx = "androidx.preference:preference-ktx:${versions.preference}"
        const val print = "androidx.print:print:1.0.0"
        const val recommendation = "androidx.recommendation:recommendation:1.0.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:${versions.recyclerView}"
        const val recyclerViewSelection =
            "androidx.recyclerview:recyclerview-selection:${versions.recyclerView}"
        const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:1.0.0"
        const val sqlite = "androidx.sqlite:sqlite:${versions.sqlite}"
        const val sqliteFramework = "androidx.sqlite:sqlite-framework:${versions.sqlite}"
        const val sqliteKtx = "androidx.sqlite:sqlite-ktx:${versions.sqlite}"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.0.0"
        const val transition = "androidx.transition:transition:1.0.0"
        const val tvProvider = "androidx.tvprovider:tvprovider:1.0.0"
        const val vectorDrawable =
            "androidx.vectordrawable:vectordrawable:${versions.vectorDrawable}"
        const val vectorDrawableAnimated =
            "androidx.vectordrawable:vectordrawable-animated:${versions.vectorDrawable}"
        const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:1.0.0"
        const val viewPager = "androidx.viewpager:viewpager:1.0.0"
        const val wear = "androidx.wear:wear:1.0.0"
        const val webkit = "androidx.webkit:webkit:1.0.0"

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
            private const val version = "2.0.0"
            const val common = "androidx.lifecycle:lifecycle-common:$version"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata:$version"
            const val liveDataCore = "androidx.lifecycle:lifecycle-livedata-core:$version"
            const val process = "androidx.lifecycle:lifecycle-process:$version"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:$version"
            const val reactiveStreamsKtx =
                "androidx.lifecycle:lifecycle-reactivestreams-ktx:$version"
            const val runtime = "androidx.lifecycle:lifecycle-runtime:$version"
            const val service = "androidx.lifecycle:lifecycle-service:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        }

        object Room {
            private const val version = "2.0.0"
            const val common = "androidx.room:room-common:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val guava = "androidx.room:room-guava:$version"
            const val migration = "androidx.room:room-migration:$version"
            const val runtime = "androidx.room:room-runtime:$version"
            const val rxJava2 = "androidx.room:room-rxjava2:$version"
            const val testing = "androidx.room:room-testing:$version"
        }

        object Paging {
            private const val version = "2.0.0"
            const val common = "androidx.paging:paging-common:$version"
            const val runtime = "androidx.paging:paging-runtime:$version"
            const val rxJava2 = "androidx.paging:paging-rxjava2:$version"
        }

        object Work {
            private const val version = "2.0.0"
            const val runtime = "androidx.work:work-runtime:$version"
            const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
            const val testing = "androidx.work:work-testing:$version"
        }

        object Navigation {
            private const val version = "2.0.0"
            private const val artifactPrefix = "androidx.navigation:navigation"
            const val common = "$artifactPrefix-common:$version"
            const val commonKtx = "$artifactPrefix-common-ktx:$version"
            const val fragment = "$artifactPrefix-fragment:$version"
            const val fragmentKtx = "$artifactPrefix-fragment-ktx:$version"
            const val runtime = "$artifactPrefix-runtime:$version"
            const val runtimeKtx = "$artifactPrefix-runtime-ktx:$version"
            const val ui = "$artifactPrefix-ui:$version"
            const val uiKtx = "$artifactPrefix-ui-ktx:$version"
            const val safeArgsGenerator = "$artifactPrefix-safe-args-generator:$version"
            const val safeArgsGradlePlugin = "$artifactPrefix-safe-args-gradle-plugin:$version"
        }

        object Slice {
            private const val version = "1.0.0"
            const val builders = "androidx.slice:slice-builders:$version"
            const val buildersKtx = "androidx.slice:slice-builders-ktx:1.0.0-alpha6"
            const val core = "androidx.slice:slice-core:$version"
            const val view = "androidx.slice:slice-view:$version"
        }

        object ArchCore {
            private const val version = "2.0.0"
            const val common = "androidx.arch.core:core-common:$version"
            const val runtime = "androidx.arch.core:core-runtime:$version"
            const val testing = "androidx.arch.core:core-testing:$version"
        }

        object Test {
            val espresso = Espresso
            private const val runnerVersion = "1.1.1"
            private const val rulesVersion = runnerVersion
            private const val monitorVersion = runnerVersion
            private const val orchestratorVersion = runnerVersion
            private const val coreVersion = "1.1.0"
            const val core = "androidx.test:core:$coreVersion"
            const val coreKtx = "androidx.test:core-ktx:$coreVersion"
            const val monitor = "androidx.test:monitor:$monitorVersion"
            const val orchestrator = "androidx.test:orchestrator:$orchestratorVersion"
            const val rules = "androidx.test:rules:$rulesVersion"
            const val runner = "androidx.test:runner:$runnerVersion"

            val ext = Ext

            object Ext {
                private const val version = coreVersion
                const val junit = "androidx.test.ext:junit:$version"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
                const val truth = "androidx.test.ext:truth:$version"
            }

            const val jankTestHelper = "androidx.test.jank:janktesthelper:1.0.1"
            const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:1.0.1-alpha1"

            const val services = "androidx.test.services:test-services:1.1.0"

            const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"
            const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1"

            object Espresso {
                val idling = Idling
                private const val version = "3.1.1"
                const val core = "androidx.test.espresso:espresso-core:$version"
                const val contrib = "androidx.test.espresso:espresso-contrib:$version"
                const val idlingResource =
                    "androidx.test.espresso:espresso-idling-resource:$version"
                const val intents = "androidx.test.espresso:espresso-intents:$version"
                const val accessibility =
                    "androidx.test.espresso:espresso-accessibility:$version"
                const val remote = "androidx.test.espresso:espresso-remote:$version"
                const val web = "androidx.test.espresso:espresso-web:$version"

                object Idling {
                    const val concurrent =
                        "androidx.test.espresso.idling:idling-concurrent:$version"
                    const val net = "androidx.test.espresso.idling:idling-net:$version"
                }
            }
        }

        object Legacy {
            private const val version = "1.0.0"
            const val preferenceV14 = "androidx.legacy:legacy-preference-v14:$version"
            const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:$version"
            const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:$version"
            const val supportV13 = "androidx.legacy:legacy-support-v13:$version"
            const val supportV4 = "androidx.legacy:legacy-support-v4:$version"
        }
    }

    object Google {
        const val material = "com.google.android.material:material:1.0.0"
        private const val wearOsVersion = "2.4.0"
        const val wearable = "com.google.android.wearable:wearable:$wearOsVersion"
        const val supportWearable = "com.google.android.support:wearable:$wearOsVersion"

        val playServices = PlayServices

        object PlayServices {
            private const val artifactPrefix = "com.google.android.gms:play-services"
            const val base = "$artifactPrefix-base:16.0.1"
            const val auth = "$artifactPrefix-auth:16.0.1"
            const val location = "$artifactPrefix-location:16.0.0"
            const val tasks = "$artifactPrefix-tasks:16.0.1"
            private const val visionVersion = "17.0.2"
            const val vision = "$artifactPrefix-vision:$visionVersion"
            const val visionCommon = "$artifactPrefix-vision-common:$visionVersion"
            const val wearable = "$artifactPrefix-wearable:16.0.1"
        }
    }

    object Square {
        private const val okHttpVersion = "3.12.0"
        const val okHttp = "com.squareup.okhttp3:okhttp:$okHttpVersion"
        const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$okHttpVersion"
        private const val retrofitversion = "2.5.0"
        const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitversion"
        const val retrofit2ConverterMoshi = "com.squareup.retrofit2:converter-moshi:$retrofitversion"
        private const val moshiVersion = "1.5.0"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
    }
}
