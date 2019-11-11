package com.louiscad.splitties

/**
 * The actual dependency version comes from `gradle.properties`
 * from either `version.androidx.$GROUP=xxx` or `version.androidx.$GROUP...$NAME=xxx`
 **/

object AndroidX {
    private const val placeholderVersion = "+"
    private object Versions {
        const val core = placeholderVersion // "1.0.1"
        const val multidex = placeholderVersion // "2.0.0"
        const val palette = placeholderVersion // "1.0.0"
        const val preference = placeholderVersion // "1.0.0"
        const val recyclerView = placeholderVersion // "1.0.0"
        const val sqlite = placeholderVersion // "2.0.0"
        const val vectorDrawable = placeholderVersion // "1.0.0"
        const val leanback = placeholderVersion // "1.0.0"
        const val emoji = placeholderVersion // "1.0.0"
        const val constraintLayout = placeholderVersion // "1.1.3"
        const val collection = placeholderVersion // "1.0.0"
    }
    private val versions = Versions
    const val annotation = "androidx.annotation:annotation:$placeholderVersion" // 1.0.0"
    const val appCompat = "androidx.appcompat:appcompat:$placeholderVersion" // 1.0.2"
    const val asyncLayoutInflater = "androidx.asynclayoutinflater:asynclayoutinflater:$placeholderVersion" // 1.0.0"
    const val browser = "androidx.browser:browser:$placeholderVersion" // 1.0.0"
    const val car = "androidx.car:car:$placeholderVersion" // 1.0.0-alpha5"
    const val cardView = "androidx.cardview:cardview:$placeholderVersion" // 1.0.0"
    const val collection = "androidx.collection:collection:${versions.collection}"
    const val collectionKtx = "androidx.collection:collection-ktx:${versions.collection}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${versions.constraintLayout}"
    const val constraintLayoutSolver =
        "androidx.constraintlayout:constraintlayout-solver:${versions.constraintLayout}"
    const val contentPager = "androidx.contentpager:contentpager:$placeholderVersion" // 1.0.0"
    const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:$placeholderVersion" // 1.0.0"
    const val core = "androidx.core:core:${versions.core}"
    const val coreKtx = "androidx.core:core-ktx:${versions.core}"
    const val cursorAdapter = "androidx.cursoradapter:cursoradapter:$placeholderVersion" // 1.0.0"
    const val customView = "androidx.customview:customview:$placeholderVersion" // 1.0.0"
    const val documentFile = "androidx.documentfile:documentfile:$placeholderVersion" // 1.0.0"
    const val drawerLayout = "androidx.drawerlayout:drawerlayout:$placeholderVersion" // 1.0.0"
    const val dynamicAnimation = "androidx.dynamicanimation:dynamicanimation:$placeholderVersion" // 1.0.0"
    const val emoji = "androidx.emoji:emoji:${versions.emoji}"
    const val emojiAppCompat = "androidx.emoji:emoji-appcompat:${versions.emoji}"
    const val emojiBundler = "androidx.emoji:emoji-bundled:${versions.emoji}"
    const val exifInterface = "androidx.exifinterface:exifinterface:$placeholderVersion" // 1.0.0"
    const val fragment = "androidx.fragment:fragment:$placeholderVersion" // 1.0.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:$placeholderVersion" // 1.0.0"
    const val gridLayout = "androidx.gridlayout:gridlayout:$placeholderVersion" // 1.0.0"
    const val heifWriter = "androidx.heifwriter:heifwriter:$placeholderVersion" // 1.0.0"
    const val interpolator = "androidx.interpolator:interpolator:$placeholderVersion" // 1.0.0"
    const val leanback = "androidx.leanback:leanback:${versions.leanback}"
    const val leanbackPreference =
        "androidx.leanback:leanback-preference:${versions.leanback}"
    const val loader = "androidx.loader:loader:$placeholderVersion" // 1.0.0"
    const val localBroadcastManager =
        "androidx.localbroadcastmanager:localbroadcastmanager:$placeholderVersion" // 1.0.0"
    const val media = "androidx.media:media:$placeholderVersion" // 1.0.0"
    const val mediaWidget = "androidx.media-widget:media-widget:1.0.0-alpha5"
    const val media2 = "androidx.media2:media2:1.0.0-alpha02"
    const val mediaRouter = "androidx.mediarouter:mediarouter:$placeholderVersion" // 1.0.0"
    const val multidex = "androidx.multidex:multidex:${versions.multidex}"
    const val multidexInstrumentation =
        "androidx.multidex:multidex-instrumentation:${versions.multidex}"
    const val palette = "androidx.palette:palette:${versions.palette}"
    const val paletteKtx = "androidx.palette:palette-ktx:${versions.palette}"
    const val percentLayout = "androidx.percentlayout:percentlayout:$placeholderVersion" // 1.0.0"
    const val preference = "androidx.preference:preference:${versions.preference}"
    const val preferenceKtx = "androidx.preference:preference-ktx:${versions.preference}"
    const val print = "androidx.print:print:$placeholderVersion" // 1.0.0"
    const val recommendation = "androidx.recommendation:recommendation:$placeholderVersion" // 1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:${versions.recyclerView}"
    const val recyclerViewSelection =
        "androidx.recyclerview:recyclerview-selection:${versions.recyclerView}"
    const val slidingPaneLayout = "androidx.slidingpanelayout:slidingpanelayout:$placeholderVersion" // 1.0.0"
    const val sqlite = "androidx.sqlite:sqlite:${versions.sqlite}"
    const val sqliteFramework = "androidx.sqlite:sqlite-framework:${versions.sqlite}"
    const val sqliteKtx = "androidx.sqlite:sqlite-ktx:${versions.sqlite}"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:$placeholderVersion" // 1.0.0"
    const val transition = "androidx.transition:transition:$placeholderVersion" // 1.0.0"
    const val tvProvider = "androidx.tvprovider:tvprovider:$placeholderVersion" // 1.0.0"
    const val vectorDrawable =
        "androidx.vectordrawable:vectordrawable:${versions.vectorDrawable}"
    const val vectorDrawableAnimated =
        "androidx.vectordrawable:vectordrawable-animated:${versions.vectorDrawable}"
    const val versionedParcelable = "androidx.versionedparcelable:versionedparcelable:$placeholderVersion" // 1.0.0"
    const val viewPager = "androidx.viewpager:viewpager:$placeholderVersion" // 1.0.0"
    const val wear = "androidx.wear:wear:$placeholderVersion" // 1.0.0"
    const val webkit = "androidx.webkit:webkit:$placeholderVersion" // 1.0.0"


