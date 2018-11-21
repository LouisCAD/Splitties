# Change log for Splitties

## Version 2.1.0 (2018-11-13)
This release targets Android SDK 28, and splits depending on support libraries use version 28.0.0.

### Changes
- System Services from API 28 are now included into the same named split.
- Update `ConfigChangesHandlingCollapsingToolbarLayout` from View DSL Design to be compatible with
design support library version 28.0.0.
- Update for nullability warnings brought by SDK 28.

## Version 2.0.0 (2018-11-13)
This release targets Android SDK 27, and splits depending on support libraries use version 27.1.1.

### Changes
- Room updated to version 1.1.1 in Arch Room.
- Lambdas of `onCreate` and `onOpen` functions in Arch Room are now `crossinline`.
- The `LifecycleObserver` class is now marked as experimental.

## Version 2.0.0-beta1 (2018-11-13)
This release **breaks binary and source compatibility**.

### Kotlin 1.3.0

This is not just a compiler update for Splitties. This release already takes advantage of Kotlin 1.3
features, beyond stable coroutines:

- The `XmlStyle` class from Views DSL is now inline, for minimal footprint at runtime.
- `SuspendPrefsAccessor` from Preferences is no longer experimental as coroutines graduated.
- Functions that take a lambda in Views DSL (except `lParams` functions) have a contract. This
directly translates to more freedom in your UI code as you can initialize a property later.
- `withExtras`, `putExtras` and `with` from Bundle have a contract. This allows to initialize
local variables from contents of a `Bundle` passing through a `BundleSpec` naturally!
- Symbols that could change are marked as experimental, for less surprises in the future when they
are replaced, renamed or removed (still with a deprecation cycle whenever possible). Only
`verticalListLayoutParams` and `horizontalListLayoutParams` extension functions for
`RecyclerView.LayoutManager` are experimental for now, but this could change, especially in alpha,
or beta stage.

## Version 2.0.0-alpha9 (2018-11-13)
This release **breaks binary and source compatibility**.

It renames several package names and modules, for more consistency across the project.

View DSL has a new name: Views DSL. That also applies to its additional modules.

_Just like an extra `s` can make a new generation of smartphones, it can also make a new version of
Splitties._

The artifact names of all **Selectable Views** and **View DSL** changed, and so did the package
names.

Consequently, after updating the artifact names and the version,
you'll need to update the imports. Fortunately, this is easily done with the "Replace in Path"
IDE option present in IntelliJ IDEA and Android Studio.

All you need to do is find an old import (`import splitties.viewdsl.`), select it, select the
"Replace in Path" option paste (`import splitties.views.dsl.`) in the second input field, and
validate.

Then, you just have to the same for Selectable Views with `import splitties.selectableviews.` and
`import splitties.views.selectable.`… and voilà! You just migrated to latest Splitties version!

### New artifacts
<details>
<summary>
<b>
Here are all the artifacts added in this version. Just use the ones you need. (Click to expand)
</b>
</summary>

```groovy
implementation "com.louiscad.splitties:splitties-views-dsl:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-dsl-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-dsl-constraintlayout:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-dsl-design:$splitties_version"
debugImplementation "com.louiscad.splitties:splitties-views-dsl-ide-preview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-dsl-recyclerview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-selectable:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-selectable-appcompat:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-selectable-constraintlayout:$splitties_version"
```

All the lines above assume you defined the `splitties_version` ext property in your
root project's `build.gradle` file to `2.0.0-alpha9` as shown in this snippet:
```groovy
allProjects {
    ext {
        splitties_version = '2.0.0-alpha9'
    }
}
```

</details>

### Removed artifacts
This release removes all these artifacts:

~`implementation "com.louiscad.splitties:splitties-selectableviews:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-selectableviews-appcompat:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-selectableviews-constraintlayout:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl-design:$splitties_version"`~
~`debugImplementation "com.louiscad.splitties:splitties-viewdsl-ide-preview:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version"`~

## Version 2.0.0-alpha8 (2018-11-12)
This release **breaks binary compatibility**.

It removes all deprecated symbols that had their deprecation level raised to error in 2.0.0-alpha7.

