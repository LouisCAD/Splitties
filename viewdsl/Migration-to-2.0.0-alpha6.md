# Splitties View DSL Migration to version 2.0.0-alpha6

**Note on version 2.0.0-alpha5:** This version had was breaking alpha4 API,
but then, a way to "un-break" the API was found, so alpha6 was released to
allow a smoother migration to the changes. Before migrating, please read
this guide, because there's a small important step to perform before
upgrading the dependencies.

## Changes in Splitties View DSL 2.0.0-alpha6

### `v` deprecated

Let's start with the "saddest", the deprecations, so we end up on a positive note.

The 3 overloads of `v` for `Context`, `View` and `Ui` have been deprecated in
favor of `view`.

In fact, this is just a renaming, but the old name has been kept for compatibility
so migration is easy and step by step in case you used `v` a lot of places.

Continuing reading the changes, you'll see that you'll be less likely to need
`v`/`view` now as `inline` alternatives delegating to the `view` functions
have been added for even more readable code.

### New functions to instantiate views

#### New primitives: `view`

Now that the 3 `v` overloads are deprecated, there 6 new overloads of a function
called `view`.

Why 6? Because there are 2 types of overloads, multiplied by the 3 supported
receivers: `Context`, `View` and `Ui` as usual.

The first overload type is the one you already knew when using `v`, the
parameters are still exactly the same.

The second overload is very similar, but instead of requiring a function taking
a `Context` and returning a `View` as first parameter, it just uses the reified
type parameter (that can be inferred) to instantiate the View, so the following
snippet is valid:
```kotlin
val myBtn: Button = view {
    textResource = R.string.click_me
}
```
The first goal with this second overload is not to have a new syntax, but to
allow instantiation of subclasses when needed.

If you use the View DSL AppCompat additional module, the snippet above
will automatically create an `AppCompatButton` instance, just like the
`LayoutInflater` would automatically do with your xml layout.

This works for all AppCompat widgets, but as a bonus, if you use the
View DSL Design additional module, you'll get instances of the
design support library widgets that have some known bugs fixed!

#### New inline aliases for common views

Let's start this section with an example.

The snippet above can be replaced with this one:
```kotlin
val myBtn = button {
    textResource = R.string.click_me
}
```

That is possible because there are now several functions like `button` that
enable even more readable UI code.

See the list of available functions directly in the (simple) source for:
* Android's [Views](src/main/java/splitties/viewdsl/core/Views.kt)
and [ViewGroups](src/main/java/splitties/viewdsl/core/ViewsGroups.kt).
* AppCompat [Views](../viewdsl-appcompat/src/main/java/splitties/viewdsl/appcompat/AppCompatOnlyViews.kt).
* Design support library [Views](../viewdsl-design/src/main/java/splitties/viewdsl/design/Views.kt)
and [ViewGroups](../viewdsl-design/src/main/java/splitties/viewdsl/design/ViewGroups.kt).
* [ConstraintLayout](../viewdsl-constraintlayout/src/main/java/splitties/viewdsl/constraintlayout/Views.kt).

Note that you can create you own methods similar to these for third party views and
for you custom views.

### Improved xml compatibility

#### Easily include xml layouts

There's `inflate` works just like `view`, with an extra first parameter for the
layout resource id. [Read a bit more here](README.md#inflating-existing-xml-layouts).

#### Xml styles support

Using xml styles with Splitties is now possible, with efficient and clean code.
[Read more here](README.md#using-styles-defined-in-xml).

## Migrating from 2.0.0-alpha4 to 2.0.0-alpha6 step by step

TK
