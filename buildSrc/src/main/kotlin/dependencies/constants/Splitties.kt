@file:Suppress("PackageDirectoryMismatch", "SpellCheckingInspection", "unused")

import org.gradle.api.Incubating

@Incubating
object Splitties {
    val pack = Packs

    private const val artifactPrefix = "com.louiscad.splitties:splitties"

    object Packs {
        const val androidBase = "$artifactPrefix-fun-pack-android-base:_"
        const val androidBaseWithViewsDsl = "$artifactPrefix-fun-pack-android-base-with-views-dsl:_"
        const val appCompat = "$artifactPrefix-fun-pack-android-appcompat:_"
        const val appCompatWithViewsDsl = "$artifactPrefix-fun-pack-android-appcompat-with-views-dsl:_"
        const val androidMdc = "$artifactPrefix-fun-pack-android-material-components:_"
        const val androidMdcWithViewsDsl = "$artifactPrefix-fun-pack-android-material-components-with-views-dsl:_"
    }

    const val activities = "$artifactPrefix-activities:_"
    const val alertdialog = "$artifactPrefix-alertdialog:_"
    const val alertdialogAppcompat = "$artifactPrefix-alertdialog-appcompat:_"
    const val alertdialogAppcompatCoroutines = "$artifactPrefix-alertdialog-appcompat-coroutines:_"
    const val appctx = "$artifactPrefix-appctx:_"
    const val archLifecycle = "$artifactPrefix-arch-lifecycle:_"
    const val archRoom = "$artifactPrefix-arch-room:_"
    const val bitflags = "$artifactPrefix-bitflags:_"
    const val bundle = "$artifactPrefix-bundle:_"
    const val checkedlazy = "$artifactPrefix-checkedlazy:_"
    const val collections = "$artifactPrefix-collections:_"
    const val dimensions = "$artifactPrefix-dimensions:_"
    const val exceptions = "$artifactPrefix-exceptions:_"
    const val fragments = "$artifactPrefix-fragments:_"
    const val fragmentargs = "$artifactPrefix-fragmentargs:_"
    const val initprovider = "$artifactPrefix-initprovider:_"
    const val intents = "$artifactPrefix-intents:_"
    const val lifecycleCoroutines = "$artifactPrefix-lifecycle-coroutines:_"
    const val mainhandler = "$artifactPrefix-mainhandler:_"
    const val mainthread = "$artifactPrefix-mainthread:_"
    const val materialColors = "$artifactPrefix-material-colors:_"
    const val materialLists = "$artifactPrefix-material-lists:_"
    const val permissions = "$artifactPrefix-permissions:_"
    const val preferences = "$artifactPrefix-preferences:_"
    const val resources = "$artifactPrefix-resources:_"
    const val snackbar = "$artifactPrefix-snackbar:_"
    const val stethoInit = "$artifactPrefix-stetho-init:_"
    const val systemservices = "$artifactPrefix-systemservices:_"
    const val toast = "$artifactPrefix-toast:_"
    const val typesaferecyclerview = "$artifactPrefix-typesaferecyclerview:_"
    const val views = "$artifactPrefix-views:_"
    const val viewsAppcompat = "$artifactPrefix-views-appcompat:_"
    const val viewsCardview = "$artifactPrefix-views-cardview:_"
    const val viewsCoroutines = "$artifactPrefix-views-coroutines:_"
    const val viewsCoroutinesMaterial = "$artifactPrefix-views-coroutines-material:_"
    const val viewsDsl = "$artifactPrefix-views-dsl:_"
    const val viewsDslAppcompat = "$artifactPrefix-views-dsl-appcompat:_"
    const val viewsDslConstraintlayout = "$artifactPrefix-views-dsl-constraintlayout:_"
    const val viewsDslCoordinatorlayout = "$artifactPrefix-views-dsl-coordinatorlayout:_"
    const val viewsDslIdePreview = "$artifactPrefix-views-dsl-ide-preview:_"
    const val viewsDslMaterial = "$artifactPrefix-views-dsl-material:_"
    const val viewsDslRecyclerview = "$artifactPrefix-views-dsl-recyclerview:_"
    const val viewsMaterial = "$artifactPrefix-views-material:_"
    const val viewsRecyclerview = "$artifactPrefix-views-recyclerview:_"
    const val viewsSelectable = "$artifactPrefix-views-selectable:_"
    const val viewsSelectableAppcompat = "$artifactPrefix-views-selectable-appcompat:_"
    const val viewsSelectableConstraintlayout = "$artifactPrefix-views-selectable-constraintlayout:_"
}
