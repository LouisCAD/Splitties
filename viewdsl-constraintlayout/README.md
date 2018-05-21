# View DSL ConstraintLayout

*ConstraintLayout extension of [View DSL](../viewdsl).*

## Content

This [split](../README.md#what-is-a-split "What is a split in Splitties?")
adds extensions to use `ConstraintLayout` with View DSL:
* An `lParams` extension tailored for `ConstraintLayout` (see details below)
* `ConstraintLayout.LayoutParams` extensions for safe and readable usage.

### `ConstraintLayout` tailored `lParams` extension

The `lParams()` extension function on `ConstraintLayout` looks to be similar
to the similarly named extensions on `LinearLayout` and `FrameLayout`, but
there are two key differences:
* Since `ConstraintLayout` children are meant to have constraints, their
**default `width` and `height` are `MATCH_CONSTRAINT`, not `wrapContent`**.
That means that you'll often have to specify `height = wrapContent` for
things like `TextView`s and `Button`s.
* In xml, `match_parent` is not supported for `ConstraintLayout` and can
decrease performance (the alternative being adding 0dp + 2 parent relative
constraints). This is not the case here as **`matchParent` is rewritten as
`MATCH_CONSTRAINT` with the appropriate parent relative constraints**. The
result is a more readable UI code, without performance compromises.

### `ConstraintLayout.LayoutParams` extensions

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

These methods check the view on which the constraint depends has a valid `id`
(unless the constraint is relative to the parent) so you don't get running
code that doesn't work as expected. (`View` ids are crucial to
`ConstraintLayout`) This fail fast behavior is there to protect you from
getting broken UIs that may escape your scrutiny.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl-constraintlayout:$splitties_version"
```
