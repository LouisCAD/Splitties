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

## A few examples

Splitties is all about simplifying your code. Here are a few examples:

**Kotlin:**
```kotlin
startActivity(Intent(this, DemoActivity::class.java))
```

**Kotlin with Splitties [Activities](modules/activities):**
```kotlin
start<DemoActivity>()
```

----

**Kotlin:**
```kotlin
Snackbar.make(root, R.string.refresh_successful, Snackbar.LENGTH_SHORT)
    .show()
```

**Kotlin with Splitties [Snackbar](modules/snackbar):**
```kotlin
root.snack(R.string.refresh_successful)
```
----

**Racing coroutines:** (`raceOf(…)` comes from [the Coroutines module](modules/coroutines))

```kotlin
suspend fun awaitUserChoice(ui: SomeUi, choices: List<Stuff>): Stuff? = raceOf({
    ui.awaitSomeUserAction(choices)
}, {
    ui.awaitDismissal()
    null
}, {
    ui.showSomethingInRealtimeUntilCancelled() // Returns Nothing, will run, but never "win".
})
```

----

**Kotlin:**
```kotlin
Snackbar.make(root, getString(R.string.deleted_x_items, deletedCount), Snackbar.LENGTH_LONG)
    .setAction(android.R.string.cancel) {
        deleteOperation.requestRollback()
    }
    .setActionTextColor(ContextCompat.getColor(this, R.color.fancy_color))
    .show()
```

**Kotlin with Splitties [Snackbar](modules/snackbar):**
```kotlin
root.longSnack(str(R.string.deleted_x_items, deletedCount)) {
    action(android.R.string.cancel, textColor = color(R.color.fancy_color)) {
        deleteOperation.requestRollback()
    }
}
```

----

## Overview

