/***
 * This file is only there to make sure that the gradle-versions-plugin
 * finds all dependencies used in this project.
 * It does not contribute in any matter to the output of the plugin.
 * It can be deleted as soon as this bug is resolved
 *
 * Unable to resolve latest versions for Kotlin MPP projects #334
 * https://github.com/ben-manes/gradle-versions-plugin/issues/334
 */

configurations.create("dummy")

fun DependencyHandler.`dummy`(vararg deps: String) =
        deps.forEach { dependencyNotation -> add("dummy", dependencyNotation) }

dependencies {
    dummy("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.13.0")
    dummy(Libs.junit, Libs.roboElectric, Libs.timber, Libs.stetho)
    with(Libs.Kotlin) {
        dummy(stdlibJdk7, testJunit)
    }
    with(Libs.KotlinX.Coroutines) {
        dummy(core, coreCommon, coreNative, coreJs, android, playServices, test)
    }
    with(Libs.androidX) {
        dummy(annotation, appCompat, asyncLayoutInflater, browser, car)
        dummy(cardView, collection, collectionKtx, constraintLayout, constraintLayoutSolver)
        dummy(contentPager, coordinatorLayout, core, coreKtx, cursorAdapter, customView)
        dummy(documentFile, drawerLayout, dynamicAnimation)
        dummy(emoji, emojiAppCompat, emojiBundler, exifInterface)
        dummy(fragment, fragmentKtx, gridLayout, heifWriter, leanback, leanbackPreference)
        dummy(loader, localBroadcastManager, media, mediaWidget, media2, mediaRouter, multidex)
        dummy(multidexInstrumentation, palette, paletteKtx, percentLayout, preference, preferenceKtx)
        dummy(print, recommendation, recyclerView, recyclerViewSelection, slidingPaneLayout)
        dummy(sqlite, sqliteFramework, sqliteKtx, swipeRefreshLayout, transition, tvProvider)
        dummy(vectorDrawable, vectorDrawableAnimated, versionedParcelable, viewPager, wear, webkit)
    }
    with(Libs.AndroidX.Lifecycle) {
        dummy(common, commonJava8, compiler, extensions, liveData, liveDataCore)
        dummy(process, reactiveStreams, reactiveStreamsKtx, runtime, service)
        dummy(viewModel, viewModelKtx)
    }
    with(Libs.AndroidX.Room) {
        dummy(common, compiler, guava, migration, runtime, rxJava2, testing)
    }
    with(Libs.AndroidX.Work) {
        dummy(runtime, runtimeKtx, testing)
    }
    with(Libs.AndroidX.Navigation) {
        dummy(common, commonKtx, fragment, fragmentKtx, runtime, runtimeKtx)
        dummy(ui, uiKtx, safeArgsGenerator, safeArgsGradlePlugin)
    }
    with(Libs.AndroidX.Slice) {
        dummy(builders, buildersKtx, core, view)
    }
    with(Libs.AndroidX.ArchCore) {
        dummy(common, runtime, testing)
    }
    with(Libs.AndroidX.Test) {
        dummy(core, coreKtx, monitor, orchestrator, rules, runner)
        dummy(jankTestHelper, jankTestHelperV23, services, uiAutomator, uiAutomatorV18)
    }
    with(Libs.AndroidX.Test.Ext) {
        dummy(junit, junitKtx, truth)
    }
    with(Libs.AndroidX.Test.Espresso) {
        dummy(core, contrib, idlingResource, intents, accessibility, remote, web)
        dummy(Libs.AndroidX.Test.Espresso.Idling.concurrent, Libs.AndroidX.Test.Espresso.Idling.net)
    }
    with(Libs.AndroidX.Legacy) {
        dummy(preferenceV14, supportCoreUi, supportCoreUtils, supportV13, supportV4)
    }
    with(Libs.Google) {
        dummy(material, wearable, supportWearable)
    }
    with(Libs.Google.PlayServices) {
        dummy(base, auth, location, tasks, vision, visionCommon, wearable)
    }
    with(Libs.Square) {
        dummy(okHttp, okHttpLoggingInterceptor, retrofit2, retrofit2ConverterMoshi, moshi)
    }
}