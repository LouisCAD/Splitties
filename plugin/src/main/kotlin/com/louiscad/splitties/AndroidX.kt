package com.louiscad.splitties

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
        private const val version = "2.0.0"
        const val common = "androidx.room:room-common:$version"
        const val compiler = "androidx.room:room-compiler:$version"
        const val guava = "androidx.room:room-guava:$version"
        const val migration = "androidx.room:room-migration:$version"
        const val runtime = "androidx.room:room-runtime:$version"
        const val rxJava2 = "androidx.room:room-rxjava2:$version"
        const val testing = "androidx.room:room-testing:$version"


        val ALL: List<String> = listOf(common, compiler, guava, migration, runtime, rxJava2, testing)

    }


    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.paging=xxx` or `version.androidx.paging..$NAME=xxx`
     **/
    object Paging {
        private const val version = "2.0.0"
        const val common = "androidx.paging:paging-common:$version"
        const val runtime = "androidx.paging:paging-runtime:$version"
        const val rxJava2 = "androidx.paging:paging-rxjava2:$version"

        val ALL: List<String> = listOf(common, runtime, rxJava2)
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.work=xxx` or `version.androidx.work..$NAME=xxx`
     **/
    object Work {
        private const val version = "2.0.0"
        const val runtime = "androidx.work:work-runtime:$version"
        const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        const val testing = "androidx.work:work-testing:$version"

        val ALL: List<String> = listOf(runtime, runtimeKtx, testing)
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.navigation=xxx` or `version.androidx.navigation..$NAME=xxx`
     **/
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
        private const val version = "1.0.0"
        const val builders = "androidx.slice:slice-builders:$version"
        const val buildersKtx = "androidx.slice:slice-builders-ktx:1.0.0-alpha6"
        const val core = "androidx.slice:slice-core:$version"
        const val view = "androidx.slice:slice-view:$version"

        val ALL: List<String> = listOf(builders, buildersKtx, core, view)
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.arch.core=xxx` or `version.androidx.arch.core..$NAME=xxx`
     **/
    object ArchCore {
        private const val version = "2.0.0"
        const val common = "androidx.arch.core:core-common:$version"
        const val runtime = "androidx.arch.core:core-runtime:$version"
        const val testing = "androidx.arch.core:core-testing:$version"

        val ALL: List<String> = listOf(common, runtime, testing)
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
     **/
    object Test {
        val espresso = Espresso
        private const val runnerVersion = "1.2.0"
        private const val rulesVersion = runnerVersion
        private const val monitorVersion = runnerVersion
        private const val orchestratorVersion = runnerVersion
        private const val coreVersion = "1.2.0"
        const val core = "androidx.test:core:$coreVersion"
        const val coreKtx = "androidx.test:core-ktx:$coreVersion"
        const val monitor = "androidx.test:monitor:$monitorVersion"
        const val orchestrator = "androidx.test:orchestrator:$orchestratorVersion"
        const val rules = "androidx.test:rules:$rulesVersion"
        const val runner = "androidx.test:runner:$runnerVersion"

        val ext = Ext

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
         **/
        object Ext {
            private const val extJunitVersion = "1.1.1"
            const val junit = "androidx.test.ext:junit:$extJunitVersion"
            const val junitKtx = "androidx.test.ext:junit-ktx:$extJunitVersion"
            const val truth = "androidx.test.ext:truth:$coreVersion"

            val ALL: List<String> = listOf(junit, junitKtx, truth)
        }

        const val jankTestHelper = "androidx.test.jank:janktesthelper:1.0.1"
        const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:1.0.1-alpha1"

        const val services = "androidx.test.services:test-services:$runnerVersion"

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"
        const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1"

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

            val ALL: List<String> = listOf(core, contrib, idlingResource, intents, accessibility, remote, web)

            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test.espresso.idling=xxx` or `version.androidx.test.espresso.idling..$NAME=xxx`
             **/
            object Idling {
                const val concurrent =
                    "androidx.test.espresso.idling:idling-concurrent:$version"
                const val net = "androidx.test.espresso.idling:idling-net:$version"

                val ALL: List<String> = listOf(concurrent, net)
            }
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.legacy=xxx` or `version.androidx.legacy..$NAME=xxx`
     **/
    object Legacy {
        private const val version = "1.0.0"
        const val preferenceV14 = "androidx.legacy:legacy-preference-v14:$version"
        const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:$version"
        const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:$version"
        const val supportV13 = "androidx.legacy:legacy-support-v13:$version"
        const val supportV4 = "androidx.legacy:legacy-support-v4:$version"

        val ALL: List<String> = listOf(preferenceV14, supportCoreUi, supportCoreUtils, supportV13, supportV4)
    }
}
