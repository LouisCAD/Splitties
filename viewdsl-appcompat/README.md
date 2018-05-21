# View DSL AppCompat

*AppCompat extension of [View DSL](../viewdsl)*

## How AppCompat works with xml

When using an AppCompat theme, the `LayoutInflater` replaces the platform
widgets like `TextView` and `Button` found in your xml layouts by a
compatibility version (i.e. `AppCompatButton`, `AppCompatTextView`, etc.).

If you're curious to see how it works, look for the method `createView` in the
`AppCompatViewInflater` class from the `android.support.v7.app` package.

## How AppCompat works with Splitties View DSL

Since the `LayoutInflater` only works on xml, if you use `Button` with View DSL,
you get a `Button` instance, not an `AppCompatButton` instance. This means it
will not have AppCompat features and styling.

Fortunately, this split provides `camelCase` functions for the AppCompat
versions of these widgets.

If your project uses AppCompat and you need to create
a `TextView` or a `Button` with View DSL, use `textView` and `button`. Don't
forget to this for other widgets too, or you'll get styling issues.

## Supported widgets

All AppCompat widgets are supported as of version `27.1.1` of the support
library.

Here's the full list:
* `TextView`
* `ImageView`
* `Button`
* `EditText`
* `Spinner`
* `ImageButton`
* `CheckBox`
* `RadioButton`
* `CheckedTextView`
* `AutoCompleteTextView`
* `MultiAutoCompleteTextView`
* `RatingBar`
* `SeekBar`

Lowercase the first letter in your code and you're good to go!

[You can also see the list here, directly in the source.](
src/main/java/splitties/viewdsl/appcompat/AppCompatViews.kt
)

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl-appcompat:$splitties_version"
```