    /**
     * The actual dependency version comes from `gradle.properties`
     * Either `version.androidx.lifecycle` or `version.androidx.lifecycle...$NAME`
     **/
    val lifecycle = Lifecycle
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

    /**
     * The actual dependency version comes from `gradle.properties`
     * from either `version.androidx.room` or `version.androidx.room...$NAME`
     **/
    val room = Room

    object Room {
        private const val artifact = "androidx.room:room"
        private const val version = placeholderVersion // "2.0.0"
        const val common = "$artifact-common:$version"
        const val compiler = "$artifact-compiler:$version"
        const val guava = "$artifact-guava:$version"
        const val migration = "$artifact-migration:$version"
        const val runtime = "$artifact-runtime:$version"
        const val rxJava2 = "$artifact-rxjava2:$version"
        const val testing = "$artifact-testing:$version"
    }


    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.paging=xxx` or `version.androidx.paging..$NAME=xxx`
     **/
    val paging = Paging
    object Paging {
        private const val version = placeholderVersion // "2.0.0"
        const val common = "androidx.paging:paging-common:$version"
        const val runtime = "androidx.paging:paging-runtime:$version"
        const val rxJava2 = "androidx.paging:paging-rxjava2:$version"
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.work=xxx` or `version.androidx.work..$NAME=xxx`
     **/
    val work = Work
    object Work {
        private const val version = placeholderVersion // "2.0.0"
        const val runtime = "androidx.work:work-runtime:$version"
        const val runtimeKtx = "androidx.work:work-runtime-ktx:$version"
        const val testing = "androidx.work:work-testing:$version"
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.navigation=xxx` or `version.androidx.navigation..$NAME=xxx`
     **/
    val navigation = Navigation
    object Navigation {
        private const val version = placeholderVersion // "2.0.0"
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

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.slice=xxx` or `version.androidx.slice..$NAME=xxx`
     **/
    val slice = Slice
    object Slice {
        private const val version = placeholderVersion // "1.0.0"
        const val builders = "androidx.slice:slice-builders:$version"
        const val buildersKtx = "androidx.slice:slice-builders-ktx:1.0.0-alpha6"
        const val core = "androidx.slice:slice-core:$version"
        const val view = "androidx.slice:slice-view:$version"
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.arch.core=xxx` or `version.androidx.arch.core..$NAME=xxx`
     **/
    val archCore = ArchCore
    object ArchCore {
        private const val version = placeholderVersion // "2.0.0"
        const val common = "androidx.arch.core:core-common:$version"
        const val runtime = "androidx.arch.core:core-runtime:$version"
        const val testing = "androidx.arch.core:core-testing:$version"
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
     **/
    val test = Test
    object Test {
        private const val runnerVersion = placeholderVersion // "1.2.0"
        private const val rulesVersion = runnerVersion
        private const val monitorVersion = runnerVersion
        private const val orchestratorVersion = runnerVersion
        private const val coreVersion = placeholderVersion // "1.2.0"
        const val core = "androidx.test:core:$coreVersion"
        const val coreKtx = "androidx.test:core-ktx:$coreVersion"
        const val monitor = "androidx.test:monitor:$monitorVersion"
        const val orchestrator = "androidx.test:orchestrator:$orchestratorVersion"
        const val rules = "androidx.test:rules:$rulesVersion"
        const val runner = "androidx.test:runner:$runnerVersion"


        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.test=xxx` or `version.androidx.test..$NAME=xxx`
         **/
        val ext = Ext
        object Ext {
            private const val extJunitVersion = placeholderVersion // "1.1.1"
            const val junit = "androidx.test.ext:junit:$extJunitVersion"
            const val junitKtx = "androidx.test.ext:junit-ktx:$extJunitVersion"
            const val truth = "androidx.test.ext:truth:$coreVersion"
        }

