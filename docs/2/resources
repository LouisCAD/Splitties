# Resources

*Extensions to get resources like strings, colors or drawables easily,
with support for themed attributes.*

## Content

All the extensions of this module are currently available on
`Context`, `Fragment` and `View` types.

Each one also has an `app` prefixed version (e.g. `appColor(…)`) that is
available everywhere but only returns the resources using the
application Context, which means they will not use the theme of the
current Activity.

### Colors

* `color(…)` takes a color resource id and returns a color `Int`
* `colorSL(…)` takes a color resource id and returns a `ColorStateList`
* `styledColor(…)` takes a color theme attribute and returns a `Int`
* `styledColorSL(…)` takes a color theme attribute and returns a
`ColorStateList`

#### Example

```kotlin
val brandPrimaryColor = color(R.color.brand_primary)
val accentColor = styledColor(R.attr.colorAccent)
```

### Dimensions

* `dimen(…)` takes a dimension resource id and returns its `Float` value
* `dimenPxSize(…)` takes a dimension resource id and returns its rounded
`Int` value
* `dimenPxOffset(…)` takes a dimension resource id and returns its truncated
`Int` value

Like for [colors](#colors), there are `styled` prefixed versions that take
a theme attribute.

### Drawables

* `drawable(…)` takes a drawable resource id and returns a `Drawable?`
* `styledDrawable(…)` takes a drawable theme attribute and returns a
`Drawable?`

### Primitives

* `bool(…)` takes a boolean resource id and returns its `Boolean` value
* `int(…)` takes an integer resource id and returns its `Int` value
* `intArray(…)` takes an integer array resource id and returns an `IntArray`

There are `styledBool` and `styledInt` that take a theme attribute.

### Text

* `txt(…)` takes a string resource id and returns a `CharSequence`
* `str(…)` takes a string resource id and returns a `String`. There's also
optional format arguments.
* `qtyTxt(…)` (for plurals) returns a `CharSequence`
* `qtyStr(…)` (for plurals) returns a `String`. There's also optional format
arguments.
* `txtArray(…)` takes an array resource id and returns an
`Array<out CharSequence>`
* `strArray(…)` takes an array resource id and returns an `Array<String>`
* `styledTxt(…)` takes a string theme attribute and returns a `CharSequence?`
* `styledStr(…)` takes a string theme attribute and returns a `String?`
There's also optional format arguments.
* `styledTxtArray(…)` takes an array theme attribute and returns an
`Array<out CharSequence>?`

## Download

```groovy
implementation "com.louiscad.splitties:splitties-resources:$splitties_version"
```
