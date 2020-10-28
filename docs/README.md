# Splitties

Splitties is a collection of small Kotlin multiplatform libraries (with Android as first target).

These libraries are intended to reduce the amount of code you have to write, freeing code reading and writing
time, so you can focus more on what you want to build for your users (even if you're the only one), or
have more time to have `fun`.

This project is named "Splitties" because it is split in small modules,
distributed as independent libraries, so you can add only the ones
you need to your project/module, helping reduce the size of the final binary that users devices
will need to download and keep in the limited storage (BTW, everything is limited).

Some Android targeting modules have a content similar to what [Anko](https://github.com/Kotlin/anko) offers.
See [a short comparison of Splitties with Anko here](Comparison_with_anko.md).

Each module has been designed to have a **small footprint** and be as **efficient** as possible.

## All the multiplatform [splits](#what-is-a-split "What is a split in Splitties?")

Currently, only JVM (including Android) and JS are supported, but future Kotlin/Native support is considered (you can subscribe to [this issue](https://github.com/LouisCAD/Splitties/issues/189) to get updated on that).

- **[Bit Flags:](3/bitflags)** `hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`,
`Short`, `Byte`, and their unsigned counterparts.
- **[Collections:](3/collections)** `forEach` for `List`s without `Iterator` allocation.
- **[Coroutines:](3/coroutines)** General purpose extensions to kotlinx.coroutines.

## All the Android [splits](#what-is-a-split "What is a split in Splitties?")

- **[Activities:](3/activities)** Start activities with minimal boilerplate.
- **[Alert Dialog:](3/alertdialog)** Create simple alert dialogs with simple code.
- **[Alert Dialog AppCompat:](3/alertdialog-appcompat)** AppCompat version of
[Alert Dialog](3/alertdialog).
- **[Alert Dialog AppCompat Coroutines:](3/alertdialog-appcompat-coroutines)**
`showAndAwait` extension functions for AppCompat AlertDialog.
- **[Alert Dialog Material:](3/alertdialog-appcompat)** Material Components extension of
[Alert Dialog AppCompat](3/alertdialog-appcompat).
- **[App Context:](3/appctx)** Always have your application `Context` at hand with `appCtx`.
- **[Arch Lifecycle:](3/arch-lifecycle)** Extensions to get `ViewModel`s, use `LiveData` and
observe `Lifecycle`s.
- **[Arch Room:](3/arch-room)** Room helpers to instantiate your DB and perform
transactions in Kotlin.
- **[Bundle:](3/bundle)** `BundleSpec` to use `Bundle` with property syntax for `Intent`
extras and more.
- **[Checked Lazy:](3/checkedlazy)** `mainThreadLazy` that checks property access on
main thread, and `checkedLazy` to make your own variant.
- **[Dimensions:](3/dimensions)** Android `dp` extensions for `View` and `Context`.
Particularly handy when using [Views DSL](3/views-dsl).
- **[Exceptions:](3/exceptions)** `unexpectedValue(…)`, `unsupportedAction(…)` and similar
functions that return `Nothing`.
- **[Fragments:](3/fragments)** Start activities from fragments and do transactions with
minimal boilerplate.
- **[Fragment Args:](3/fragmentargs)** Fragment arguments without ceremony thanks to
delegated properties.
- **[Init Provider:](3/initprovider)** Base class for `ContentProvider`s used for automatic
initialization purposes.
- **[Intents:](3/intents)** Transform `companion object`s into powerful typesafe intent specs,
and create `PendingIntent`s the clean and easy way.
- **[Lifecycle Coroutines:](3/lifecycle-coroutines)** Coroutines integration with AndroidX
[`Lifecycle`](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle).
- **[Main Handler:](3/mainhandler)** Top-level `mainHandler` property to stop allocating multiple
`Handler`s for main `Looper`.
- **[Main Thread:](3/mainthread)** Properties and precondition checkers related to Android main thread.
- **[Material Colors:](3/material-colors)** [2014 Material Design color palettes](
https://material.io/design/color/#tools-for-picking-colors) as color resources.
- **[Material Lists:](3/material-lists)** List item Views implementing [Material Design guidelines](
https://material.io/guidelines) (perfect for usage in a `RecyclerView`).
- **[Permissions:](3/permissions)** Request runtime permissions without polluting your codebase.
- **[Preferences:](3/preferences)** Property syntax for Android's `SharedPreferences` or iOS/macOS `NSUserDefaults`.
- **[Resources:](3/resources)** Extensions to get resources like strings, colors or drawables easily,
with support for themed attributes.
- **[Selectable Views:](3/views-selectable)** Selectable Views with `foreground` property before
API 23.
- **[Selectable Views AppCompat:](3/views-selectable-appcompat)** [Selectable Views](3/views-selectable)
for AppCompatTextView.
- **[Selectable Views ConstraintLayout:](3/views-selectable-constraintlayout)**
[Selectable Views](3/views-selectable) for ConstraintLayout.
- **[Snackbar:](3/snackbar)** Grab a snack without ceremony with `snack(…)` and `longSnack(…)`.
- **[Stetho init:](3/stetho-init)** Have [Stetho](https://github.com/facebook/stetho) for your debug
builds, without writing any code!
- **[System Services:](3/systemservices)** No more
`context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.
- **[Toast:](3/toast)** Show a toast by just calling `toast(yourText)`, and dodge [API 25
`BadTokenException`](https://github.com/drakeet/ToastCompat#why).
- **[Typesafe RecyclerView:](3/typesaferecyclerview)** Typesafe `ViewHolder` and `ItemViewHolder` for
easy basic usage of `RecyclerView`.
- **[Views:](3/views)** Extensions function and properties on `View`s.
- **[Views AppCompat:](3/views-appcompat)** AppCompat extension of [Views](3/views). Includes helpers
for `ImageView` tinting, `ActionBar` and tooltip.
- **[Views CardView:](3/views-cardview)** CardView extension of [Views](3/views). Provides a
`contentPadding` property.
- **[Views Coroutines:](3/views-coroutines)** Android Views + Kotlin coroutines.
- **[Views Coroutines Material:](3/views-coroutines-material)** Material Components + Kotlin coroutines.
- **[Views DSL:](3/views-dsl)** Create UIs with readable Kotlin code (IDE preview supported).
- **[Views DSL AppCompat:](3/views-dsl-appcompat)** AppCompat extension of [Views DSL](3/views-dsl).
- **[Views DSL ConstraintLayout:](3/views-dsl-constraintlayout)** ConstraintLayout extension of
[Views DSL](3/views-dsl).
- **[Views DSL CoordinatorLayout:](3/views-dsl-coordinatorlayout)** CoordinatorLayout extension of
[Views DSL](3/views-dsl).
- **[Views DSL Material:](3/views-dsl-material)** Material Components extension of [Views DSL](3/views-dsl).
- **[Views DSL RecyclerView:](3/views-dsl-recyclerview)** RecyclerView extension of [Views DSL](3/views-dsl).
- **[Views Material:](3/views-material)** Material Components extension of [Views](3/views).
- **[Views RecyclerView:](3/views-recyclerview)** RecyclerView extension of [Views](3/views).

## Download

### Gradle instructions
Make sure you have `jcenter()` in the repositories defined in your project's
(root) `build.gradle` file (default for new Android Studio projects).

To make is easier to take advantage of the contents of Splitties for your Android projects, there
are grouping artifacts that include _most_ splits.

#### Android base

These 2 packs don't include AppCompat and are suitable for WearOS apps.

Includes the following modules:
- [activities](3/activities)
- [appctx](3/appctx)
- [bitflags](3/bitflags)
- [bundle](3/bundle)
- [collections](3/collections)
- [dimensions](3/dimensions)
- [fragments](3/fragments)
- [fragmentargs](3/fragmentargs)
- [intents](3/intents)
- [lifecycle-coroutines](3/lifecycle-coroutines)
- [mainhandler](3/mainhandler)
- [mainthread](3/mainthread)
- [material-colors](3/material-colors)
- [permissions](3/permissions)
- [preferences](3/preferences)
- [resources](3/resources)
- [systemservices](3/systemservices)
- [toast](3/toast)
- [views](3/views)
- [views-coroutines](3/views-coroutines)
- [views-recyclerview](3/views-recyclerview)
- [views-selectable](3/views-selectable)
- [views-selectable-constraintlayout](3/views-selectable-constraintlayout)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-base:3.0.0-alpha07")
```

There's also a version with Views DSL. It additionally includes the following modules:

- [views-dsl](3/views-dsl)
- [views-dsl-constraintlayout](3/views-dsl-constraintlayout)
- [views-dsl-recyclerview](3/views-dsl-recyclerview)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-base-with-views-dsl:3.0.0-alpha07")
```

#### Android AppCompat

These 2 packs include the [Android base](#android-base) pack, and the following modules:
- [alertdialog-appcompat](3/alertdialog-appcompat)
- [alertdialog-appcompat-coroutines](3/alertdialog-appcompat-coroutines)
- [views-appcompat](3/views-appcompat)
- [views-selectable-appcompat](3/views-selectable-appcompat)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-appcompat:3.0.0-alpha07")
```

There's also a version with Views DSL. It additionally includes the Views DSL version of the
[Android base pack](#android-base) and the following module:
- [views-dsl-appcompat](3/views-dsl-appcompat)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-appcompat-with-views-dsl:3.0.0-alpha07")
```

#### Android Material Components

These 2 packs include the [Android AppCompat](#android-appcompat) pack, and the following modules:
- [material-lists](3/material-lists)
- [snackbar](3/snackbar)
- [views-cardview](3/views-cardview)
- [views-coroutines-material](3/views-coroutines-material)
- [views-material](3/views-material)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-material-components:3.0.0-alpha07")
```

There's also a version with Views DSL. It additionally includes the Views DSL version of the
[Android AppCompat pack](#android-appcompat) and the following modules:
- [views-dsl-coordinatorlayout](3/views-dsl-coordinatorlayout)
- [views-dsl-material](3/views-dsl-material)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-material-components-with-views-dsl:3.0.0-alpha07")
```

#### All the artifacts (47)

Add the version of the library to not repeat yourself if you use multiple
artifacts, and make sure their versions are in sync by adding an ext property
into your root project `build.gradle` file:
```groovy
allProjects {
    ext {
        splitties_version = "3.0.0-alpha07"
    }
}
```

<details>
<summary>
<b>Here are all the artifacts of this library. Just use the ones you need. (Click to expand)</b>
</summary>

```kts
implementation("com.louiscad.splitties:splitties-activities:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-alertdialog:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-alertdialog-appcompat:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-alertdialog-appcompat-coroutines:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-appctx:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-arch-lifecycle:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-arch-room:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-bitflags:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-bundle:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-checkedlazy:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-collections:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-coroutines:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-dimensions:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-exceptions:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-fragments:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-fragmentargs:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-initprovider:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-intents:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-lifecycle-coroutines:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-mainhandler:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-mainthread:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-material-colors:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-material-lists:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-permissions:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-preferences:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-resources:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-snackbar:{{version.splitties3}}")
debugImplementation("com.louiscad.splitties:splitties-stetho-init:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-systemservices:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-toast:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-typesaferecyclerview:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-appcompat:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-cardview:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-coroutines:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-coroutines-material:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl-appcompat:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl-constraintlayout:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl-coordinatorlayout:{{version.splitties3}}")
debugImplementation("com.louiscad.splitties:splitties-views-dsl-ide-preview:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl-material:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-dsl-recyclerview:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-material:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-recyclerview:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-selectable:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-selectable-appcompat:{{version.splitties3}}")
implementation("com.louiscad.splitties:splitties-views-selectable-constraintlayout:{{version.splitties3}}")
```

</details>

#### Dev versions
Let's say you need a new feature or a fix that did not make it to a release yet:

You can grab it in the latest dev version by adding the corresponding repository and
changing the library version to the dev version you need in your root project `build.gradle` file:

```groovy
allProjects {
    repositories {
        google()
        jcenter() // Add dev versions repo below
        maven { url 'https://dl.bintray.com/louiscad/splitties-dev' }
    }
    ext {
        splitties_version = '3.0.0-dev-008'
    }
}
```

### Other build systems
For maven and alternative build-systems, check the [Bintray page](
https://bintray.com/louiscad/maven/splitties).

## New versions notifications
Releases are announced on GitHub, you can subscribe by [clicking on "Watch", then "Releases only"](
https://help.github.com/en/articles/watching-and-unwatching-releases-for-a-repository
).

## Improve this library
If you want this library to have **a new feature or an improvement** in a
new or in an existing module, please, open an issue or vote/comment a
similar one first, so it can be discussed.

**Documentation contributions** are also welcome.
For typos or other small improvements, feel free to submit a PR
(pull request) directly.
For more significant doc contributions, please, open an issue first so it
can be discussed.

**If you find a bug**, please open an issue with all the important details.
If you know a simple fix that is not API breaking and that does not have
side-effects that need to be considered, you may also directly submit a PR.

You can also join the discussion on Kotlin's Slack in the
[#splitties](https://kotlinlang.slack.com/messages/splitties) channel (you can get an invitation
[here](http://slack.kotlinlang.org/)).

## What is a split
A "split" is a module of the Splitties library that you can add as a
dependency. It only includes the required transitive dependencies.
This allows you to only add what you need in your app or library module,
so the final apk is as small as possible and doesn't include stuff not used
by your app.

Let's say you're build a Wear OS app using the Views DSL.
Wear OS apps don't need AppCompat. Including it would be a waste of
bandwidth and storage. The Views DSL core module relies on the Android
SDK but not on AppCompat, so you don't bloat your wrist app with AppCompat
by using Views DSL. However, if you are building a phone, tablet or computer
Android app, there's a Views DSL AppCompat split with a few extensions for
you to use.

## Credits

Special thanks to [Jovche Mitrejchevski](https://twitter.com/jovchem) for
helping in taking decisions for this project.

Thanks to JetBrains and the contributors for [Anko](https://github.com/Kotlin/anko),
which was a great source of inspiration, especially for Views DSL, and of course
thanks for the excellent [Kotlin](https://kotl.in) programming language that makes this project possible.

Thanks to [Doug Stevenson](https://twitter.com/CodingDoug) for his articles
["Kotlin & Android: A Brass Tacks Experiment"](
https://medium.com/@CodingDoug/kotlin-android-a-brass-tacks-experiment-part-3-84e65d567a37
). It is fair to say that Views DSL has its root in this experiment.

## License
This library is published under Apache License version 2.0 which you can see
[here](https://github.com/LouisCAD/Splitties/blob/master/LICENSE).
