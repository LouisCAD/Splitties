# Change log for Splitties

## Version 3.0.0 (2021-08-20)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

Finally! Splitties 3.0.0 is there, and it's ready to keep iterating.

The last 2.x version, the 2.1.1 dates back from 2018-11-25, close to 3 years ago.
Why did it take so long to have version 3.0.0?

Let's say making a multi-modules, multiplatform library isn't full of easy and straightforward steps.
A blog post (my first!) will be published later to share that experience from start to now.
Feel free to click follow on [blog.louiscad.com](https://blog.louiscad.com), or [on Twitter](https://twitter.com/Louis_CAD) to know when the story is published.

I'll also share a roadmap for Splitties there, which I hope will get you excited for your future endeavors.

Let's get into the actual content of this release:

### Documentation updates and improvements, and website

In case you missed it, Splitties now has a website, happily powered by [Material for MKDocs](https://squidfunk.github.io/mkdocs-material/). The address is [splitties.louiscad.com](https://splitties.louiscad.com).

The main page shows a better overview of what Splitties is about, and its content. It should be helpful for newcomers.

The setup info for each module is now upfront in their respective doc pages, be it on the website, or on GitHub, and they have been updated to recommend using the dependency notations from [refreshVersions](https://github.com/jmfayard/refreshVersions), which will also help you update Splitties, along with other libraries, and all that in less time.

Full disclosure: I am working on refreshVersions myself, in close collaboration with its author, Jean-Michel Fayard. I use it, and I totally recommend it for all Gradle projects. Splitties is using it of course.

### Bit Flags

#### Changed

The `hasFlag`, `withFlag`, and `minusFlag` extensions for unsigned integers (`UByte`, `UShort`, `UInt`, and `ULong`) are no longer annotated with the `@ExperimentalUnsignedTypes` annotation.

### Preferences

#### Added

There's a new `DataStorePreferences` class for the Android side, which you can use as a substitute of the `Preferences` class. _If you were already using `SuspendPrefsAccessor`, that's all you need to change._

This AndroidX DataStore backed implementation should remove the risks of your app and their users being affected by the potential performance issues of Android platform's `SharedPreferences`.
Be sure to check out [the updated docs](modules/preferences/README.md)!

## Version 3.0.0-rc03 (2021-08-06)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

### App Context

#### Added

The `AppCtxInitializer` class is now public, so you can put it in the list of dependencies of your own [AndroidX App Startup](https://developer.android.com/topic/libraries/app-startup) Initializer in case it relies on `appCtx` to be initialized.

### Resources

#### Fix

In 3.0.0-rc02, changes in `styledColorSL` made it break when the theme attribute was pointing to a color resource that had no selector (i.e. a non inlined plain color code like `#00bbff`). This has now been fixed, and the code also got simpler.

## Version 3.0.0-rc02 (2021-08-03)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

### Resources

#### Fixes

By resolving a subtle issue that could break IDE preview, the version 3.0.0-alpha07 of Splitties also broke the `styledColor` function and some other in come cases. If you had a color theme attribute and had a theme that was setting its value, pointing to another color resource, you'd be in luck. However, if the color value was set inline, right into the theme, it'd crash ([as you can see in this issue](https://github.com/LouisCAD/Splitties/issues/258)). This release fixes this kind of problem for all the
affected functions:
- `styledColor`
- `styledColorSL`
- `styledDimen`
- `styledDimenPxSize`
- `styledDimenPxOffset`
- `styledBool`
- `styledInt`
- `styledTxt`
- `styledStr`

## Version 3.0.0-rc01 (2021-08-01)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

This release removes previously deprecated code.

**Make sure you don't have transitive dependencies that relied on deprecated code from a previous release of Splitties!** If that's the case, you'll see the host app crash at runtime.

### Removed

The "Init provider" split has been removed and is no longer published starting with this release.

You can find if you are using it by searching for its maven coordinates:
`com.louiscad.splitties:splitties-initprovider` (the "Find in Path" IDE option can help you locate it).

If you used it, you'll need to move to [AndroidX App Startup](https://developer.android.com/topic/libraries/app-startup).

All the other previously deprecated symbols at hidden level have been removed.
This completes the deprecation cycle for the upcoming Splitties 3.0.0 release.

## Version 3.0.0-beta06 (2021-08-01)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

This release is advancing deprecation cycles further before the upcoming 3.0.0 release.

## Deprecation cycle

All previously deprecated symbols were either removed, or hidden:

- Error → Hidden
- Hidden → _Removed_

## Version 3.0.0-beta05 (2021-08-01)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

This release is advancing deprecation cycles before the upcoming 3.0.0 release.

## Deprecation cycle

All previously deprecated symbols were either removed, or moved one step closer to it:

- Warning → Error
- Error → Hidden
- Hidden → _Removed_

## Version 3.0.0-beta04 (2021-07-30)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

### Fix

Fix iOS/multiplatform publication (see [issue #280](https://github.com/LouisCAD/Splitties/issues/280)).

## Version 3.0.0-beta03 (2021-07-17)

Compiled with Kotlin 1.5.21 and kotlinx.coroutines 1.5.1-native-mt.

Other transitive dependencies have also been upgraded to the latest stable version.

It includes upgrades to various AndroidX libraries, Material Design Components 1.4.0, and Stetho 1.6.0.

This release is advancing deprecation cycles before the upcoming, long awaited 3.0.0 release.

### Coroutines

#### Deprecations

The `SendChannel.offerCatching` has been deprecated in favor of `trySend` from kotlinx.coroutines 1.5+

The deprecation level of `awaitCancellation()` has been raised from warning to error. Use the one from kotlinx.coroutines instead.

#### Lifecycle coroutines

#### Deprecations

`Dispatchers.MainAndroid` deprecation level has been raised from warning to error. Using `Dispatchers.Main` is fine performance wise since kotlinx.coroutines 1.3.3.
The `PotentialFutureAndroidXLifecycleKtxApi` annotation deprecation level was also raised to error.

The following symbols that were previously deprecated at error level are now hidden and will be removed in the next release:
- `Lifecycle.coroutineScope`
- `Lifecycle.job`
- `LifecycleOwner.lifecycleScope`
- `MainDispatcherPerformanceIssueWorkaround`

### Preferences

#### Deprecations

Raise the deprecation level of the implicit key delegates from warning to error.

### Resources

#### Deprecations

Raise the deprecation level of `Context.withStyledAttributes` to error.

### Views AppCompat

#### Deprecations

Raise the deprecation level of `ActionBar.showHomeAsUp` to error.

## Version 3.0.0-beta02 (2021-07-08)

Compiled with Kotlin 1.4.32 and kotlinx.coroutines 1.4.3-native-mt.

This is the first release of Splitties that is published on MavenCentral!
The maven coordinates are the same.

Also, there's now a documentation website on [splitties.louiscad.com](https://splitties.louiscad.com), check it out!

### Alert Dialog

Add "calls in place exactly once" contracts for the lambdas of all the `alertDialog` functions.

### Alert Dialog AppCompat

Add "calls in place exactly once" contracts for the lambdas of all the `alertDialog` functions.

### Alert Dialog AppCompat Coroutines

Fix rare crash that could happen when waiting for a button click if multiple ones were clicked simultaneously.

Now, only the first one to be considered clicked by the system will trigger, and the second one will be ignored.

### Alert Dialog Material

Add "calls in place exactly once" contracts for the lambdas of all the `materialAlertDialog` functions.

### App Context

Replace the initializing `ContentProvider` with AndroidX App Startup.

### Coroutines

Deprecate `awaitCancellation()` in favor of the one now included right into kotlinx.coroutines.

### Lifecycle Coroutines

Introduce the `whileStarted(Lifecyle)` extension function for `Flow` to have a flow emit values only while the passed `Lifecycle` is started.

### Stetho Init

Replace the initializing `ContentProvider` with AndroidX App Startup.

## Version 3.0.0-beta01 (2020-09-14)

Compiled with Kotlin 1.4.10 and kotlinx.coroutines 1.3.9-native-mt.

This release has **multiplatform** splits compatible with iOS, macOS and watchOS projects that use Kotlin/Native 1.4.0 and 1.4.10.
Feedback is appreciated (Twitter, Kotlin's Slack, GitHub issues…).

### Platforms added

watchOS support has been added to the following [splits](README.md#what-is-a-split "What is a split in Splitties?"):
- [Bit Flags](modules/bitflags)
- [Collections](modules/collections)
- [Coroutines](modules/coroutines)
- [Main Thread](modules/mainthread)
- [Preferences](modules/preferences)

All CPU architectures are supported (arm32, arm64 & X86 for simulator).

### Arch Room

#### Deprecated

Deprecated the `transaction` and `inTransaction` extension functions in favor of `withTransaction` (Room KTX) and `runInTransaction` (Room runtime) itself.

### Init Provider

#### Changed

Now requires to opt-in to `@ObsoleteContentProviderHack`.
This split will be deprecated once [App Startup from AndroidX](https://developer.android.com/topic/libraries/app-startup) goes stable.

### Lifecycle Coroutines

#### Deprecated

Deprecated symbols now have error level. Next release will remove them.

#### Removed

- `LifecycleOwner.coroutineScope` that was at error deprecation level. Use `LifecycleOwner.lifecycleScope` from AndroidX instead.

### Toast

#### Changed

Using this API now requires to opt-in to `@UnreliableToastApi` so the developers acknowledge
the gotchas of `android.widget.Toast` or use something else (like snackbars, banners or dialogs).

### Views

#### Added

Add the following read/write extensions properties for `View`:
- `startPadding`
- `endPadding`
- `leftPadding`
- `rightPadding`

#### Changed

The `onClick` parameter changed from a `crossinline` lambda to a `View.OnClickListener` now
that Kotlin 1.4 brings SAM conversion for Kotlin functions taking SAM Java interfaces.
Usage should not change. Note that there is now an implicit `it` parameter of type `View` that
might break existing code if an outer `it` was used in the `onClick` lambda.

### Views DSL

#### Changed

All lambdas in `AndroidStyles` now have a contract. That allows you to initialize `val`s declared in the outer scope.

### Views DSL AppCompat

#### Changed

- Only instantiate AppCompat version of Android widgets if the current theme inherits
an AppCompat theme.
- All lambdas in `AppCompatStyles` now have a contract. That allows you to initialize `val`s declared in the outer scope.

#### Added

Add the missing AppCompat version of `ToggleButton`.

### Views DSL Material

#### Changed

- Only instantiate Material Components version of Android widgets if the current theme inherits
a material theme.
- All lambdas in `MaterialComponentsStyles` now have a contract. That allows you to initialize `val`s declared in the outer scope.

#### Added

Add the following missing Material Components counterparts of Android widgets:
`CheckBox`, `RadioButton`, `TextView`, `AutoCompleteTextView`.


## Version 3.0.0-alpha07 (2020-09-01)

Compiled with Kotlin 1.3.72 and kotlinx.coroutines 1.3.8.

**This release introduces 2 new splits:**
- [Alert Dialog Material](modules/alertdialog-material) (Android only) It is included in the "Android Material Components" fun pack. Thanks @ivoberger for the contribution!
- [Coroutines](modules/coroutines) (supports macOS, iOS, JS, JVM & Android) It is included in the "Android base" fun pack.

### Alert Dialog and Alert Dialog AppCompat

#### Added

Add `isCancellable` parameter (defaults to `true`, as when unspecified) to `alertDialog` builders.

### Arch Lifecycle

#### Added

- `viewModels { … }` for `FragmentActivity` and `Fragment`
- `activityViewModels { … }` for `Fragment`

#### Changed

This split no longer depends on the `androidx.lifecycle:lifecycle-extensions` artifact that has been deprecated in AndroidX Lifecycle 2.2.0 and is no longer published in later versions.

#### Deprecated

AndroidX Lifecycle KTX artifacts caught up with features that this split originally provided, so we've deprecated that overlap:

- `activityScope()` for `Activity` -> `viewModels()`
- `activityScope()` for `Fragment` -> `activityViewModels()`
- `fragmentScope()` for `Fragment` -> `viewModels()`

Also, we provided variants of these that took a lambda. Since AndroidX doesn't provide such a facility, they have been kept, but the old naming has been deprecated to match the AndroidX naming.

_Note that these changes provide a ReplaceWith clause for easy migration._

**Important:** Next alpha release will move the deprecation level to error, and the alpha release after will remove them completely. (So it's best to not skip this update if you were using these extensions.)

### Collections

#### Added

`forEachReversedWithIndex` extension for `List` now has an `allowSafeModifications` parameter.
When set to `true` (default is `false`), you can mutate the list as long as it doesn't prevent the
next iteration from happening (or that you perform a non local return to stop iterating altogether).

_As usual, unsafe operations while iterating a list can result in a `ConcurrentModificationException`
or in an `IndexOutOfBoundsException` to be thrown._

### Dimensions

#### Changed

The `dip` and `dp` functions now return the type of their argument (`Int` or `Float`).
_You'll need to migrate usages of the previous `dp` function, so they pass a `Float`. Use "Find in Path" in the IDE to find them before fixing. If you often passed the same value to `dp`, the "Replace in Path" IDE option can save you even more time._

### Lifecycle Coroutines

#### Added

- `Lifecycle.isStartedFlow()`
- `Lifecycle.isStartedFlow(timeout: Duration)`
- `Lifecycle.isResumedFlow()`
- `Lifecycle.isResumedFlow(timeout: Duration)`
- `Lifecycle.stateFlow(): Flow<Lifecycle.State>`

#### Changed

The following extension functions for `Lifecycle` have been promoted to `@ExperimentalSplittiesApi` (from `@PotentialFutureAndroidXLifecycleKtxApi`):
- `createScope`
- `createJob`
- `awaitResumed`
- `awaitStarted`
- `awaitCreated`
- `awaitState`

#### Deprecated

`Dispatchers.MainAndroid` is no longer needed and has been deprecated since the performance issue that affected `Dispatchers.Main` has been fixed since kotlinx.coroutines `1.3.3`.

The following extension properties have been deprecated because they are now provided by AndroidX Lifecycle Runtime KTX:
- `Lifecycle.coroutineScope`
- `LifecycleOwner.lifecycleScope`

`Lifecycle.job` has also been deprecated even though there's no as-concise replacement because it doesn't satisfy a common use case.

### Main Thread

#### Added

Now supports macOS, iOS and JS.

### Material Lists

#### Changed

- Ensure all `TextView`s in the list items are at least one line tall, even if the text is empty.
- Support enabled/disabled state by making child views duplicate parent state.

### Permissions

#### Added

New `ensureAllPermissions` function available in top-level and as extension for `FragmentActivity` and `Fragment` to request multiple permissions in a row and ensure you have them all granted.

### Preferences

#### Added

- Now supports macOS and iOS (backed by `NSUserDefaults`, but supports custom implementation too, just like on Android).
- Added `preferences` property to `PrefDelegate`s.
- Added `key` property to `PrefDelegate`s.
- Added `valueFlow()` function to `PrefDelegate`s to get current value and changes of a pref field.

#### Changed

- The `availableAtDirectBoot` parameter has been renamed to `androidAvailableAtDirectBoot`.
- The `XxxPref` classes (e.g. `BoolPref`, `StringPref`, etc.) are no longer inner classes but are now part of the `PrefDelegate` sealed class hierarchy.

### Resources

#### Added

- New `resolveThemeAttribute` extension function for `Context`. This is the replacement for `withStyledAttributes`.

#### Deprecated

- `withStyledAttributes` must be replaced by new `resolveThemeAttribute` that also has an implementation that is working reliably in IDE Preview. It will be removed in a future release.

### Snackbar

#### Changed

- The `snack`, `longSnack` and `snackForever` extension functions now work with any `View` instead of just `CoordinatorLayout`.

### System Services

#### Added
- Add `biometricManager` from API 29.
- Add `roleManager` from API 29.

### Views AppCompat

#### Added

- `configActionBar` extension function for `AppCompatActivity`.
- `homeAsUp` extension read/write property for `ActionBar`.

#### Changed

- `ActionBar.showTitle` now supports reading current value.
- `ActionBar.showHome` now supports reading current value.
- `ActionBar.useLogo` now supports reading current value.
- `ActionBar.showCustomView` now supports reading current value.

#### Deprecated
- `ActionBar.showHomeAsUp` has been deprecated and must be replaced by `homeAsUp`. It will be removed in a future release.

### Views Coroutines & Views Coroutines Material

#### Bug fixes

- Fix a very rare crash that would occur when performing two clicks or long clicks in a row (e.g. by calling `performClick` twice without a UI thread dispatch) when using `View.awaitOneClick()`, `View.awaitOneLongClick()` or `FloatingActionButton.showAndAwaitOneClickThenHide()`.

### Views DSL

#### Added

- `UiPreView` is now included by default, the extra "Views DSL IDE Preview" module is no longer needed. If you never use it in your production code, R8 should remove it from your release app. This has been done to simplify setup.
- The `isInPreview` extension properties for `Ui` and `View` allow you to condition content to show based on whether it's the actual app or the IDE preview. Note that it statically evaluates to `false` in release builds (unlike `View.isInEditmode`), so the compiler will remove any code placed in the branch of an `if (isInPreview)` condition, and will allow R8 to remove any code that was only used in IDE preview.
- There's 2 new overloads of the `ViewGroup.add` extension functions that take either a `beforeChild` or an `afterChild` parameter. You must use the parameter name to call one of these overloads. It comes handy in `ViewGroup`s where the order of the child `View`s matters (e.g. `FrameLayout` and `LinearLayout`).
- `space` to create an `android.widget.Space` from a `Ui`, a `View` or a `Context` reference. Thanks to @Miha-x64 for the contribution!

#### Changed

- `UiPreView` now shows known error cases in the preview itself with a red warning triangle icon.

### Views DSL ConstraintLayout

#### Added

- Add new extensions: `above`, `below`, `before` and `after` for `ConstraintLayout.LayoutParams`.

### Views DSL Material

#### Added

Add `slider`, `rangeSlider` and `shapeableImageView` extensions for `View`, `Ui` and `Context`. They can instantiate the new widgets from the version 1.2.0 of the material-components-android library.

### Views DSL IDE preview

**This module has been deprecated.** It will no longer published in future releases.

Its content has been moved to the main "Views DSL" split.


## Version 3.0.0-alpha06 (2019-05-03)

Compiled with Kotlin 1.3.31.

### Permissions
Handle empty `grantResults` for permission request ([#191](https://github.com/LouisCAD/Splitties/issues/191)).


## Version 3.0.0-alpha05 (2019-04-29)

Compiled with Kotlin 1.3.31.

**This release introduces 3 new splits:**

- [Alert Dialog AppCompat Coroutines](modules/alertdialog-appcompat-coroutines)
- [Permissions](modules/permissions)
- [Views Coroutines Material](modules/views-coroutines-material)

**The most important change** though, is how **simpler** integrating Splitties in your Android projects has become starting from this release, thanks to the new grouping artifacts.

See their content and their maven coordinates [in the dedicated part of the README](README.md#gradle-instructions).

There is also new features and changes in existing splits, as detailed below.

#### Alert Dialog & Alert Dialog AppCompat
The `alert` functions have been deprecated in favor of a more accurate naming: `alertDialog`.
These `alertDialog` functions are now usable on `Context` (vs previously only on `Activity`), and they have optional parameters to specify the title, the message and even an icon (using a resource id or a `Drawable`.

Also, the `title` and the `message` properties are now nullable, in respect to their accepted value.

#### Fragments
The `show` and `showAsync` extension functions allow you to show a `DialogFragment` without fearing the infamous `IllegalStateException` if the state has already been saved, because it will wait for the lifecycle to be in the RESUMED state before showing the `DialogFragment`.
These 2 extension functions are defined for `FragmentManager`, `FragmentActivity` and `Fragment`.

`show` is a suspending function that resumes after the lifecycle was resumed and the `DialogFragment` was shown.
`showAsync` is when you are outside of a coroutine but it is marked as experimental because it has "async" in its name while not returning a `Deferred`. Feel free to suggest a better name in the issues or in the `#splitties` channel of Kotlin's Slack.

#### Lifecycle Coroutines
New suspending inline extensions functions for `Lifecycle` have been added for convenience:
`awaitResumed`, `awaitStarted` & `awaitCreated`.

They can replace code like `awaitState(Lifecycle.State.RESUMED)` for improved readability.

#### Material Lists
A new `IconTwoLinesCheckBoxListItem` class has been added. What it does is self-explanatory.

Also, all the list items are now fully xml friendly.

#### Resources
The `colorSL` and `appColorSL` extension functions no longer return the nullable version of `ColorStateList`.

#### Views DSL ConstraintLayout

All the `ConstraintLayout.LayoutParams` extension functions now have overloads that allow specifying the margin.

For example, the following code:
```kotlin
centerHorizontally()
horizontalMargin = dip(16)
```
can now be written on one line: `centerHorizontally(margin = dip(16))`.

That improves readability as the word "horizontal" is no longer repeated, and it is still explicit.

#### Views Material

Two set-only extensions properties have been added for `MaterialButton`:
`iconResource` and `iconTintAndTextColor`.


## Version 3.0.0-alpha04 (2019-03-03)

Compiled with Kotlin 1.3.21.

### New features
- The `wrapContent` and `matchParent` extensions for `ViewGroup` now apply for `View` too.
- Add `wrapInScrollView` and `wrapInHorizontalScrollView` extension functions for `View`.
- Add experimental multiplatform support with initial Kotlin/JS support for the `Bit Flags` and `Collections` splits.

### Changes

- Rename `LifecycleOwner.coroutineScope` to `lifecycleScope`. This change is binary compatible.
- Make `wrapInRecyclerView` lambda inline. This change is not binary compatible.

### Fixes

- Remove contract in the `Intents` split that would cause compilation to fail when used.


## Version 3.0.0-alpha03 (2019-02-05)

Compiled with Kotlin 1.3.20.

**This release introduces a new split: [Views Coroutines](modules/views-coroutines/README.md).**

### New features
- Added `radioGroup { ... }` functions in Views DSL.
- Added first class support for `ConstraintLayout` barriers, guidelines and groups.
- New `styledView` function for use when making an API for xml styles usage in Kotlin. See an example in [AppCompatStyles](modules/views-dsl-appcompat/src/androidMain/kotlin/splitties/views/dsl/appcompat/AppCompatStyles.kt).
- The [MaterialComponentsStyles](modules/views-dsl-material/src/androidMain/kotlin/splitties/views/dsl/material/MaterialComponentsStyles.kt) class brings access to all the xml styles defined in Google's Material Components library for Android in a typesafe way.
- Add `materialCardView { ... }` functions in Views DSL Material.
- Add `navigationView { ... }` functions in Views DSL Material.
- Make `EditText` inputType typesafe with the set only `type` extension property and the `InputType` inline class.
- Views DSL IDE Preview now supports `CoroutineContext` and `CoroutineScope` as constructor parameters for `Ui` subclasses.
- Added [contracts](https://kotlinlang.org/docs/reference/whatsnew13.html#contracts) for all the `lParams` functions from Views DSL and variants. Also added to the `roomDb` function from Arch Room as well as the `verticalListLayoutParams` and `horizontalListLayoutParams` functions from Views DSL RecyclerView.
- Add support for unsigned numbers in Bit Flags (i.e. `UByte`, `UShort`, `UInt` and `ULong`).

### Changes
- When using the `button` function from Views DSL, `MaterialButton` is now automatically used in place of `AppCompatButton` if you also use Views DSL Material.
- Make `mainHandler` async by default to avoid vSync delays. It is used for `Dispatchers.MainAndroid`, so it will result in speed improvements when using Lifecycle Coroutines. If you really need sync behavior, you can use the new `mainHandlerSync` top level property instead.
- Make `Dispatchers.MainAndroid` of type `MainCoroutineDispatcher` so the `immediate` property is available.
- Make the `awaitState` function from Lifecycle Coroutines safe to use off the main thread, and document it.
- Call `validate()` from the `lParams { ... }` function for `ConstraintLayout`.
- Make xml styles related classes inline again (thanks to compiler bug fixed in Kotlin 1.3.20).
- The reified generic variant of `view` from Views DSL is now an internal API.
- `ViewFactory` and related symbols are now an internal API.
- Mark some SystemServices as nullable to be instant app tolerant. That includes `WallpaperManager`,`WifiManager`, `WifiP2pManager`, `UsbManager`, `DevicePolicyManager`, `FingerprintManager`,`ShortcutManager` and `WifiAwareManager`.
- The Views DSL IDE Preview documentation now states that running the `compileDebugKotlin` gradle task is enough to update the preview. This is faster than a full build.

### Fixes
- Make `viewFactory` from Views DSL internal API compatible with IDE Preview.

### Deprecation
- The `illegal` top level function from Exceptions has been deprecated in favor of `error` from Kotlin stdlib.

### New artifact
This release has the following new artifact:
```
"com.louiscad.splitties:splitties-views-coroutines:3.0.0-alpha03"
```


## Version 3.0.0-alpha02 (2019-01-06)

**This release introduces a new split: [Lifecycle Coroutines](modules/lifecycle-coroutines/README.md).**

### New artifact
This release has the following new artifact:
```
"com.louiscad.splitties:splitties-lifecycle-coroutines:3.0.0-alpha02"
```


## Version 3.0.0-alpha01 (2018-12-21)

This release is compiled with Kotlin 1.3.11.

It is a breaking release (more details in the changes section), and the API is subject to changes
as it is back to an alpha stage.
APIs that are likely to change have an experimental annotation that triggers a warning
(which can be removed by opt-in), to prevent you from using them unintentionally.

### Migration to AndroidX

All the old support library artifacts have been replaced by AndroidX ones.

If your project has not migrated to AndroidX yet, please follow the quick steps below.

<details>
<summary>
<b>
Migrating your project to AndroidX in a `fun` way. (Click to expand)
</b>
</summary>

Theoretically, migrating a project to AndroidX is easy: you just select "Migrate to AndroidX" from
the "Refactor" menu. Unfortunately, in addition to being unacceptably slow, it didn't work properly
for Splitties (except for a past attempt which had to be abandoned for API stability reasons). _Our
experience was waiting minutes with an unresponsive IDE, then giving up with no other choice than
force closing Android Studio, and finally getting a broken project, with some dangling fully
qualified references (instead of proper import replacement). We reverted and looked for
an alternative that would work properly, and perform faster._

The solution has been a Kotlin script that is a white box, and runs in a matter of seconds. You can
use it for your project too, so you can migrate to AndroidX quickly, and in a `fun` way.

It is available [here](scripts/AndroidX-migrator.kts), and depends on
[this csv file](scripts/androidx-class-mapping.csv).

_Note that this script doesn't migrate the dependencies, because we changed the way we define
dependencies (using constants defined in `buildSrc`), and it would have been harder to handle
all the edge cases, and doing it by hand with Replace in Path from IDE was quick enough for us.
If you prefer to have it, you are free to contribute and reach out in the issues or elsewhere._

To use it in your project, follow these simple steps:
1. Replace the support libraries dependencies by AndroidX dependencies (and update Splitties ones if
you already used it).
2. Copy paste the two files linked above at the root of the gradle project.
3. Edit the `expectedNumberOfModules` property defined in the `AndroidX-migrator.gradle.kts` file
to match the number of modules that your project has.
4. Make sure you have `kotlinc` 1.3+ available (see [easy installation in official docs here](
https://kotlinlang.org/docs/tutorials/command-line.html
)).
5. Open a terminal at the root of the gradle project.
6. Run `kotlinc -script AndroidX-migrator.gradle.kts` and wait for completion.
7. Sync gradle project.
8. Build the project to ensure everything has migrated properly, or fix and try again.

</details>

### Improved API to use xml styles defined in Android or AppCompat
Now, you pass the `Context` only once to the `AndroidStyles` or `AppCompatStyles` constructor,
and you no longer have to pass it to the subsequent functions call. It is advised to obviously
cache this instance to reduce boilerplate and avoid overhead. This is a breaking change.

### Package name changes and replaced artifacts

The design support library no longer exists in AndroidX. It is replaced by several AndroidX
artifacts and the Google Material Components library.

Consequently, the package names no longer reference "design" but "coordinatorlayout" and "material"
instead.

As you can see below, the design support library dependent artifacts have been replaced. Note that
Views DSL Material has a transitive dependency to Views DSL CoordinatorLayout, so you don't need to
add an explicit dependency for the latter if you already use the former.

### New artifacts
This release has the following new artifacts:
```
"com.louiscad.splitties:splitties-views-material:3.0.0-alpha01"
"com.louiscad.splitties:splitties-views-dsl-coordinatorlayout:3.0.0-alpha01"
"com.louiscad.splitties:splitties-views-dsl-material:3.0.0-alpha01"
```

### Removed artifacts
This release removes these two artifacts:
~`"com.louiscad.splitties:splitties-views-design-styles:2.1.1"`~
~`"com.louiscad.splitties:splitties-views-dsl-design-styles:2.1.1"`~


## Version 2.1.1 (2018-11-25)

This release is compiled with Kotlin 1.3.10.

### Changes
- Enforce read-only in the `withExtras` extension function for `Activity`. Any attempt to mutate
a property inside it will result in an `IllegalStateException` to be thrown, because this should be
done in `putExtras` instead. You can see more info in the updated KDoc of these functions.
- Add a `withExtras` extension function for `Intent` (previously only available for `Activity`).
- Add a `putExtras` extension function for `Activity` (previously only available for `Intent`).
- Fix nullability warning in FragmentArgDelegate.
- Add KDoc to all public symbols from the Activities split.
- Add KDoc to all public symbols from the AlertDialog split.
- Add KDoc to all public symbols from the AlertDialog AppCompat split.


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

Then, you just have to do the same for Selectable Views with `import splitties.selectableviews.` and
`import splitties.views.selectable.`… and voilà! You just migrated to latest Splitties version!

### New artifacts
<details>
<summary>
<b>
Here are all the artifacts added in this version. Just use the ones you need. (Click to expand)
</b>
</summary>

```groovy
implementation("com.louiscad.splitties:splitties-views-dsl:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-dsl-appcompat:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-dsl-constraintlayout:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-dsl-design:$splitties_version")
debugImplementation("com.louiscad.splitties:splitties-views-dsl-ide-preview:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-dsl-recyclerview:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-selectable:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-selectable-appcompat:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-selectable-constraintlayout:$splitties_version")
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

~`implementation("com.louiscad.splitties:splitties-selectableviews:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-selectableviews-appcompat:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-selectableviews-constraintlayout:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl-design:$splitties_version")`~
~`debugImplementation("com.louiscad.splitties:splitties-viewdsl-ide-preview:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version")`~


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
implementation("com.louiscad.splitties:splitties-mainthread:$splitties_version")
```

### Removed artifacts
This release removes these two artifacts:

~`implementation("com.louiscad.splitties:splitties-uithread:$splitties_version")`~
~`implementation("com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version")`~


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
implementation("com.louiscad.splitties:splitties-activities:$splitties_version")
implementation("com.louiscad.splitties:splitties-collections:$splitties_version")
implementation("com.louiscad.splitties:splitties-fragments:$splitties_version")
implementation("com.louiscad.splitties:splitties-intents:$splitties_version")
implementation("com.louiscad.splitties:splitties-material-colors:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-cardview:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-design:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-recyclerview:$splitties_version")
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
implementation("com.louiscad.splitties:splitties-alertdialog:$splitties_version")
implementation("com.louiscad.splitties:splitties-alertdialog-appcompat:$splitties_version")
implementation("com.louiscad.splitties:splitties-appctx:$splitties_version")
implementation("com.louiscad.splitties:splitties-arch-lifecycle:$splitties_version")
implementation("com.louiscad.splitties:splitties-arch-room:$splitties_version")
implementation("com.louiscad.splitties:splitties-bitflags:$splitties_version")
implementation("com.louiscad.splitties:splitties-bundle:$splitties_version")
implementation("com.louiscad.splitties:splitties-checkedlazy:$splitties_version")
implementation("com.louiscad.splitties:splitties-dimensions:$splitties_version")
implementation("com.louiscad.splitties:splitties-exceptions:$splitties_version")
implementation("com.louiscad.splitties:splitties-initprovider:$splitties_version")
implementation("com.louiscad.splitties:splitties-mainhandler:$splitties_version")
implementation("com.louiscad.splitties:splitties-material-lists:$splitties_version")
implementation("com.louiscad.splitties:splitties-preferences:$splitties_version")
implementation("com.louiscad.splitties:splitties-resources:$splitties_version")
implementation("com.louiscad.splitties:splitties-fragmentargs:$splitties_version")
implementation("com.louiscad.splitties:splitties-selectableviews:$splitties_version")
implementation("com.louiscad.splitties:splitties-selectableviews-appcompat:$splitties_version")
implementation("com.louiscad.splitties:splitties-selectableviews-constraintlayout:$splitties_version")
debugImplementation("com.louiscad.splitties:splitties-stetho-init:$splitties_version")
implementation("com.louiscad.splitties:splitties-systemservices:$splitties_version")
implementation("com.louiscad.splitties:splitties-toast:$splitties_version")
implementation("com.louiscad.splitties:splitties-typesaferecyclerview:$splitties_version")
implementation("com.louiscad.splitties:splitties-uithread:$splitties_version")
implementation("com.louiscad.splitties:splitties-snackbar:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl-appcompat-styles:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version")
implementation("com.louiscad.splitties:splitties-viewdsl-design:$splitties_version")
debugImplementation("com.louiscad.splitties:splitties-viewdsl-ide-preview:$splitties_version")
implementation("com.louiscad.splitties:splitties-views:$splitties_version")
implementation("com.louiscad.splitties:splitties-views-appcompat:$splitties_version")
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
