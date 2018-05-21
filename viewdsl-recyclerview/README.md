# View DSL RecyclerView

*RecyclerView extension of [View DSL](../viewdsl)*

## Content

### RecyclerView with scrollbars

To get scrollbars on a `View` in Android, you need to enable it in the
`android:scrollbars` xml attribute first. Only then you can disable and
re-enable them back using the `isHorizontalScrollBarEnabled` and
`isVerticalScrollBarEnabled` properties.

To overcome this problem, **this split provides a `recyclerView` function**
that you can use in place of the `RecyclerView` constructor in `v` and `add`.

This function inflates a `RecyclerView` with both horizontal and vertical
scrollbars enabled in xml, but the scrollbars will only ever appear if your
content can scroll in that direction.

That means you don't need to add
`isHorizontalScrollBarEnabled = false` if you use a `LinearLayoutManager` in
its default orientation (vertical). `isHorizontalScrollBarEnabled` and
`isVerticalScrollBarEnabled` properties are only useful if you have scrolling
content but you still want to hide the scrollbars, but you can directly use
`RecyclerView` constructor to get this behavior.

### RecyclerView item layout parameters

`RecyclerView.onCreateViewHolder` method passes the parent `ViewGroup` so
layout parameters are properly generated when the item view is inflated from
xml. When using View DSL in a `RecyclerView`, you are not inflating xml, so
this `parent` parameter is of no use. However, you can set the item view
layout parameters manually.

This split provides two extension functions on `RecyclerView.LayoutManager`
to make it easy: `verticalListLayoutParams` and `horizontalListLayoutParams`. 

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl-recyclerview:$splitties_version"
```