        const val jankTestHelper = "androidx.test.jank:janktesthelper:$placeholderVersion" // 1.0.1"
        const val jankTestHelperV23 = "androidx.test.jank:janktesthelper-v23:1.0.1-alpha1"

        const val services = "androidx.test.services:test-services:$runnerVersion"

        const val uiAutomator = "androidx.test.uiautomator:uiautomator:$placeholderVersion" // "2.2.0"
        const val uiAutomatorV18 = "androidx.test.uiautomator:uiautomator-v18:2.2.0-alpha1"

        /**
         * The actual dependency version comes from `gradle.properties`
         * from `version.androidx.test.espresso=xxx` or `version.androidx.test.espresso..$NAME=xxx`
         **/
        val espresso = Espresso
        object Espresso {
            private const val version = placeholderVersion // "3.1.1"
            const val core = "androidx.test.espresso:espresso-core:$version"
            const val contrib = "androidx.test.espresso:espresso-contrib:$version"
            const val idlingResource =
                "androidx.test.espresso:espresso-idling-resource:$version"
            const val intents = "androidx.test.espresso:espresso-intents:$version"
            const val accessibility =
                "androidx.test.espresso:espresso-accessibility:$version"
            const val remote = "androidx.test.espresso:espresso-remote:$version"
            const val web = "androidx.test.espresso:espresso-web:$version"


            /**
             * The actual dependency version comes from `gradle.properties`
             * from `version.androidx.test.espresso.idling=xxx` or `version.androidx.test.espresso.idling..$NAME=xxx`
             **/
            val idling = Idling
            object Idling {
                const val concurrent =
                    "androidx.test.espresso.idling:idling-concurrent:$version"
                const val net = "androidx.test.espresso.idling:idling-net:$version"
            }
        }
    }

    /**
     * The actual dependency version comes from `gradle.properties`
     * from `version.androidx.legacy=xxx` or `version.androidx.legacy..$NAME=xxx`
     **/
    val legacy = Legacy
    object Legacy {
        private const val version = placeholderVersion // "1.0.0"
        const val preferenceV14 = "androidx.legacy:legacy-preference-v14:$version"
        const val supportCoreUi = "androidx.legacy:legacy-support-core-ui:$version"
        const val supportCoreUtils = "androidx.legacy:legacy-support-core-utils:$version"
        const val supportV13 = "androidx.legacy:legacy-support-v13:$version"
        const val supportV4 = "androidx.legacy:legacy-support-v4:$version"
    }
}
