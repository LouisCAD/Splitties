# Views DSL Material

*Material Components extension of [Views DSL](../views-dsl)*

## Table of contents

* [Functions to instantiate Views and ViewGroups from Material Components](#functions-to-instantiate-views-and-viewgroups-from-material-components)
  * [Multi-process apps](#multi-process-apps)
* [Use Material Components xml styles in a typesafe way](#use-material-components-xml-styles-in-a-typesafe-way)
* [Extensions on ViewGroups from Material Components](#extensions-on-viewgroups-from-material-components)
  * [`AppBarLayout` extensions](#appbarlayout-extensions)
    * [`defaultLParams`](#defaultlparams)
    * [Values for `scrollFlags`](#values-for-scrollflags)
  * [`CollapsingToolbarLayout` extensions](#collapsingtoolbarlayout-extensions)
    * [`defaultLParams`](#defaultlparams-1)
    * [`actionBarLParams`](#actionbarlparams)
    * [Values for `collapseMode`](#values-for-collapsemode)
  * [`CoordinatorLayout` extensions](#coordinatorlayout-extensions)
    * [More in Views DSL CoordinatorLayout](#more-in-views-dsl-coordinatorlayout)
    * [`contentScrollingWithAppBarLParams()`](#contentscrollingwithappbarlparams)
* [Bottom sheet behavior extensions](#bottom-sheet-behavior-extensions)
  * [`bottomSheetBehavior`](#bottomsheetbehavior)
  * [Bottom sheet state extensions](#bottom-sheet-state-extensions)
* [TextInputLayout helper](#textinputlayout-helper)
* [Download](#download)

## Functions to instantiate Views and ViewGroups from Material Components

Instead of using `view(::AppBarLayout) { … }` and similar, you can use `appBarLayout(…) { … }`.

All widgets from Material Components are supported.

To see the list, check the implementations for
[Views](src/androidMain/kotlin/splitties/views/dsl/material/Views.kt) and
[ViewGroups](src/androidMain/kotlin/splitties/views/dsl/material/ViewGroups.kt).

Note that there two bonuses in this split:
* When calling `appBarLayout(…) { … }`, you get an implementation that fixes a
scrolling bug from Material Components where first click is ignored.
* When calling `collapsingToolbarLayout(…) { … }`, you get an implementation that
handles config changes.

### Multi-process apps

If your app needs to use AppCompat themed widgets in a non default process, you'll need to
manually setup ViewFactory so it uses AppCompat. Here's how you need to it: Copy paste
[this InitProvider](
src/androidMain/kotlin/splitties/views/dsl/material/experimental/MaterialViewInstantiatorInjectProvider.kt
) into a package of an android library/app module of your project, then declare it in the
`AndroidManifest.xml` of the module exactly like it is done [here](
src/androidMain/AndroidManifest.xml
). To do so, copy paste it, then fix the package of the class under the `android:name` xml attribute
of the `provider` tag, then specify the `android:process` value to the one of your non default
process.

Be sure to test it to make sure you have set it up properly.

## Use Material Components xml styles in a typesafe way

Since Material Components styles are not included by default inside the theme, you need to
load them first. This is simply done with the following code:
```kotlin
private val materialStyles = MaterialComponentsStyles(ctx)
```

You can then use styles using the `MaterialComponentsStyles` instance. Here's an example:
```kotlin
val bePoliteBtn = materialStyles.button.outlined {
    textResource = R.string.be_polite
}
```

Styles are supported for `BottomAppBar`, `BottomNavigationView`, `MaterialButton`, `Chip`, `TabLayout` and `TextInputLayout`.

## Extensions on ViewGroups from Material Components

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

#### More in Views DSL CoordinatorLayout

This split has a transitive dependency on [Views DSL CoordinatorLayout](
../views-dsl-coordinatorlayout/README.md
) which includes `coordinatorLayout` instantiating function, as well as layout params
functions like `defaultLParams`, `appBarLParams`, and `anchorTo`.

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
implementation("com.louiscad.splitties:splitties-views-dsl-material:$splitties_version")
```