- [System interaction (Android only)](#system-interaction-android-only)
- [User input and user interface related splits](#user-input-and-user-interface-related-splits)
  - [Small messages (Android only)](#small-messages-android-only)
  - [Dialogs (Android only)](#dialogs-android-only)
  - [System UI (Android only)](#system-ui-android-only)
  - [Extensions for Views (Android only)](#extensions-for-views-android-only)
  - [Creating View based UIs with the power of Kotlin (Android only)](#creating-view-based-uis-with-the-power-of-kotlin-android-only)
  - [Various UI utilities (Android only)](#various-ui-utilities-android-only)
  - [Material Design helpers (Android only)](#various-ui-utilities-android-only)
- [Inter and cross app communication: Activities, Fragments, Intents, and Bundles](#inter-and-cross-app-communication-activities-fragments-intents-and-bundles)
- [Concurrency (Multiplatform)](#concurrency-multiplatform)
- [Data persistence (Multiplatform)](#data-persistence-multiplatform)
- [Utilities (Multiplatform)](#utilities-multiplatform)
- [Debugging (Android only)](#debugging-android-only)
- [Legacy (Android only)](#legacy-android-only)

## System interaction (Android only):

- **[App Context:](modules/appctx)** Always have your application `Context` at hand with `appCtx`.
- **[System Services:](modules/systemservices)** No more `context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.

## User input and user interface related splits:

### Small messages (Android only)

- **[Snackbar:](modules/snackbar)** Grab a snack without ceremony with `snack(…)` and `longSnack(…)`.
- **[Toast:](modules/toast)** Show a toast by just calling `toast(yourText)`, and dodge [API 25
  `BadTokenException`](https://github.com/drakeet/ToastCompat#why).

### Dialogs (Android only)

- **[Alert Dialog:](modules/alertdialog)** Create simple alert dialogs with simple code.
- **[Alert Dialog AppCompat:](modules/alertdialog-appcompat)** AppCompat version of
  [Alert Dialog](modules/alertdialog).
- **[Alert Dialog AppCompat Coroutines:](modules/alertdialog-appcompat-coroutines)**
  `showAndAwait` extension functions for AppCompat AlertDialog.
- **[Alert Dialog Material:](modules/alertdialog-appcompat)** Material Components extension of
  [Alert Dialog AppCompat](modules/alertdialog-appcompat).

### System UI (Android only)

- **[Dangerous permissions:](modules/permissions)** Request runtime permissions without polluting your codebase.

### Extensions for Views (Android only)

- **[Views:](modules/views)** Extensions function and properties on `View`s.
- **[Views AppCompat:](modules/views-appcompat)** AppCompat extension of [Views](modules/views). Includes helpers for `ImageView` tinting, `ActionBar` and tooltip.
- **[Views CardView:](modules/views-cardview)** CardView extension of [Views](modules/views). Provides a `contentPadding` property.
- **[Views Material:](modules/views-material)** Material Components extension of [Views](modules/views).
- **[Views Coroutines Material:](modules/views-coroutines-material)** Material Components + Kotlin coroutines.
- **[Views RecyclerView:](modules/views-recyclerview)** RecyclerView extension of [Views](modules/views).
- **[Views Coroutines:](modules/views-coroutines)** Android Views + Kotlin coroutines.

### Creating View based UIs with the power of Kotlin (Android only)

- **[Views DSL:](modules/views-dsl)** Create UIs with readable Kotlin code (IDE preview supported).
- **[Views DSL AppCompat:](modules/views-dsl-appcompat)** AppCompat extension of [Views DSL](modules/views-dsl).
- **[Views DSL ConstraintLayout:](modules/views-dsl-constraintlayout)** ConstraintLayout extension of
  [Views DSL](modules/views-dsl).
- **[Views DSL CoordinatorLayout:](modules/views-dsl-coordinatorlayout)** CoordinatorLayout extension of
  [Views DSL](modules/views-dsl).
- **[Views DSL Material:](modules/views-dsl-material)** Material Components extension of [Views DSL](modules/views-dsl).
- **[Views DSL RecyclerView:](modules/views-dsl-recyclerview)** RecyclerView extension of [Views DSL](modules/views-dsl).

### Various UI utilities (Android only)

- **[Resources:](modules/resources)** Extensions to get resources like strings, colors or drawables easily,
  with support for themed attributes.
- **[Dimensions:](modules/dimensions)** Android `dp` extensions for `View` and `Context`.
  Particularly handy when using [Views DSL](modules/views-dsl).
- Selectable views
  - **[Selectable Views:](modules/views-selectable)** Selectable Views with `foreground` property before API 23.
  - **[Selectable Views AppCompat:](modules/views-selectable-appcompat)** [Selectable Views](modules/views-selectable) for AppCompatTextView.
  - **[Selectable Views ConstraintLayout:](modules/views-selectable-constraintlayout)** [Selectable Views](modules/views-selectable) for ConstraintLayout.
- **[Typesafe RecyclerView:](modules/typesaferecyclerview)** Typesafe `ViewHolder` and `ItemViewHolder` for easy basic usage of `RecyclerView`.

### Material Design helpers (Android only)

- **[Material Colors:](modules/material-colors)** [2014 Material Design color palettes](
  https://material.io/design/color/#tools-for-picking-colors) as color resources.
- **[Material Lists:](modules/material-lists)** List item Views implementing [Material Design guidelines](
  https://material.io/guidelines) (perfect for usage in a `RecyclerView`).

## Inter and cross app communication: Activities, Fragments, Intents, and Bundles

- **[Activities:](modules/activities)** Start activities with minimal boilerplate.
- **[Intents:](modules/intents)** Transform `companion object`s into powerful typesafe intent specs,
  and create `PendingIntent`s the clean and easy way.
- **[Fragments:](modules/fragments)** Start activities from fragments and do transactions with
  minimal boilerplate.
- **[Fragment Args:](modules/fragmentargs)** Fragment arguments without ceremony thanks to
  delegated properties.
- **[Bundle:](modules/bundle)** `BundleSpec` to use `Bundle` with property syntax for `Intent`
  extras and more.

## Concurrency (Multiplatform)

- **[Coroutines:](modules/coroutines)** General purpose extensions to kotlinx.coroutines.
- **[Lifecycle Coroutines (Android only):](modules/lifecycle-coroutines)** Coroutines integration with AndroidX
  [`Lifecycle`](https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle).
- **[Main Thread:](modules/mainthread)** Properties and precondition checkers related to the main thread.
- **[Main Handler (Android only):](modules/mainhandler)** Top-level `mainHandler` property to stop allocating multiple
  `Handler`s for main `Looper`.
- **[Checked Lazy (Android only):](modules/checkedlazy)** `mainThreadLazy` that checks property access on

## Data persistence (Multiplatform)

- **[Preferences:](modules/preferences)** Property syntax for Android's `SharedPreferences`/`DataStore` and macOS/iOS/watchOS `NSUserDefaults`.
- **[Arch Room:](modules/arch-room)** Room helpers to instantiate your DB and perform
  transactions in Kotlin.

## Utilities (Multiplatform)

- **[Bit Flags:](modules/bitflags)** `hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`, `Short`, `Byte`, and their unsigned counterparts.
- **[Collections:](modules/collections)** `forEach` for `List`s without `Iterator` allocation.

## Debugging (Android only)

- **[Stetho init:](modules/stetho-init)** Have [Stetho](https://github.com/facebook/stetho) for your debug
  builds, without writing any code!

## Legacy (Android only)

- **[Exceptions:](modules/exceptions)** `unexpectedValue(…)`, `unsupportedAction(…)` and similar
  functions that return `Nothing`.
- **[Arch Lifecycle:](modules/arch-lifecycle)** Extensions to get `ViewModel`s, use `LiveData` and
  observe `Lifecycle`s.

## Download

### Gradle instructions

Make sure you have `mavenCentral()` in the repositories defined in your project's
(root) `build.gradle` file (default for new Android Studio projects).

To make it easier to take advantage of the contents of Splitties for your Android projects, there
are grouping artifacts that include _most_ splits.

#### Android base

Adding with [refreshVersions](https://github.com/jmfayard/refreshVersions): `Splitties.pack.androidBase` or `Splitties.pack.androidBaseWithViewsDsl`.

These 2 packs don't include AppCompat and are suitable for WearOS apps.

Includes the following modules:
- [activities](modules/activities)
- [appctx](modules/appctx)
- [bitflags](modules/bitflags)
- [bundle](modules/bundle)
- [collections](modules/collections)
- [coroutines](modules/coroutines)
- [dimensions](modules/dimensions)
- [fragments](modules/fragments)
- [fragmentargs](modules/fragmentargs)
- [intents](modules/intents)
- [lifecycle-coroutines](modules/lifecycle-coroutines)
- [mainhandler](modules/mainhandler)
- [mainthread](modules/mainthread)
- [material-colors](modules/material-colors)
- [permissions](modules/permissions)
- [preferences](modules/preferences)
- [resources](modules/resources)
- [systemservices](modules/systemservices)
- [toast](modules/toast)
- [views](modules/views)
- [views-coroutines](modules/views-coroutines)
- [views-recyclerview](modules/views-recyclerview)
- [views-selectable](modules/views-selectable)
- [views-selectable-constraintlayout](modules/views-selectable-constraintlayout)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-base:3.0.0")
```

There's also a version with Views DSL. It additionally includes the following modules:

- [views-dsl](modules/views-dsl)
- [views-dsl-constraintlayout](modules/views-dsl-constraintlayout)
- [views-dsl-recyclerview](modules/views-dsl-recyclerview)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-base-with-views-dsl:3.0.0")
```

#### Android AppCompat

Adding with [refreshVersions](https://github.com/jmfayard/refreshVersions): `Splitties.pack.appCompat` or `Splitties.pack.appCompatWithViewsDsl`.

These 2 packs include the [Android base](#android-base) pack, and the following modules:
- [alertdialog-appcompat](modules/alertdialog-appcompat)
- [alertdialog-appcompat-coroutines](modules/alertdialog-appcompat-coroutines)
- [views-appcompat](modules/views-appcompat)
- [views-selectable-appcompat](modules/views-selectable-appcompat)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-appcompat:3.0.0")
```

There's also a version with Views DSL. It additionally includes the Views DSL version of the
[Android base pack](#android-base) and the following module:
- [views-dsl-appcompat](modules/views-dsl-appcompat)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-appcompat-with-views-dsl:3.0.0")
```

#### Android Material Components

Adding with [refreshVersions](https://github.com/jmfayard/refreshVersions): `Splitties.pack.androidMdc` or `Splitties.pack.androidMdcWithViewsDsl`.

These 2 packs include the [Android AppCompat](#android-appcompat) pack, and the following modules:
- [alertdialog-material](modules/alertdialog-material)
- [material-lists](modules/material-lists)
- [snackbar](modules/snackbar)
- [views-cardview](modules/views-cardview)
- [views-coroutines-material](modules/views-coroutines-material)
- [views-material](modules/views-material)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-material-components:3.0.0")
```

There's also a version with Views DSL. It additionally includes the Views DSL version of the
[Android AppCompat pack](#android-appcompat) and the following modules:
- [views-dsl-coordinatorlayout](modules/views-dsl-coordinatorlayout)
- [views-dsl-material](modules/views-dsl-material)

Gradle dependency:

```kotlin
implementation("com.louiscad.splitties:splitties-fun-pack-android-material-components-with-views-dsl:3.0.0")
```

#### All the artifacts (47)

Since you might use multiple artifacts, to not repeat yourself, we recommend you to put the version in a central place,
so it's little effort to upgrade to newer versions.

The best way to do this is to use [refreshVersions](https://github.com/jmfayard/refreshVersions),
it has built-in dependency notations for `Splitties`, and also many other popular and qualitative libraries,
like kotlinx, AndroidX, libraries from Square/CashApp and libraries from Google.

Most importantly, with it, running the `refreshVersions` task will show you the available updates in a matter of seconds,
for all of your dependencies, right into the `versions.properties`, in a way that makes upgrading effortless,
even with just the keyboard.

FYI, the current latest release of Splitties is the version `3.0.0`

<details>
<summary>
<b>Here are the maven coordinates of all the artifacts of this library, for reference. (Click to expand)</b>
</summary>

```
com.louiscad.splitties:splitties-activities
com.louiscad.splitties:splitties-alertdialog
com.louiscad.splitties:splitties-alertdialog-appcompat
com.louiscad.splitties:splitties-alertdialog-appcompat-coroutines
com.louiscad.splitties:splitties-appctx
com.louiscad.splitties:splitties-arch-lifecycle
com.louiscad.splitties:splitties-arch-room
com.louiscad.splitties:splitties-bitflags
com.louiscad.splitties:splitties-bundle
com.louiscad.splitties:splitties-checkedlazy
com.louiscad.splitties:splitties-collections
com.louiscad.splitties:splitties-coroutines
com.louiscad.splitties:splitties-dimensions
com.louiscad.splitties:splitties-exceptions
com.louiscad.splitties:splitties-fragments
com.louiscad.splitties:splitties-fragmentargs
com.louiscad.splitties:splitties-intents
com.louiscad.splitties:splitties-lifecycle-coroutines
com.louiscad.splitties:splitties-mainhandler
com.louiscad.splitties:splitties-mainthread
com.louiscad.splitties:splitties-material-colors
com.louiscad.splitties:splitties-material-lists
com.louiscad.splitties:splitties-permissions
com.louiscad.splitties:splitties-preferences
com.louiscad.splitties:splitties-resources
com.louiscad.splitties:splitties-snackbar
com.louiscad.splitties:splitties-stetho-init
com.louiscad.splitties:splitties-systemservices
com.louiscad.splitties:splitties-toast
com.louiscad.splitties:splitties-typesaferecyclerview
com.louiscad.splitties:splitties-views
com.louiscad.splitties:splitties-views-appcompat
com.louiscad.splitties:splitties-views-cardview
com.louiscad.splitties:splitties-views-coroutines
com.louiscad.splitties:splitties-views-coroutines-material
com.louiscad.splitties:splitties-views-dsl
com.louiscad.splitties:splitties-views-dsl-appcompat
com.louiscad.splitties:splitties-views-dsl-constraintlayout
com.louiscad.splitties:splitties-views-dsl-coordinatorlayout
com.louiscad.splitties:splitties-views-dsl-ide-preview
com.louiscad.splitties:splitties-views-dsl-material
com.louiscad.splitties:splitties-views-dsl-recyclerview
com.louiscad.splitties:splitties-views-material
com.louiscad.splitties:splitties-views-recyclerview
com.louiscad.splitties:splitties-views-selectable
com.louiscad.splitties:splitties-views-selectable-appcompat
com.louiscad.splitties:splitties-views-selectable-constraintlayout
```

</details>

#### Snapshots

Let's say you need to try a new feature or a fix that did not make it to a release yet:

You can grab it in the snapshot version by adding the corresponding repository as shown below, and
changing the library version to the latest snapshot, `3.0.0-SNAPSHOT`:

```groovy
allProjects {
    repositories {
        mavenCentral()
        google() // Add sonatype snapshots repo below
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
}
```

## New versions notifications

Releases are announced on GitHub, you can subscribe by [clicking on "Watch", then "Releases only"](
https://docs.github.com/en/github/managing-subscriptions-and-notifications-on-github/setting-up-notifications/configuring-notifications#configuring-your-watch-settings-for-an-individual-repository
).

However, if you use [refreshVersions](https://github.com/jmfayard/refreshVersions),
you'll also learn about updates when you run the `refreshVersions` task right in the `versions.properties` file.

## Improve this library

If you want this library to have **a new feature or an improvement** in a
new or in an existing module, please, open an issue or vote/comment a
similar one first, so it can be discussed.

**Documentation contributions** are also welcome.
For typos or other small improvements, feel free to submit a PR
(pull request) directly.
For more significant doc contributions, please open an issue first, so it
can be discussed.

**If you find a bug**, please open an issue with all the important details.
If you know a simple fix that is not API breaking and that does not have
side effects that need to be considered, you may also directly submit a PR.

You can also join the discussion on Kotlin's Slack in the
[#splitties](https://kotlinlang.slack.com/messages/splitties) channel (you can get an invitation
[here](http://slack.kotlinlang.org/)).

## What is a split

A "split" is a module of the Splitties library that you can add as a
dependency. It only includes the required transitive dependencies.
This allows you to only add what you need in your app or library module,
so the final apk/ipa/app is as small as possible and doesn't include stuff not used
by your app.

Let's say you're building a Wear OS app using the Views DSL.
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
