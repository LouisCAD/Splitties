# View DSL Design

*Design Support Library extension of [View DSL](../viewdsl)*

## Content

The Android Design Support libraries has a few `ViewGroup` subclasses that
have their own `LayoutParams`. This split provides extensions on these
`ViewGroup`s to create their associated `LayoutParams` for use with View DSL
in the `add` function.

It also provides extensions for using `BottomSheetBehavior` with View DSL,
and using `TextInputLayout`.

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
- `parallaxMultiplier`, which defaults to `0.5f` (default value as of 27.1.0)

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

If you `CoordinatorLayout` has an `AppbarLayout` and scrolling content
(e.g. a `RecyclerView`),
use this method to add the scrolling content View. It sets an
`AppBarLayout.ScrollingViewBehavior` under the hood.

### Bottom sheet behavior

#### `bottomSheetBehavior`

This extension function on `Ui` that takes an optional initialization lambda
creates a `BottomSheetBehavior` to use on `CoordinatorLayout.LayoutParams`.

#### Bottom sheet state extensions

This split also includes extensions on `BottomSheetBehavior`:
* `hidden` and `expanded`: read-write extension properties.
* `hide()` and `expand()`: extension functions.

They make playing with your bottom sheets programmatically a breeze.
See their KDoc to see their exact behavior.

### TextInputLayout helper

The `addInput` extension function on `TextInputLayout` takes a required id
used for the `TextInputEditText` that it creates and adds to the layout.

The id is required so the content of the user input is saved in instance
state even if the host Activity is killed by the system.

Like `add`, it has an optional initialization lambda.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl-design:$splitties_version"
```