Make sure you don't rely on any library that uses an old version of a Splitties artifact that relies
on these previously deprecated and now removed symbols, or your app is likely not build, or to
crash at runtime because of not found classes.

## Version 2.0.0-alpha7 (2018-11-12)
Raise all deprecated symbols deprecation level to error.

Use this version to make sure you don't use them in your projects, next version will remove them!

## Version 2.0.0-alpha6 (2018-11-11)
Version 2.0.0-alpha5 broke the API, this version fixes this.

### Like version 2.0.0-alpha5, but without breaking the API from 2.0.0-alpha4
This release is _mostly_ the same as 2.0.0-alpha5, but also has 2 very important things:
- binary compatibility (minor change in Preferences experimental API excluded)
- source compatibility, except an overload resolution ambiguity in View DSL, **see the
[migration guide](
https://github.com/LouisCAD/Splitties/blob/v2.0.0-alpha6/viewdsl/Migration-to-2.0.0-alpha6.md
)** for a smooth migration.

### Removed splits and versions sync
Version 2.0.0-alpha5 technically removed 2 splits (one was actually a renaming, the other one
a merging).

Consequently, you have to make sure your dependencies no longer reference these removed artifacts.
Easily done.

Problems arise when you depend on libraries that themselves depend on Splitties, which may be older
versions. These libraries may bring transitive dependencies to old modules that will clash with
ones from newer versions, and the versions they rely on may also mismatch with the ones you need,
and these older versions will take precedence if they are in library modules of your project that
don't depend explicitly on a newer version.

Fortunately, Gradle dependency resolution strategy allows to easily fix these issues all over your
project.

All you need to do is to make sure you have the following snippet into your root project's
`build.gradle` file:

```groovy
allprojects {
    ext {
        splitties_version = '2.0.0-alpha6'
    }
    configurations.all {
        resolutionStrategy.eachDependency { DependencyResolveDetails details ->
            def req = details.requested
            if (req.group != "com.louiscad.splitties") return
            if (req.name == "splitties-uithread") {
                details.useTarget(
                        group: req.group,
                        name: "splitties-mainthread",
                        version: splitties_version
                )
                details.because("Splitties uithread has been renamed to mainthread")
            } else if (req.name == "splitties-viewdsl-appcompat-styles") {
                details.useTarget(
                        group: req.group,
                        name: "splitties-viewdsl-appcompat",
                        version: splitties_version
                )
                details.because("[Splitties] Split merged and removed")
            } else {
                details.useTarget(group: req.group, name: req.name, version: splitties_version)
                details.because("Transitive dependencies could take precedence otherwise")
            }
        }
    }
}
```

The snippet above, for all sub-projects (aka. modules):
1. defines Splitties version in an `ext` property so it can be used in all `build.gradle` files.
2. sets a resolution strategy for all configurations (like `implementation` or `api`) which:
  1. redirects any usage of the old `splitties-mainthread` artifact to the new
  `splitties-mainthread` one.
  2. redirects any usage of the old `splitties-viewdsl-appcompat-styles` artifact to the one it has
  been merged into: `splitties-viewdsl-appcompat`.
  3. makes sure all splitties artifacts versions are in sync, across all sub-projects.


If you don't do this but have a library using an old artifact in your dependencies, you'll
encounter gradle sync issues, or runtime issues, and the real cause may not appear clearly
(because of bugs in tooling).

### Changes per module
#### Checked Lazy
`uiLazy` has been deprecated in favor of `mainThreadLazy`.

#### View DSL RecyclerView
The `wrapInRecyclerView` extension function now accepts an optional lambda to configure the wrapping
`RecyclerView` easily.

## Version 2.0.0-alpha5 (2018-11-02)
_This release is **breaking** if you come from version 2.0.0-alpha4, especially if you were using
View DSL. It's highly recommended to directly migrate from version 2.0.0-alpha4 to 2.0.0-alpha6,
which has a smoother migration path, and [a guide](
https://github.com/LouisCAD/Splitties/blob/v2.0.0-alpha6/viewdsl/Migration-to-2.0.0-alpha6.md
) to do so._

### Project wide changes
- Add consumer proguard rules for splits with optional dependencies.
- Update to Kotlin 1.2.71
- Update kotlinx.coroutines to version 0.30.2

