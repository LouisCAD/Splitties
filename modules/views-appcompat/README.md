# Views AppCompat

*AppCompat extension of [Views](../views)*

## Content

### Tooltip helpers

`tooltipTxt` allows to set a tooltip text on any `View` using property syntax
even on pre-O devices (using AndroidX's `TooltipCompat` in these cases).

The `contentDescAsTooltip()` extension function for `View` takes the content
description of your view (used for accessibility) and sets it as the tooltip
text so users can long press or hover on the view and see what it is.

### ImageView tinting

The `imgTintList` and `imgTintMode` extension properties for `ImageView` are
the same as `imageTintList` and `imageTintMode` … except they work before
API 21 because they delegate to AndroidX's `ImageViewCompat`.

### ActionBar extensions

The `configActionBar { … }` extension function for `AppCompatActivity`
allows to easily setup the `supportActionBar`. If it is null, the passed
lambda is ignored, and an `AssertionError` is logged.

The write only `showTitle`, `showHome`, `showHomeAsUp`, `useLogo` and
`showCustomView` boolean extension properties are meant to be used on an
`Actionbar` (usually inside the `configActionBar { … }` lambda). They are
more readable than the `setDisplayHomeAsUpEnabled` and alike methods.

### Config changes handling Toolbar

AppCompat `Toolbar` from AndroidX has different dimensions and text sizes
for portrait and landscape modes, but they are not updated when the
configuration changes.

`splitties.views.appcompat.Toolbar` extends it and
updates it when configuration changes so you can avoid restarting your
`Activity` when the device rotates, goes into multi-window mode or undergoes
any other window size related config change if the rest of your content
handles this without needing to be recreated.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-views-appcompat:$splitties_version")
```
