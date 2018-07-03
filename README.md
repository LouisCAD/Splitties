# Splitties

Splitties is a collection of small independent Android libraries that aims
to make developing apps and libraries for Android (including Wear, TV,
Things, Auto and ChromeOS targeted) easier and more `fun`.

Some modules are similar to what [Anko](https://github.com/Kotlin/anko)
provides.

This project is named "Splitties" because it is split in small modules,
distributed as independent Android libraries, so you can add only the ones
you need to your project/module, helping reduce the size of the final apk.

Each module has been designed to have a small footprint and be as efficient
as possible.

## All the [splits](#what-is-a-split "What is a split in Splitties?")

- **[Activities:](activities)** Start activities with minimal boilerplate.
- **[Alert Dialog:](alertdialog)** Create simple alert dialogs with simple code.
- **[Alert Dialog AppCompat:](alertdialog-appcompat)** AppCompat version of
[Alert Dialog](alertdialog).
- **[App Context:](appctx)** Always have your application `Context` at hand with `appCtx`.
- **[Arch Lifecycle:](arch-lifecycle)** Extensions to get `ViewModel`s, use `LiveData` and observe
`Lifecycle`s.
- **[Arch Room:](arch-room)** Room helpers to instantiate your DB and perform transactions in
Kotlin.
- **[Bit Flags:](bitflags)** `hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`,
`Short` and `Byte`.
- **[Bundle:](bundle)** `BundleSpec` to use `Bundle` with property syntax for `Intent` extras
and more.
- **[Collections:](collections)** `forEach` for `List`s without `Iterator` allocation.
- **[Checked Lazy:](checkedlazy)** `uiLazy` that checks property access on UI thread, and
`checkedLazy` to make your own variant.
- **[Dimensions:](dimensions)** Android `dp` extensions for `View` and `Context`. Particularly
handy when using [View DSL](viewdsl).
- **[Exceptions:](exceptions)** `illegal(…)` and similar functions that return `Nothing`, handy for
impossible or illegal `when` branches.
- **[Fragments:](fragments)** Start activities from fragments and do transactions with minimal
boilerplate.
- **[Fragment Args:](fragmentargs)** Fragment arguments without ceremony thanks to delegated
properties.
- **[Init Provider:](initprovider)** Base class for `ContentProvider`s used for automatic
initialization purposes.
- **[Intents:](intents)** Transform `companion object`s into powerful typesafe intent specs.
- **[Main Handler:](mainhandler)** Top-level `mainHandler` property to stop allocating multiple
`Handler`s for main `Looper`.
- **[Material Colors:](material-colors)** [2014 Material Design color palettes](
https://material.io/design/color/#tools-for-picking-colors) as color resources.
- **[Material Lists:](material-lists)** List item Views implementing [Material Design guidelines](
https://material.io/guidelines) (perfect for usage in a `RecyclerView`).
- **[Preferences:](preferences)** Property syntax for Android's SharedPreferences.
- **[Resources:](resources)** Extensions to get resources like strings, colors or drawables easily,
with support for themed attributes.
- **[Selectable Views:](selectableviews)** Selectable Views with `foreground` property before
API 23.
- **[Selectable Views AppCompat:](selectableviews-appcompat)** [Selectable Views](selectableviews)
for AppCompatTextView.
- **[Selectable Views ConstraintLayout:](selectableviews-constraintlayout)**
[Selectable Views](selectableviews) for ConstraintLayout.
- **[Snackbar:](snackbar)** Grab a snack without ceremony with `snack(…)` and `longSnack(…)`.
- **[Stetho init:](stetho-init)** Have [Stetho](https://github.com/facebook/stetho) for your debug
builds, without writing any code!
- **[System Services:](systemservices)** No more
`context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.
- **[Toast:](toast)** Show a toast by just calling `toast(yourText)`, and dodge [API 25
`BadTokenException`](https://github.com/drakeet/ToastCompat#why).
- **[Typesafe RecyclerView:](typesaferecyclerview)** Typesafe `ViewHolder` and `ItemViewHolder` for
easy basic usage of `RecyclerView`.
- **[UI Thread:](uithread)** Properties related to Android UI thread, and `checkUiThread()`
precondition checker.
- **[View DSL:](viewdsl)** Create UIs with readable Kotlin code.
- **[View DSL AppCompat:](viewdsl-appcompat)** AppCompat extension of [View DSL](viewdsl).
- **[View DSL AppCompat styles:](viewdsl-appcompat-styles)** AppCompat styles for
[View DSL](viewdsl).
- **[View DSL ConstraintLayout:](viewdsl-constraintlayout)** ConstraintLayout extension of
[View DSL](viewdsl).
- **[View DSL Design:](viewdsl-design)** Design Support Library extension of [View DSL](viewdsl).
- **[View DSL IDE preview:](viewdsl-ide-preview)** Preview [View DSL](viewdsl) UIs in the IDE.
- **[View DSL RecyclerView:](viewdsl-recyclerview)** RecyclerView extension of [View DSL](viewdsl).
- **[Views:](views)** Extensions function and properties on `View`s.
- **[Views AppCompat:](views-appcompat)** AppCompat extension of [Views](views). Includes helpers
for `ImageView` tinting, `ActionBar` and tooltip.
- **[Views CardView:](views-cardview)** CardView extension of [Views](views). Provides a
`contentPadding` property.
- **[Views Design:](views-design)** Design Support library extension of [Views](views).
- **[Views RecyclerView:](views-recyclerview)** RecyclerView extension of [Views](views).

## Download

### Gradle instructions
Make sure you have `jcenter()` in the repositories defined in your project's
(root) `build.gradle` file (default for new Android Studio projects).

Add the version of the library to not repeat yourself if you use multiple
artifacts, and make sure their versions are in sync by adding an ext property
into your root project `build.gradle` file:
```groovy
allProjects {
    ext {
        splitties_version = '2.0.0-alpha4'
    }
}
```

<details>
<summary>
<b>Here are all the artifacts of this library. Just use the ones you need. (Click to expand)</b>
</summary>

```groovy
implementation "com.louiscad.splitties:splitties-activities:$splitties_version"
implementation "com.louiscad.splitties:splitties-alertdialog:$splitties_version"
implementation "com.louiscad.splitties:splitties-alertdialog-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-appctx:$splitties_version"
implementation "com.louiscad.splitties:splitties-arch-lifecycle:$splitties_version"
implementation "com.louiscad.splitties:splitties-arch-room:$splitties_version"
implementation "com.louiscad.splitties:splitties-bitflags:$splitties_version"
implementation "com.louiscad.splitties:splitties-bundle:$splitties_version"
implementation "com.louiscad.splitties:splitties-checkedlazy:$splitties_version"
implementation "com.louiscad.splitties:splitties-collections:$splitties_version"
implementation "com.louiscad.splitties:splitties-dimensions:$splitties_version"
implementation "com.louiscad.splitties:splitties-exceptions:$splitties_version"
implementation "com.louiscad.splitties:splitties-fragments:$splitties_version"
implementation "com.louiscad.splitties:splitties-fragmentargs:$splitties_version"
implementation "com.louiscad.splitties:splitties-initprovider:$splitties_version"
implementation "com.louiscad.splitties:splitties-intents:$splitties_version"
implementation "com.louiscad.splitties:splitties-mainhandler:$splitties_version"
implementation "com.louiscad.splitties:splitties-material-colors:$splitties_version"
implementation "com.louiscad.splitties:splitties-material-lists:$splitties_version"
implementation "com.louiscad.splitties:splitties-preferences:$splitties_version"
implementation "com.louiscad.splitties:splitties-resources:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews-constraintlayout:$splitties_version"
implementation "com.louiscad.splitties:splitties-snackbar:$splitties_version"
debugImplementation "com.louiscad.splitties:splitties-stetho-init:$splitties_version"
implementation "com.louiscad.splitties:splitties-systemservices:$splitties_version"
implementation "com.louiscad.splitties:splitties-toast:$splitties_version"
implementation "com.louiscad.splitties:splitties-typesaferecyclerview:$splitties_version"
implementation "com.louiscad.splitties:splitties-uithread:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-design:$splitties_version"
debugImplementation "com.louiscad.splitties:splitties-viewdsl-ide-preview:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-cardview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-design:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-recyclerview:$splitties_version"
```

</details>

#### Snapshots
Let's say you need a new feature or a fix that did
not make it to a release yet:

You can grab it in the latest snapshot by adding the
snapshots repository and changing the library version to the `-SNAPSHOT`
version in your root project `build.gradle` file:

```groovy
allProjects {
    repositories {
        google()
        jcenter() // Add snapshots repo below
        maven { url 'https://oss.jfrog.org/artifactory/oss-snapshot-local' }
    }
    ext {
        splitties_version = '2.0.0-SNAPSHOT' // Change this line
    }
}
```

If you need to, you can browse the deployed snapshots on artifactory with [the native browser](
https://oss.jfrog.org/list/oss-snapshot-local/com/louiscad/splitties/) or [the web app](
https://oss.jfrog.org/webapp/#/artifacts/browse/tree/General/oss-snapshot-local/com/louiscad/splitties
) so you can pick a specific snapshot.

### Other build systems
For maven and alternative build-systems, check the [Bintray page](
https://bintray.com/louiscad/maven/splitties).

## New versions notifications
To get notified for new versions, be sure to click on "Watch" on the
[splitties Bintray page](https://bintray.com/louiscad/maven/splitties).

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

## What is a split
A "split" is a module of the Splitties library that you can add as a
dependency. It only includes the required transitive dependencies.
This allows you to only add what you need in your app or library module,
so the final apk is as small as possible and doesn't include stuff not used
by your app.

Let's say you're build an Android Wear app using the View DSL.
Android Wear apps don't need AppCompat. Including it would be a waste of
bandwidth and storage. The View DSL core module relies on the Android
SDK but not on AppCompat, so you don't bloat your wrist app with AppCompat
by using View DSL. However, if you are building a phone, tablet or computer
Android app, there's a View DSL AppCompat split with a few extensions for
you to use.

## License
This library is published under Apache License version 2.0 which you can see
[here](https://github.com/LouisCAD/Splitties/blob/master/LICENSE).
