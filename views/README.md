# Views

*Extensions function and properties on `View`s.*

## Content

This split includes extensions on view related classes. They are helpful when
dealing with views programmatically.

### View background properties

`bg` is an alias to the `background` property for `View, but works below 
API 16, using `setBackgroundDrawable(…)`.

`backgroundColor` allows to set the background color of a `View` using
property syntax.

### ImageView properties

`imageResource` allows to set the image of an `ImageView` from a resource id
using property syntax.

`imageDrawable` allows to get, set or clear the image of an `ImageView` with
a `Drawable?` using property syntax.

`imageBitmap` allows to set the image of an `ImageView` from a `BitMap`
using property syntax.

### Layout direction properties

`isLtr` extension property for `View` is true on API 16 and lower, or when
the layout direction is left to right (like in English).

`isRtl` is `!isLtr`. Would be true on an API 17+ device setup in Arabic or
another RTL language, or when the device is forced to RTL in developer options.

### View padding properties

The following extension properties allow to set the padding of a `View` using
property syntax:

```
padding
horizontalPadding
verticalPadding
topPadding
bottomPadding
```

You can also use the `setPaddingDp` extension function that offers default
parameter values.

### View visibility properties

This split has 2 `Boolean` extension properties dedicated to `View`
visibility: `visible` and `gone`.

There's also a `showIf(…)` extension function that makes the `View` gone if
the argument is false, visible otherwise.

### Gravity flags aliases

Using `Gravity` constants on Android is a bit verbose.
To alleviate this small issue, this split provides extension properties for
`View` which start with `gravity`. They cover most use cases and play well
with autocomplete.

**Example:**

*Without Splitties*: `Gravity.START or Gravity.CENTER_VERTICAL`

*With Splitties*: `gravityStartCenter`

**List of supported `Gravity` flags:**
```
gravityCenter
gravityCenterVertical
gravityCenterHorizontal
gravityVerticalCenter
gravityHorizontalCenter
gravityStart
gravityTop
gravityEnd
gravityBottom
gravityStartBottom
gravityStartTop
gravityEndBottom
gravityEndTop
gravityBottomStart (alias to gravityStartBottom)
gravityTopStart (alias to gravityStartTop)
gravityBottomEnd (alias to gravityEndBottom)
gravityTopEnd (alias to gravityEndTop)
gravityStartCenter
gravityEndCenter
gravityTopCenter
gravityBottomCenter
```

### LayoutInflater

`LayoutInflater`, `Context` and `ViewGroup` have an `inflate` extension
function that make inflating xml easy thanks to type parameter.

There's also an `inflateAndAttach` extension function for `ViewGroup`.

### TextView extensions

`textResource` allows to set the text of a `TextView` from a string resource
id using property syntax.

`textColorResource` allows to set the color of the text of a `TextView` from
a color resource id using property syntax.

`textAppearance` allows to set the text appearance of a `TextView` from a
text appearance style resource id using property syntax, even below API 23.

`centerText()` sets gravity to center and center aligns the text.
`alignTextToStart()` sets gravity to start and start aligns the text.
`alignTextToEnd()` sets gravity to end and end aligns the text.

`setCompoundDrawables(…)` takes `Drawable?` parameters for `start`, `top`,
`end` and `bottom` which all default to `null`, plus an `intrinsicBounds`
parameter that defaults to `false`.

`setCompoundDrawables(…)` has an overload which takes drawable resource ids
that default to 0 (no drawable) but no `intrinsicBounds` parameter (as it is
implicitly true).

`clearCompoundDrawables()` clears all compound drawables the `TextView` has.

### Other

This split also includes an `onClick { … }` extension method for `View`. It
is slightly less efficient than using `setOnClickListener { … }` as of Kotlin
1.2 since it has to generate a SAM class converter, making the dex and the
final apk slightly bigger, but there is no significant runtime impact. This
method may get deprecated in the future for a version that would provide a
`suspend` lambda instead of a "regular" one that doesn't support suspension
points.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-views:$splitties_version"
```
