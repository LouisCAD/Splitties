# Views DSL AppCompat

*AppCompat extension of [Views DSL](../views-dsl)*

## How AppCompat works with xml

When using an AppCompat theme, the `LayoutInflater` replaces the platform
widgets like `TextView` and `Button` found in your xml layouts by a
compatibility version (i.e. `AppCompatButton`, `AppCompatTextView`, etc.).

If you're curious to see how it works, look for the method `createView` in the
`AppCompatViewInflater` class from the `androidx.appcompat.app` package.

## How AppCompat works with Splitties Views DSL

Since the `LayoutInflater` only works on xml, if you use `view(::TextView)` with Views DSL,
you get a `TextView` instance, not an `AppCompatTextView` instance. This means it
will not have AppCompat features and styling (e.g. auto size for `TextView`).

However, if you use `textView()` it will automatically delegate to
this split if in the dependencies, returning an `AppCompatTextView` instance.

This works for all AppCompat widgets.

If you want to use a style defined in appcompat (like `Widget_AppCompat_Button_Colored`),
just cache locally an `AppCompatStyles` instance and use its properties and functions.

## Supported widgets

All AppCompat widgets are supported.

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

Just call the related method that is the camelCase version of the PascalCase constructor.
For example, you can call `seekBar(…) { … }` and you'll receive an `AppCompatSeekBar` instance.

Note that automatically doesn't mean magically. In fact, no reflection is involved (contrary
to xml inflation).

[You can also see the source of the function that maps to AppCompat widgets versions](
src/androidMain/kotlin/splitties/views/dsl/appcompat/experimental/AppCompatViewFactory.kt
), and the [InitProvider that makes it zero initialization on your side](
src/androidMain/kotlin/splitties/views/dsl/appcompat/experimental/AppCompatViewInstantiatorInjectProvider.kt
).

There's also support for `Toolbar` with the `toolbar` function, and `SwitchCompat` with
the `switch` function.

Note that the returned `Toolbar` handles config changes.

## Multi-process apps

If your app needs to use AppCompat themed widgets in the non default process, you'll need to
manually setup ViewFactory so it uses AppCompat. Here's how you need to it: Copy paste
[this InitProvider](
src/androidMain/kotlin/splitties/views/dsl/appcompat/experimental/AppCompatViewInstantiatorInjectProvider.kt
) into a package of an android library/app module of your project, then declare it in the
`AndroidManifest.xml` of the module exactly like it is done [here](
src/androidMain/AndroidManifest.xml
). To do so, copy paste it, then fix the package of the class under the `android:name` xml attribute
of the `provider` tag, then specify the `android:process` value to the one of your non default
process.

Be sure to test it to make sure you have set it up properly.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-views-dsl-appcompat:$splitties_version")
```
