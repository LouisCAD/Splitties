# Views DSL CoordinatorLayout

*CoordinatorLayout extension of [Views DSL](../views-dsl).*

## Table of contents

* [Functions to instantiate a CoordinatorLayout](#functions-to-instantiate-a-coordinatorlayout)
* [`CoordinatorLayout` extensions](#coordinatorlayout-extensions)
    * [`defaultLParams`](#defaultlparams)
    * [`appBarLParams`](#appbarlparams)
* [`CoordinatorLayout.LayoutParams` extensions](#coordinatorlayoutlayoutparams-extensions)
    * [`anchorTo`](#anchorto)

### Functions to instantiate a CoordinatorLayout

Instead of using `view(::CoordinatorLayout, …) { … }` or `view<CoordinatorLayout>(…) { … }`,
you can use `coordinatorLayout(…) { … }`.

### `CoordinatorLayout` extensions

#### `defaultLParams`

This extension has default width and height set to `wrapContent` and an
optional `gravity` parameter.

#### `appBarLParams`

Has a `matchParent` width.
Use it when adding an `AppBarLayout` or a similar View that takes the app bar role.

### `CoordinatorLayout.LayoutParams` extensions

#### `anchorTo`

Anchors the passed View with the specified gravity, setting a generated id if it had none.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-views-dsl-coordinatorlayout:$splitties_version"
```
