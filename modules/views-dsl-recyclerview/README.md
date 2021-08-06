# Views DSL RecyclerView

*RecyclerView extension of [Views DSL](../views-dsl)*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.viewsDslRecyclerview`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-views-dsl-recyclerview`.

## Table of contents

* [RecyclerView with scrollbars](#recyclerview-with-scrollbars)
* [Make any view scrollable](#make-any-view-scrollable)
* [RecyclerView item layout parameters](#recyclerview-item-layout-parameters)

## RecyclerView with scrollbars

To get scrollbars on a `View` in Android, you need to enable it in the
`android:scrollbars` xml attribute first. Only then you can disable and
re-enable them back using the `isHorizontalScrollBarEnabled` and
`isVerticalScrollBarEnabled` properties.

To overcome this problem, **this split provides a `recyclerView` function**.

This function inflates a `RecyclerView` with both horizontal and vertical
scrollbars enabled in xml, but the scrollbars will only ever appear if your
content can scroll in that direction, so you likely just have to use it.

## Make any view scrollable

Let's say you have a `LinearLayout`, or a `TextView` that is just a bit too
long to fit into all screen sizes. In xml, you'd likely use `NestedScrollView`,
or good old `ScrollView`. Unfortunately, `NestedScrollView` has bugs that may
cut off the content in some hard to reproduce consistently cases, and
`ScrollView` doesn't support nested scroll as you may need in your app.

On the other hand, there's `RecyclerView`, which has none of these issues.

This split provides a `wrapInRecyclerView` extension function for `View`
that returns a `RecyclerView` wrapping the `View` is has been called on.

It is vertical by default, but you can set the `horizontal` parameter to
`true`

Also, you can **(should)** specify the `id` so scrolling position is saved
into instance state and restored when needed.

And you have an optional lambda to configure the `RecyclerView`, which
can be useful for things like setting the padding and disabling clipping,
setting the background, etc.

Here's two small examples:

```kotlin
val content = textView {
    textResource = R.string.a_very_long_string
}.wrapInRecyclerView(id = R.id.main_content) {
    verticalPadding = dip(8)
    horizontalPadding = dip(16)
    clipToPadding = false
}
```

```kotlin
val content = textView {
    textResource = R.string.good_luck_scrolling_these_10_thousand_characters
    textSize = 48f // This size is interpreted in sp unit BTW
}.wrapInRecyclerView(horizontal = true)
```

## RecyclerView item layout parameters

`RecyclerView.onCreateViewHolder` method passes the parent `ViewGroup` so
layout parameters are properly generated when the item view is inflated from
xml. When using Views DSL in a `RecyclerView`, you are not inflating xml, so
this `parent` parameter is of no use. However, you can set the item view
layout parameters manually.

This split provides two extension functions on `RecyclerView.LayoutManager`
to make it easy: `verticalListLayoutParams` and `horizontalListLayoutParams`.
