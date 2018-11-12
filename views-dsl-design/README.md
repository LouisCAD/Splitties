# Views DSL Design

*Design Support Library extension of [Views DSL](../views-dsl)*

## Table of contents

* [Functions to instantiate Views and ViewGroups from the Design Support Library](#functions-to-instantiate-views-and-viewgroups-from-the-design-support-library)
  * [Multi-process apps](#multi-process-apps)
* [Extensions on ViewGroups from the Design Support Library](#extensions-on-viewgroups-from-the-design-support-library)
  * [`AppBarLayout` extensions](#appbarlayout-extensions)
    * [`defaultLParams`](#defaultlparams)
    * [Values for `scrollFlags`](#values-for-scrollflags)
  * [`CollapsingToolbarLayout` extensions](#collapsingtoolbarlayout-extensions)
    * [`defaultLParams`](#defaultlparams-1)
    * [`actionBarLParams`](#actionbarlparams)
    * [Values for `collapseMode`](#values-for-collapsemode)
  * [`CoordinatorLayout` extensions](#coordinatorlayout-extensions)
    * [`defaultLParams`](#defaultlparams-2)
    * [`appBarLParams`](#appbarlparams)
    * [`contentScrollingWithAppBarLParams()`](#contentscrollingwithappbarlparams)
* [Bottom sheet behavior extensions](#bottom-sheet-behavior-extensions)
  * [`bottomSheetBehavior`](#bottomsheetbehavior)
  * [Bottom sheet state extensions](#bottom-sheet-state-extensions)
* [TextInputLayout helper](#textinputlayout-helper)
* [Download](#download)

## Functions to instantiate Views and ViewGroups from the Design Support Library

Instead of using `v<AppBarLayout>(…) { … }` and similar, you can use
`appBarLayout(…) { … }`.

All widgets from the Design Support Library are supported.

To see the list, check the implementations for
[Views](src/main/java/splitties/views/dsl/design/Views.kt) and
[ViewGroups](src/main/java/splitties/views/dsl/design/ViewGroups.kt).

Note that there two bonuses in this split:
* When calling `appBarLayout(…) { … }`, you get an implementation that fixes a
scrolling bug from Design Support Library where first click is ignored.
* When calling `collapsingToolbarLayout(…) { … }`, you get an implementation that
handles config changes.

### Multi-process apps

If your app needs to use AppCompat themed widgets in the non default process, you'll need to
manually setup ViewFactory so it uses AppCompat. Here's how you need to it: Copy paste
[this InitProvider](
src/main/java/splitties/views/dsl/design/experimental/DesignViewInstantiatorInjectProvider.kt
) into a package of an android library/app module of your project, then declare it in the
`AndroidManifest.xml` of the module exactly like it is done [here](
src/main/AndroidManifest.xml
). To do so, copy paste it, then fix the package of the class under the `android:name` xml attribute
of the `provider` tag, then specify the `android:process` value to the one of your non default
process.

Be sure to test it to make sure you have set it up properly.

## Extensions on ViewGroups from the Design Support Library

### `AppBarLayout` extensions

#### `defaultLParams`

This extension function has a default width of `matchParent`
and a default height of `wrapContent` so you don't have to specify them in
most of the cases.

It also has a `scrollFlags` parameter which defaults to `ENTER_ALWAYS`, same
as when inflated from an xml layout. 

Use it when you add a `View` to an `AppBarLayout`.

#### Values for `scrollFlags`

`SCROLL`, `EXIT_UNTIL_COLLAPSED`, `ENTER_ALWAYS`, `ENTER_ALWAYS_COLLAPSED`
and `SNAP` are extension properties on `AppBarLayout` that are meant to be
used as flags (using `or` if you use multiple ones) with the `scrollFlags`
parameter of the `defaultLParams` function mentioned above. They are
provided for your convenience so they appear in autocomplete when relevant.

### `CollapsingToolbarLayout` extensions

#### `defaultLParams`

As for `AppBarLayout`, this extension function has a default width of
`matchParent` and a default height of `wrapContent` so you don't have to
specify them in most of the cases.

It also has two optional parameters:
- `collapseMode`, which defaults to `COLLAPSE_MODE_OFF`
- `parallaxMultiplier`, which defaults to `0.5f` (default value as of 27.1.1)

Use it when you add a `View` to a `CollapsingToolbarLayout`.

#### `actionBarLParams`

Similar to `defaultLParams`, but the height is `R.attr.actionBarSize`
instead of `wrapContent`. Use it when adding a `Toolbar`.

#### Values for `collapseMode`

`PIN` and `PARALLAX` are extension properties on `CollapsingToolbarLayout`
that are meant to be used for the `collapseMode` parameter of the
`defaultLParams` and `actionBarLParams` functions mentioned above. They are
provided for your convenience so they appear in autocomplete when relevant.

### `CoordinatorLayout` extensions

#### `defaultLParams`

This extension has default width and height set to `wrapContent` and an
optional `gravity` parameter.

#### `appBarLParams`

Has a `matchParent` width.
Use it when adding an `AppBarLayout`.

#### `contentScrollingWithAppBarLParams()`

If your `CoordinatorLayout` has an `AppbarLayout` and scrolling content
(e.g. a `RecyclerView`),
use this method to add the scrolling content View. It sets an
`AppBarLayout.ScrollingViewBehavior` under the hood.

This function accepts an optional config lambda.

## Bottom sheet behavior extensions

### `bottomSheetBehavior`

This extension function on `Ui` that takes an optional initialization lambda
creates a `BottomSheetBehavior` to use on `CoordinatorLayout.LayoutParams`.

### Bottom sheet state extensions

This split also includes extensions on `BottomSheetBehavior`:
* `hidden` and `expanded`: read-write extension properties.
* `hide()` and `expand()`: extension functions.

They make playing with your bottom sheets programmatically a breeze.
See their KDoc to see their exact behavior.

## TextInputLayout helper

The `addInput` extension function on `TextInputLayout` takes a required id
used for the `TextInputEditText` that it creates and adds to the layout.

The id is required so the content of the user input is saved in instance
state even if the host Activity is killed by the system.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-views-dsl-design:$splitties_version"
```