### Changes per module
#### Collections
Add allocation-free reverse `List` forEach extensions: `forEachReversedByIndex` and
`forEachReversedWithIndex`.

#### Intents
Add `toPendingActivities()` extension function for `Array<Intent>`.

#### Material Lists
- The following previously deprecated classes have been removed:
  - `SingleLineIconListItem` (use `IconOneLineListItem` instead)
  - `TwoLinesIconListItem` (use `IconTwoLinesListItem` instead)
  - `TwoLinesIconSwitchListItem` (use `IconTwoLinesSwitchListItem` instead)
- All the xml files (except `view_ids.xml`) have been removed.

#### Preferences
##### Enhancements
- The preferences are now loaded in `Dispatchers.IO` when using `SuspendPrefsAccessor`.
- Fix clash with private property name and non imported extension for Preferences. (#96).

##### Breaking changes (in experimental API)
The `SuspendPrefsAccessor` constructor parameter of type `CoroutineDispatcher` has been removed
now that `Dispatchers.IO` is always used.
If you didn't specify a custom dispatcher, you don't need to do anything.

#### Selectable Views (all variants)
All the final methods have been opened up, and now have the `@CallSuper` annotation instead. This
makes the classes more useful to develop custom views, like the extended Floating Action Button with
`SelectableConstraintLayout` where you need to clip the view in `onDraw(…)`.

The `dispatchDrawableHotspotChanged` overridden method in the classes of the Selectable Views splits
family was annotated with `@TargetApi`, but is not correctly annotated with `@RequiresApi`. This
likely caused no issue as you usually don't call this method directly but let Android do, but now,
it's fixed!

#### ~UI Thread~ -> Main Thread
The UI Thread split has been renamed to Main thread (for the same reasons kotlinx.coroutines
replaced `UI` by `Dispatchers.Main`).

While **the artifact has been changed**, the old symbols have only been deprecated in favor of new
ones defined in new package, with new names (`ui`->`main`).

If you use a library that depends on the old artifact, see "Removed splits and versions sync"
in version 2.0.0-alpha6 release notes.

#### View DSL (and additional modules)
The API of View DSL (and its additional modules) has been improved, and there's some new features,
along with some deprecations.

However, this release (2.0.0-alpha5) is **breaking** when updating from previous versions. This
has been fixed in 2.0.0-alpha6, so please **skip this release if you are upgrading**, and follow
thoroughly **the migration guide**.

Regardless, there has been improvements in View DSL additional modules. Please, review them below.

#### ~View DSL AppCompat Styles~ -> View DSL AppCompat
The View DSL AppCompat Styles split has been merged into View DSL AppCompat.

If you use a library that depends on the old artifact, see "Removed splits and versions sync"
in version 2.0.0-alpha6 release notes.

#### View DSL ConstraintLayout
Probably the best change in this split is that now, you no longer need to specify any `View` id.
If there's none and you add a constraint using extension functions from this split, an id that can't
clash with aapt/xml ids will be generated and assigned to the view so constraints work. Keep in mind
you may still want to use stable ids defined in xml or elsewhere in cases where you use views that
need to have their state saved, like a `RecyclerView`, an `EditText` or a `CheckBox`.

Added support for chains with the two `horizontalChain` and `verticalChain` new extension functions
for `ConstraintLayout`. Also added `horizontalMargin` and `verticalMargin` extension properties for
`List<View>` that are designed for use in a `ConstraintLayout` when you made a chain with the two
new methods that take a list of views.

#### View DSL Design
When instantiating an `AppBarLayout` or a `CollapsingToolbarLayout` with the new `appBarLayout` and
`collapsingToolbarLayout` functions, you'll automatically get instances that have known bugs in the
design support library fixed, including config changes handling (supports rotation or layout changes
without recreating the Activity).

#### View DSL RecyclerView
The `setSingleView` extension function for `RecyclerView` has been deprecated. Use the
`wrapInRecyclerView` extension function for `View` instead, it is simpler to use and supports
horizontal scrolling.

#### Views
- Deprecated View visibility extension properties and functions in favor of Android KTX ones.
- Added `lines` write only extension property for `TextView`.
- Change the signature of the lambda of the `onClick` extension function for `View`. It no longer
passes the clicked `view`. This is to avoid `it` shadowing when you nest lambdas, and has been done
because this parameter is almost never used.
- Add 1 extension function, 1 extension property and 1 top level function, related to `View` id
generation: `View.assignAndGetGeneratedId()`, `View.existingOrNewId` and `generateViewId()`.

### Upcoming APIs preview in the sample
The sample of Splitties is a place where you can preview several extensions or other work that can
be integrated into Splitties as a library later.

In addition to what was already present in the sample, there has been an important addition:
An example of how to request a dangerous permission with a single suspend call, plus a try/catch
to handle user deny.

### New artifact
This release has a new artifact:
```groovy
implementation "com.louiscad.splitties:splitties-mainthread:$splitties_version"
```

### Removed artifacts
This release removes these two artifacts:

~`implementation "com.louiscad.splitties:splitties-uithread:$splitties_version"`~
~`implementation "com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version"`~

## Version 2.0.0-alpha4 (2018-07-09)
* Provide `ReplaceWith` migrations for `add` methods deprecated in version 2.0.0-alpha2.
* Add default empty lambda for `startActivity(action: String…)` methods.
* Compiled with Kotlin 1.2.51

## Version 2.0.0-alpha3 (2018-06-09)
### New features
#### Bundle
There are 2 new methods: `bundleOrDefault(…)` and `bundleOrElse { … }` to allow default values in
`BundleSpec` delegated properties.

#### Fragment Args
Like for `BundleSpec`, there are 2 new methods: `argOrDefault(…)` and `argOrElse { … }` to allow
default values in `Fragment` delegated argument properties.

### Breaking changes
#### Bundle
- The delegates previously returned by `bundle()` and `bundleOrNull()` are no longer part of the
public API and have been replaced by the `ReadWriteProperty` interface.

#### Fragment Args
- The `arg()` and `argOrNull()` functions have moved out of the `support` subpackage as there's no
longer any ambiguity since platform Fragments are deprecated and going away. Auto-import should
import the versions from the new package for you.
- The delegates previously returned by `arg()` and `argOrNull()` are no longer part of the public
API and have been replaced by the `ReadWriteProperty` interface.

## Version 2.0.0-alpha2 (2018-05-21)
### 9 new library modules (amounting to a total of 42 splits):
- **Activities**: Start activities with minimal boilerplate
- **Collections**: `forEach` for `List`s without `Iterator` allocation
- **Fragments**: Start activities from fragments and do transactions with minimal boilerplate
- **Intents**: Transform `companion object`s into powerful typesafe intent specs
- **Material Colors**: [2014 Material Design color palettes](
https://material.io/design/color/#tools-for-picking-colors) as color resources
- **View DSL RecyclerView**: RecyclerView extension of View DSL
- **Views CardView**: CardView extension of Views. Provides a `contentPadding` property
- **View Design**: Design Support library extension of Views
- **Views RecyclerView**: RecyclerView extension of Views

### Other changes:
#### AppCtx
The `consume { … }` utility function from the `splitties.init` package has been deprecated. Replace
it with `true.also { _ -> … }` or `false.also { _ -> … }`.
#### Arch Lifecycle
- New `mapNotNull`, `switchMap` and `switchMapNotNull` extension functions for `LiveData`.
- `activityScope` and `fragmentScope` extension functions to get a `ViewModel` now accept an
optional lambda (which creates a `ViewModelProvider` under the hood. This allows to pass arguments
to your `ViewModel` when it's first created.
- `observe` and `observeNotNull` now return the created `Observer` so it can be unregistered
manually later if needed.
#### Arch Room
- The new `inTransaction { … }` extension function for `RoomDatabase`s does the same as
`transaction { … }` but also returns the value of the last expression of the lambda.
- New `onCreate { … }` and `onOpen { … }` extension functions for `RoomDatabase.Builder`.
#### Bundle
- `BundleHelper` has been renamed to `BundleSpec`, but a typealias keeps the source compatibility.
However, there's no binary compatibility, which means you'll need to recompile any library using it.
- `BundleSpec` and the methods relying on it can now be used safely on any thread!
#### Material Lists
Allow disabling default icon tint on list items with optional constructor parameter.
#### Preferences
A new experimental `SuspendPrefsAccessor` for coroutines users allows you to ensure you can't load
the preferences (which does I/O) on the UI thread.
#### Resources
The `str` extension functions `formatArgs` now accept `null` arguments.
#### View DSL
The higher order function `add` has been deprecated because it went in the way of promoting a view
to a property easily.
#### View DSL AppCompat styles
Added `flatButton`, `imgActionButton` and `largeProgressBar`.
#### View DSL ConstraintLayout
Add `baselineToBaselineOf(…)` extension function for `ConstraintLayout.LayoutParams`.
#### View DSL IDE Preview
`UiPreView` injects a valid value into `appCtx` so your `Ui`s can depend on it (probably indirectly)
without breaking preview!
#### Views
- New Gravity flags aliases (e.g. `gravityStartCenter` instead of
`Gravity.START or Gravity.CENTER_VERTICAL`).
- Change case of `imageBitMap` to `imageBitmap` to make it more consistent with the class name
`Bitmap`
#### Views AppCompat
`tooltipTxt` now accepts null to remove any tooltip previously set on the view.

### New artifacts
<details>
<summary>
<b>
Here are all the artifacts added in this version. Just use the ones you need. (Click to expand)
</b>
</summary>

```groovy
implementation "com.louiscad.splitties:splitties-activities:$splitties_version"
implementation "com.louiscad.splitties:splitties-collections:$splitties_version"
implementation "com.louiscad.splitties:splitties-fragments:$splitties_version"
implementation "com.louiscad.splitties:splitties-intents:$splitties_version"
implementation "com.louiscad.splitties:splitties-material-colors:$splitties_version"
implementation "com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-cardview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-design:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-recyclerview:$splitties_version"
```

All the lines above assume you defined the `splitties_version` ext property in your
root project's `build.gradle` file to `2.0.0-alpha2` as shown in this snippet:
```groovy
allProjects {
    ext {
        splitties_version = '2.0.0-alpha2'
    }
}
```

</details>

## Version 2.0.0-alpha1 (2018-03-11)
### 26 new library modules (amounting to a total of 33 splits):
- **Alert Dialog**: Create simple alert dialogs with simple code
- **Alert Dialog AppCompat**: AppCompat version of Alert Dialog
- **Arch Lifecycle**: Extensions to get `ViewModel`s, use `LiveData` and observe `Lifecycle`s
- **Arch Room**: Room helpers to instantiate your DB and perform transactions in Kotlin
- **Bit Flags**: `hasFlag`, `withFlag` and `minusFlag` extensions on `Long`, `Int`, `Short` and
`Byte`
- **Bundle**: `BundleHelper` to use `Bundle` with property syntax for `Intent` extras
and more
- **Dimensions**: Android `dp` extensions for `View` and `Context`. Particularly handy
when using View DSL
- **Exceptions**: `illegal(…)` and similar functions that return `Nothing`, handy for
impossible or illegal `when` branches
- **Fragment Args**: Fragment arguments without ceremony thanks to delegated properties
- **Init Provider**: Base class for `ContentProvider`s used for automatic initialization
purposes
- **Main Handler**: Top-level `mainHandler` property to stop allocating multiple `Handler`s for
main `Looper`
- **Resources**: Extensions to get resources like strings, colors or drawables easily,
with support for themed attributes
- **Selectable Views AppCompat**: Selectable Views for AppCompatTextView
- **Selectable Views ConstraintLayout**: Selectable Views for ConstraintLayout
- **Snackbar**: Grab a snack without ceremony with `snack(…)` and `longSnack(…)`
- **System Services**: No more `context.getSystemService(NAME_OF_SERVICE) as NameOfManager`
- **Toast**: Show a toast by just calling `toast(yourText)`, and dodge [API 25
`BadTokenException`](https://github.com/drakeet/ToastCompat#why)
- **UI Thread**: Properties related to Android UI thread, and `checkUiThread()` precondition
checker
- **View DSL**: Create UIs with readable Kotlin code
- **View DSL AppCompat**: AppCompat extension of View DSL
- **View DSL AppCompat styles**: AppCompat styles for View DSL
- **View DSL ConstraintLayout**: ConstraintLayout extension of View DSL
- **View DSL Design**: Design Support Library extension of View DSL
- **View DSL IDE preview**: Preview View DSL UIs in the IDE
- **Views**: Extensions function and properties on `View`s
- **Views AppCompat**: AppCompat extension of Views. Includes helpers for `ImageView`
tinting, `ActionBar` and tooltip

### 1 renamed module:
- "Checked Lazy" replaces the "Concurrency" module.

### Other changes:
- The groupId of the library changed from `xyz.louiscad.splitties` to `com.louiscad.splitties`.
Check dependencies list below.
- All previous modules migrated to Kotlin, excepted the `ViewHolder` class from the Typesafe
RecyclerView module that can't be written in Kotlin at the moment due to hiding super fields not
being supported.
- App Context module has a new, memory leak safe `injectAsAppCtx()` method, that uses the new
`canLeakMemory()` extension function on `Context` that is also public.
- Preferences `StringPref` and `stringPref` don't allow null values anymore. Use `StringOrNullPref`
and `stringOrNullPref` if you need nullable strings. Same for `StringSetPref` and `stringSetPref`.
- The `isUiThread` property moved from the old "Concurrency" module to the "UI Thread" module.
- Material Lists are now written in Kotlin with View DSL, fixing icon tinting support on day/night
themes and behavior on long texts. Also, the naming has been improved. Old named items are now
deprecated.
- Checked Lazy does no longer depend on Timber but is now more configurable, allowing to write
reporting behavior if needed.
- Selectable Views has been split in base module (that includes only dependencies on Android
platform), AppCompat module and ConstraintLayout module.
- Selectable Views don't support the `foreground` xml attribute from app namespace anymore, but
there's a new `foregroundSelector` property.

### New artifacts
<details>
<summary>
<b>
Here are all the artifacts added in this version. Just use the ones you need. (Click to expand)
</b>
</summary>

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
debugImplementation "com.louiscad.splitties:splitties-viewdsl-ide-preview:$splitties_version"
implementation "com.louiscad.splitties:splitties-views:$splitties_version"
implementation "com.louiscad.splitties:splitties-views-appcompat:$splitties_version"
```

All the lines above assume you defined the `splitties_version` ext property in your
root project's `build.gradle` file to `2.0.0-alpha1` as show in this snippet:
```groovy
allProjects {
    ext {
        splitties_version = '2.0.0-alpha1'
    }
}
```

</details>

## Version 1.3.0 (2017-04-17)
### 5 new library modules:
- **App Context**: Have a `Context` everywhere
- **Concurrency**: Single thread `lazy` implementations, with reporting via [Timber](
https://github.com/JakeWharton/timber) support
- **Material Lists**: List Items for RecyclerView implementing [Material Design Guidelines](
https://material.io/guidelines/)
- **Preferences**: Property syntax for Android's SharedPreferences
- **Stetho init**: Have [Stetho](https://github.com/facebook/stetho) without writing any code!

### 1 renamed module:
- Selectable Views replaces Selectable ViewGroups

### Other changes:
- Selectable Views now has a `SelectableTextView`. Made to use it on simple, single-line list items.
- Typesafe RecyclerView now depends on Kotlin
- Typesafe RecyclerView has a new `ItemViewHolder` helper class for simple but common use cases.

## Version 1.2 (2016-09-19)
This version adds the `setHost(Host host)` method in `ViewWrapper.Binder` interface where `Host` can
be any type you want you can use from the implementing item View to communicate with your Activity,
Fragment, Presenter, or whatever. Note this adds a third type parameter to the `ViewWrapper` class,
and a second one for the `ViewWrapper.Binder` class.

## Version 1.1 (2016-09-11)
This version adds the `setViewHolder(ViewWrapper holder)` method in `ViewWrapper.Binder` interface
so list item `View`s can now get a reference to their `ViewHolder`, and call `getAdapterPosition()`
on it for example.

## Version 1.0 (2016-08-24)
This is the first release of Splitties. It includes two independent modules:
- Typesafe RecyclerView
- Selectable ViewGroups

For gradle projects from `jcenter()` repo:

``` groovy
compile 'xyz.louiscad.splitties:selectableviewgroups:1.0'
compile 'xyz.louiscad.splitties:typesaferecyclerview:1.0'
```
