# Views RecyclerView

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.viewsRecyclerview`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-views-recyclerview`.

## Content

### RecyclerView extensions

The `fixedSize` extension property for `RecyclerView` is an alias to the
`hasFixedSize()` and `setHasFixedSize(…)` methods.

### functions to create a LinearLayoutManager

The `verticalLayoutManager` and `horizontalLayoutManager` functions allow you
to instantiate and customize a `LinearLayoutManager` with more idiomatic
Kotlin code.

### functions to create a GridLayoutManager

The `gridLayoutManager` and `horizontalGridLayoutManager` functions allow you
to instantiate and customize a `GridLayoutManager` with more idiomatic Kotlin
code.
