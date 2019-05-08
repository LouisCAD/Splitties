# Views DSL ConstraintLayout

*ConstraintLayout extension of [Views DSL](../views-dsl).*

## Table of contents

* [`ConstraintLayout` tailored `lParams` extension](#constraintlayout-tailored-lparams-extension)
* [`ConstraintLayout.LayoutParams` extensions for safe and readable usage](#constraintlayoutlayoutparams-extensions-for-safe-and-readable-usage)
* [Download](#download)

## `ConstraintLayout` tailored `lParams` extension

The `lParams()` extension function on `ConstraintLayout` looks to be similar
to the similarly named extensions on `LinearLayout` and `FrameLayout`, but
there are two key differences:
* Since `ConstraintLayout` children are meant to have constraints, their
**default `width` and `height` are `matchConstraints`, not `wrapContent`**.
That means that you'll often have to specify `height = wrapContent` for
things like `TextView`s and `Button`s.
* In xml, `match_parent` is not supported for `ConstraintLayout` and can
decrease performance (the alternative being adding 0dp + 2 parent relative
constraints). This is not the case here as **`matchParent` is rewritten as
`matchConstraints` with the appropriate parent relative constraints**. The
result is a more readable UI code, without performance compromises.

## `ConstraintLayout.LayoutParams` extensions for safe and readable usage

With this split also comes a set of extension functions to use in
`lParams(…) { … }`.

Center relatively to parent:
* `centerHorizontally()`
* `centerVertically()`
* `centerInParent()`

Parent relative constraints:
* `topOfParent()`
* `bottomOfParent()`
* `startOfParent()`
* `endOfParent()`
* `leftOfParent()`
* `rightOfParent()`

Center relatively to another `View`:
* `alignVerticallyOn(…)`
* `alignHorizontallyOn(…)`
* `centerOn(…)`

View relative constraints:
* `topToTopOf(…)`
* `topToBottomOf(…)`
* `bottomToTopOf(…)`
* `bottomToBottomOf(…)`
* `baselineToBaselineOf(…)`
* `startToStartOf(…)`
* `startToEndOf(…)`
* `endToStartOf(…)`
* `endToEndOf(…)`
* `leftToLeftOf(…)`
* `leftToRightOf(…)`
* `rightToRightOf(…)`
* `rightToLeftOf(…)`

Chains:
* `horizontalChain(…) { … }` and its companion `horizontalMargin` extension for `List<View>`.
* `verticalChain(…) { … }` and its companion `verticalMargin` extension for `List<View>`.

Barriers:
* `barrier(…)` which takes a `BarrierType` (inline class) and a list or vararg of `View`s.
* `startBarrier(…)`, `leftBarrier(…)`, `topBarrier(…)`, `endBarrier(…)`, `rightBarrier(…)` and `bottomBarrier(…)`

Guidelines:
* `verticalGuideline(…)` and `horizontalGuideline(…)` which take a begin offset (pixels),
an end offset, or a ratio (between 0 and 1).

Groups:
* `group(…)` which takes the views to group.

These methods come with a great bonus feature:

If a `View` involved in a constraint has no valid `id`, then a generated one
is automatically assigned to it!

These generated ids can't clash with aapt/xml ids, so it's safe to put
xml defined ids on some views that need to have their state saved (e.g.
a `RecyclerView`, an `EditText` or a `CheckBox`) in the same layout.

Note that `View` ids are crucial to `ConstraintLayout` machinery.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-views-dsl-constraintlayout:$splitties_version")
```
