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

## All the splits

<details>
<summary>
<b>Click here to expand the outline of the
<a href="#what-is-a-split" title="What is a split?">splits</a></b>
</summary>

* [Alert Dialog](#alert-dialog) and its [AppCompat](#alert-dialog-appcompat)
variant
* [App Context](#app-context)
* [Arch Lifecycle](#arch-lifecycle)
* [Arch Room](#arch-room)
* [Bit Flags](#bit-flags)
* [Bundle](#bundle)
* [Checked Lazy](#checked-lazy)
* [Dimensions](#dimensions)
* [Exceptions](#exceptions)
* [Fragment Args](#fragment-args)
* [Init Provider](#init-provider)
* [Main Handler](#main-handler)
* [Material Lists](#material-lists)
* [Preferences](#preferences)
* [Resources](#resources)
* [Selectable Views](#selectable-views) plus its
[AppCompat](#selectable-views-appcompat) and
[ConstraintLayout](#selectable-views-constraintlayout) extensions
* [Snackbar](#snackbar)
* [Stetho init](#stetho-init)
* [System Services](#system-services)
* [Toast](#toast)
* [Typesafe RecyclerView](#typesafe-recyclerview)
* [UI Thread](#ui-thread)
* [View DSL](#view-dsl) plus its [AppCompat](#view-dsl-appcompat),
[AppCompat styles](#view-dsl-appcompat-styles),
[ConstraintLayout](#view-dsl-constraintlayout) and
[Design](#view-dsl-design) extensions
* [Views](#views) plus its [AppCompat](#views-appcompat) extensions

</details>

### Alert Dialog
*Create simple alert dialogs with simple code*

[Read more here](alertdialog/README.md)

### Alert Dialog AppCompat
*AppCompat version of [Alert Dialog](#alert-dialog)*

[Read more here](alertdialog-appcompat/README.md)

### App Context
*Always have your application `Context` at hand with `appCtx`.*

[Read more here](appctx/README.md)

### Arch Lifecycle
*Extensions to get `ViewModel`s, use `LiveData` and observe `Lifecycle`s.*

[Read more here](arch-lifecycle/README.md)

### Arch Room
*Room helpers to instantiate your DB and perform transactions in Kotlin.*

[Read more here](arch-room/README.md)

### Bit Flags
*`hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`, `Short`
and `Byte`*

[Read more here](bitflags/README.md)

### Bundle
*`BundleHelper` to use `Bundle` with property syntax for `Intent` extras
and more.*

[Read more here](bundle/README.md)

### Checked Lazy
*`uiLazy` that checks property access on UI thread, and `checkedLazy`
to make your own variant.*

[Read more here](checkedlazy/README.md)

### Dimensions
*Android `dp` extensions for `View` and `Context`. Particularly handy
when using [View DSL](#view-dsl).*

[Read more here](dimensions/README.md)

### Exceptions
*`illegal(…)` and similar functions that return `Nothing`, handy for
impossible or illegal `when` branches.*

[Read more here](exceptions/README.md)

### Fragment Args
*Fragment arguments without ceremony thanks to delegated properties.*

[Read more here](fragmentargs/README.md)

### Init Provider
*Base class for `ContentProvider`s used for automatic initialization
purposes.*

[Read more here](initprovider/README.md)

### Main Handler
*Top-level `mainHandler` property to stop allocating multiple `Handler`s for
main `Looper`.*

[Read more here](mainhandler/README.md)

### Material Lists
*List item Views implementing [Material Design guidelines](
https://material.io/guidelines) (perfect for usage in a `RecyclerView`).*

[Read more here](material-lists/README.md)

### Preferences
*Property syntax for Android's SharedPreferences.*

[Read more here](preferences/README.md)

### Resources
*Extensions to get resources like strings, colors or drawables easily,
with support for themed attributes.*

[Read more here](resources/README.md)

### Selectable Views
*Selectable Views with `foreground` property before API 23.*

[Read more here](selectableviews/README.md)

### Selectable Views AppCompat
*[Selectable Views](#selectable-views) for AppCompatTextView.*

[Read more here](selectableviews-appcompat/README.md)

### Selectable Views ConstraintLayout
*[Selectable Views](#selectable-views) for ConstraintLayout.*

[Read more here](selectableviews-constraintlayout/README.md)

### Snackbar
*Grab a snack without ceremony with `snack(…)` and `longSnack(…)`*

[Read more here](snackbar/README.md)

### Stetho init
*Have [Stetho](https://github.com/facebook/stetho) for your debug builds,
without writing any code!*

[Read more here](stetho-init/README.md)

### System Services
*No more `context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.*

[Read more here](systemservices/README.md)

### Toast
*Show a toast by just calling `toast(yourText)`, and dodge [API 25
`BadTokenException`](https://github.com/drakeet/ToastCompat#why).*

[Read more here](toast/README.md)

### Typesafe RecyclerView
*Typesafe `ViewHolder` and `ItemViewHolder` for easy basic usage of
`RecyclerView`.*

[Read more here](typesaferecyclerview/README.md)

### UI Thread
*Properties related to Android UI thread, and `checkUiThread()` precondition
checker.*

[Read more here](uithread/README.md)

### View DSL
*Create UIs with readable Kotlin code.*

[Read more here](viewdsl/README.md)

### View DSL AppCompat
*AppCompat extension of [View DSL](#view-dsl)*

[Read more here](viewdsl-appcompat/README.md)

### View DSL AppCompat styles
*AppCompat styles for [View DSL](#view-dsl)*

[Read more here](viewdsl-appcompat-styles/README.md)

### View DSL ConstraintLayout
*ConstraintLayout extension of [View DSL](#view-dsl)*

[Read more here](viewdsl-constraintlayout/README.md)

### View DSL Design
*Design Support Library extension of [View DSL](#view-dsl)*

[Read more here](viewdsl-design/README.md)

### Views
*Extensions function and properties on `View`s.*

[Read more here](views/README.md)

### Views AppCompat
*AppCompat extension of [Views](#views). Includes helpers for `ImageView`
tinting, `ActionBar` and tooltip.*

[Read more here](views-appcompat/README.md)

## Download
**IMPORTANT NOTE**: The 2.0.0-alpha1 version is is pending jcenter sync.
That means that for the time being, you need to add the bintray repo manually.

To do so, add the following config snippet to your root project `build.gradle`
file:
```groovy
allprojects {
    repositories {
        maven { url "https://dl.bintray.com/louiscad/maven" }
    }
}
```
Subscribe to [this issue](https://github.com/LouisCAD/Splitties/issues/22) to
know when it is on jcenter.

#### Gradle instructions
Make sure you have `jcenter()` in the repositories defined in your project's
(root) `build.gradle` file (default for new Android Studio projects).

Add the version of the library to not repeat yourself if you use multiple
artifacts, and make sure their versions are in sync by adding an ext property
into your root project `build.gradle` file:
```groovy
allProjects {
    ext {
        splitties_version = '2.0.0-alpha1'
    }
}
```
Here are all the artifacts of this library. Just use the ones you need:
```groovy
implementation "com.louiscad.splitties:splitties-alertdialog:$splitties_version"
implementation "com.louiscad.splitties:splitties-alertdialog-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-appctx:$splitties_version"
implementation "com.louiscad.splitties:splitties-arch-lifecycle:$splitties_version"
implementation "com.louiscad.splitties:splitties-arch-room:$splitties_version"
implementation "com.louiscad.splitties:splitties-bitflags:$splitties_version"
implementation "com.louiscad.splitties:splitties-bundle:$splitties_version"
implementation "com.louiscad.splitties:splitties-checkedlazy:$splitties_version"
implementation "com.louiscad.splitties:splitties-dimensions:$splitties_version"
implementation "com.louiscad.splitties:splitties-exceptions:$splitties_version"
implementation "com.louiscad.splitties:splitties-initprovider:$splitties_version"
implementation "com.louiscad.splitties:splitties-mainhandler:$splitties_version"
implementation "com.louiscad.splitties:splitties-material-lists:$splitties_version"
implementation "com.louiscad.splitties:splitties-preferences:$splitties_version"
implementation "com.louiscad.splitties:splitties-resources:$splitties_version"
implementation "com.louiscad.splitties:splitties-fragmentargs:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-selectableviews-constraintlayout:$splitties_version"
debugImplementation "com.louiscad.splitties:splitties-stetho-init:$splitties_version"
implementation "com.louiscad.splitties:splitties-systemservices:$splitties_version"
implementation "com.louiscad.splitties:splitties-toast:$splitties_version"
implementation "com.louiscad.splitties:splitties-typesaferecyclerview:$splitties_version"
implementation "com.louiscad.splitties:splitties-uithread:$splitties_version"
implementation "com.louiscad.splitties:splitties-snackbar:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-design:$splitties_version"
implementation "com.louiscad.splitties:splitties-views:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-appcompat:$splitties_version"
```

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
