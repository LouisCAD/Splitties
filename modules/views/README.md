# Views

*Extensions function and properties on `View`s.*

## Content

This split includes extensions on view related classes. They are helpful when
dealing with views programmatically.

### Depends on AndroidX core KTX

Android core KTX already bundles its share of useful extensions for Views.
For example, it includes `View` visibility extension properties like `isVisible`.
To avoid duplication, Splitties deprecated symbols made redundant by Android
core KTX, and included the dependency transitively instead.

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

### EditText extensions

`type` allows to set the input type in a typesafe and more readable way using the `InputType`
inline class (lots of "type" occurrences in this sentence).

### TextView extensions

`textResource` allows to set the text of a `TextView` from a string resource
id using property syntax.

`textColorResource` allows to set the color of the text of a `TextView` from
a color resource id using property syntax.

`textAppearance` allows to set the text appearance of a `TextView` from a
text appearance style resource id using property syntax, even below API 23.

`lines` allows to set the exact number of lines of a `TextView` using
property syntax.

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

### Click

`onClick { … }` extension method for `View` avoids `it` shadowing if you have
other lambdas, while also being more expressive than `setOnClickListener { … }`.

`onLongClick { … }` is similar to `onClick { … }`, but also removes the need
to have an extra line in the lambda to return `true` or `false`.

### LayoutInflater

`LayoutInflater`, `Context` and `ViewGroup` have an `inflate` extension
function that make inflating xml easy thanks to type parameter.

There's also an `inflateAndAttach` extension function for `ViewGroup`.

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

### Other

The `generateViewId()` top level function is a backwards compatible and
more efficient version of `View.generateViewId()` that was introduced
in Android API 17.

The `assignAndGetGeneratedId()` extension function for `View` calls
`generateViewId()` assigns it to the view and returns new generated id.

The `existingOrNewId` extension property for `View` calls
`assignAndGetGeneratedId()` if the view has no id (`0`/`View.NO_ID`),
and returns the `id` of the View, existing or just generated.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-views:$splitties_version")
```
