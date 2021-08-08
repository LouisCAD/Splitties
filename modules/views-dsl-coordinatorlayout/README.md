# Views DSL CoordinatorLayout

*CoordinatorLayout extension of [Views DSL](../views-dsl).*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.viewsDslCoordinatorlayout`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-views-dsl-coordinatorlayout`.

## Table of contents

* [Functions to instantiate a CoordinatorLayout](#functions-to-instantiate-a-coordinatorlayout)
* [`CoordinatorLayout` extensions](#coordinatorlayout-extensions)
    * [`defaultLParams`](#defaultlparams)
    * [`appBarLParams`](#appbarlparams)
* [`CoordinatorLayout.LayoutParams` extensions](#coordinatorlayoutlayoutparams-extensions)
    * [`anchorTo`](#anchorto)

### Functions to instantiate a CoordinatorLayout

Instead of using `view(::CoordinatorLayout, …) { … }`, you can use `coordinatorLayout(…) { … }`.

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
