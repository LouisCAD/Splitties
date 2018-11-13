# Selectable Views

*Selectable Views with `foreground` property before API 23.*

This [split](../README.md#what-is-a-split "What is a split in Splitties?")
provides selectable `LinearLayout` and platform `TextView`. See also the
[AppCompat](../views-selectable-appcompat/README.md) and
[ConstraintLayout](../views-selectable-constraintlayout/README.md) versions.

Selectable views are particularly handy for list items that can be clicked.
They have a `foregroundSelector` property that is like `foregound` property
available on `FrameLayout` and all Views on API 23+. It defaults to
`android.R.attr.selectableItemBackground`, showing visual feedback when the
user selects the View (ripple effect on Lollipop and newer Android versions).

## Usage

Just use `SelectableLinearLayout` and `SelectableTextView` instead of
`LinearLayout` and `TextView`, and change the `foregroundSelector`
`Drawable` if the default doesn't suit your needs.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-views-selectable:$splitties_version"
```
