# View DSL

*Create UIs with readable Kotlin code.*

There's a whole document about [View DSL vs xml layouts](Kotlin-UIs-vs-xml-layouts.md)
if you are not convinced yet.

*TL;DR:* Kotlin code is more concise than xml, and a small library like this
one is the proof of what is already possible with this great language.

Splitties View DSL has been designed to be:
* Simple
* Concise
* Expressive
* Explicit
* Efficient
* Reliable
* Flexible

That's 7 key considerations, which are all necessary to make a great library.

## Introduction

As said above, Splitties View DSL has been designed to be *simple*.

Consequently, you'll find no class in this split (API-wise, as strictly speaking,
all functions and properties, even top-level ones and extensions belong to a class
in the bytecode). That means you won't have to learn a whole new API to use
Splitties View DSL. You'll just have to discover the extension functions and
properties as you need them to craft your Android user interfaces with Kotlin code.

It turns out that you just need a few extension functions and properties to
make UI-related code at least as readable as xml counterparts. Note that while
putting all of your UI code directly in an `Activity` or a `Fragment` is
possible with Splitties View DSL (and can surely help for throwaway prototyping),
we will be recommending a cleaner, yet simple approach (spoiler: a custom class).

Before we dive into the details of the API, let's take a look at a simple example:

```kotlin
val launchDemoBtn = button(style = ButtonStyle.colored) {
    textResource = R.string.go_to_the_demo
}
```

**This example was meaningless**, because no one ever publishes an app with only
one button. Also, the snippet above just creates a button. If you want it into
a `ViewGroup`, or as the content of an `Activity` or a `Fragment`, you need to
do so explicitly.

There are **real examples in the sample**. You can start by taking a look at
[`MainUi`](../sample/src/main/java/com/louiscad/splittiessample/main/MainUi.kt).
You can also see a simple example that uses `ConstraintLayout` in [`AboutUi`](
../sample/src/main/java/com/louiscad/splittiessample/about/AboutUi.kt
).

_Opening the project in your IDE and navigating the sample UI code while reading
this documentation may certainly help you have a hands-on experience and be
comfortable more quickly writing UIs with Kotlin, a programming language that is
probably already familiar to you._

## Table of contents

* [The extensions](#the-extensions)
  * [Creating and configuring views](#creating-and-configuring-views)
  * [Laying out the views](#laying-out-the-views)
* [The `Ui` interface](#the-ui-interface)
  * [Why this interface](#why-this-interface)
  * [What it is made of](#what-it-is-made-of)
  * [Implementing the interface](#implementing-the-ui-interface)
  * [Using Ui implementations](#using-ui-implementations)
* [Additional modules](#additional-modules)
* [Download](#download)

## The extensions

Splitties is primarily made of extension functions and properties, to create
views with minimal code but maximum flexibility.

Just calling the constructor, then calling needed methods in an `apply { ... }`
block could be enough to use Kotlin instead of xml for your user interfaces,
but **Splitties View DSL allows something more readable**, more concise,
and with a few features, like themes, styles, and seamless AppCompat
support, **without the boilerplate**.

### Creating and configuring views

#### The most generic way: `v`

The `v` extension functions are a primitive of Splitties View DSL.
They are generic, so they allow you to instantiate a `View` of any type.

There are 6 functions named `v`, because there's **2 overload types**, and they
are made available for 3 receiver types: `Ui`, `View` and `Context`.

With respect to efficiency, they are all **inline**. That means no unnecessary
allocation that would slightly decrease performance otherwise.

Both overloads allow the following **optional** parameters:
* `@IdRes id: Int`, the id of the View. Example argument: `R.id.btn_submit`
* `@StyleRes theme: Int`, resource of a theme overlay that will be applied to
the View. Example argument: `R.style.AppTheme_AppBarOverlay`
* `initView: V.() -> Unit`, a lambda that is like `apply` for the created View. 

**The first overload** of `v` takes a required first parameter that is a function
taking a `Context`, and returning a `View`. Since constructors are also
methods in Kotlin, you can directly use a method reference like so:
`v(::View)`.
The same goes for any other `View` subclass (e.g. `v(::FrameLayout)`).
You can also use a lambda instead: `v({ FrameLayout(it) })`. In fact, that's
how you should do it while autocomplete for method references is not optimal,
then use the IDE quick action (<kbd>alt</kbd>/<kbd>⌥ option</kbd> + <kbd>⏎ enter</kbd>)
to convert it to method reference.
You can of course use any custom method reference that is not a reference to
a constructor as long as that method takes a `Context` parameter and returns a
`View` or any subclass of it.

Here's a simple but typical example of this first overload:
```kotlin
val myView: MyCustomView = v(::MyCustomView, R.id.my_view) {
    backgroundColor = Color.BLACK
}
```

**The second overload** of `v` takes no required parameter, but relies on explicit
(reified) type parameter to work properly. Just `v<TextView>()` is enough to
instantiate a `TextView`. However, this version relies on a "view factory" that
can automatically provide subclasses of the requested type as necessary. If
you use the View DSL AppCompat, you'll automatically receive instances of
`AppCompatButton` with `v<Button>` thanks to the underlying View factory.
An extra optional parameter `style: Style<V>` allows you to provide a style for
the View, that will be applied before the `initView` lambda is executed (if any).
Note that the [`Style`](/src/main/java/splitties/viewdsl/core/Style.kt) interface
has only one function that can be applied to the created view.
Some are provided by a few of the [additional modules](#additional-modules),
but creating your own is also possible, and easy.

Here's a simple example of this second overload:
```kotlin
val submitBtn = v<Button>(R.id.btn_submit) {
    textResource = R.string.submit
}
```

#### `styledV`, the generic way, which supports xml defined styles

There are some times where you need to use an xml defined style,
such as when using a style defined in AppCompat like `Widget.AppCompat.ActionButton`.

While the most used styles such as AppCompat button styles have been ported to Kotlin
in the [additional modules](#additional-modules) with
[`Style`](/src/main/java/splitties/viewdsl/core/Style.kt) implementations,
you may still need to use other styles, that are defined in xml.
You could rewrite them in Kotlin, but since it can be a bit time consuming,
we provided a way to use them with minimal code.

`styledV` works exactly like the first `v` overload described above, but has an
additional **required** parameter: `@AttrRes styleAttr: Int`. Also, its first
parameter is optional as long as the type parameter is specified or inferred.
When not explicitly referencing a constructor, the "view factory" mentioned
above for the second `v` overload is used, with its benefits.

As you can see, it doesn't expect a style resource (e.g.
`R.style.Widget.AppCompat.ActionButton`), but a theme attribute resource (e.g.
`R.attr.Widget.AppCompat.ActionButton`). This is because of a limitation in
Android where you can programmatically only use xml styles that are inside a theme.
That's doesn't mean that you will have to pollute all the themes you're using
with styles definitions though.

Android allows you to combine multiple themes with the `applyStyle(…)` method
that you can call on any `theme`, which any `Context` has. That way, you can
apply a theme that already includes references the xml styles you need with
only one line of code (you can find some in the
[additional modules](#additional-modules)).

Here's a short example:
```kotlin
context.theme.applyStyle(R.style.AppCompatStyles_ActionButton, false)
val clapButton = styledV<ImageButton>(styleAttr = R.attr.Widget_AppCompat_ActionButton) {
    imageResource = R.drawable.ic_clap_white_24dp
}
```

The first line makes sure the `theme` associated to the `context` can resolve all
the style attributes defined into `R.style.AppCompatStyles_ActionButton`, such as
`R.attr.Widget_AppCompat_ActionButton`.

You can write your own theme + `attrs.xml` file that references your custom
xml styles, or the ones that a library may provide to you. The convention
is to use the name of the style as the name for the attr, so it's easy to
see the relation without being confused.

#### The most beautiful ways: explicitly named aliases to the generic way

Instead of using `v<Button>(…) { … }` to create a `Button` instance, you can use
`button(…) { … }`. The parameters are exactly the same as `v`.

Such methods exist for most `View`s and `ViewGroup`s included in Android, and
there's more in the [additional modules](#additional-modules).

You can see implementations for [Views](src/main/java/splitties/viewdsl/core/Views.kt)
and [ViewGroups](src/main/java/splitties/viewdsl/core/ViewsGroups.kt).

These methods are a bit more natural to read and to write, but they are really
just **inline** aliases, purely syntactic sugar.

You can define your own if you want. Just make sure to write it first for
`Context` and add two overloads for `View` and `Ui` that delegate to the
one for `Context`. Also, remember to make them inline to avoid lambda allocation.

#### View extensions

For even more expressive UI code, Splitties View DSL has a transitive dependency on
the [Views split](../views) that provides a useful set of Kotlin-friendly extension
functions and properties dedicated to `View`s and some of their subclasses.

### Laying out the views

#### `ViewGroup.add(…)`, an alias to `ViewGroup.addView(…)`

To add a `View` to a `ViewGroup` in code, you can use `View.addView(…)`. However,
this can become quite redundant to have `View` repeated over and over when it's
already obvious that you are in a UI centric class that passes a parameter that is
clearly a `View`.

That's why this split has an **inline** alias to it named just `add(…)` for `ViewGroup`.
It has the extra benefit of returning the passed `View`, which can be handy in some
situations.

#### The `lParams` extension functions

The `ViewGroup.add(…)` functions requires an instance of `ViewGroup.LayoutParams`,
but creating them at hand would be cumbersome.

Splitties provides several methods named `lParams(…) { … }` for the 2 Android's
built-in `ViewGroup`s: `LinearLayout` and `FrameLayout`. You can find support
for additional `ViewGroup`s in the [additional modules](#additional-modules).

Here's the contract that every `lParams` function must respect:
1. The receiver is the type of the target `ViewGroup` subclass.
2. The function returns the `LayoutParams` for the target `ViewGroup`.
3. The first parameter is `width` and defaults to `wrapContent`, unless otherwise noted.
4. The second parameter is `height` and defaults to the same value as `width`.
5. There may be additional parameters, with default values if possible.
6. The last parameter is a lambda with `LayoutParams` as a receiver and is executed exactly once,
last (i.e. after any logic that the `lParams` implementation may have).
7. If the `lParams` function targets a `ViewGroup` that has a superclass that also has its own
`LayoutParams`, and its own `lParams` function, it should be named `defaultLParams` instead to
prevent any overload resolution ambiguity. A great example is `AppBarLayout` that is a child class
of `LinearLayout` and has such extension functions for `LayoutParams`.

##### **WARNING** regarding `lParams` and `defaultLParams` usage:

`lParams` and similar functions are resolved based on the type of their receiver.
However, unless you prepend `lParams` or `defaultLParams` call with `this.`, the received is picked
implicitly, and can be indirect, possibly causing the wrong `lParams` method to be used.

Here's a short, example:

You're in a `FrameLayout` (because you're writing a subclass of it, or because you're in a
`frameLayout` lambda).
You call `constraintLayout { ... }` and start adding views inside it, but when you call `lParams`,
you may use the implementation for `FrameLayout`, and wonder why the
`ConstraintLayout.LayoutParams` properties and extensions are not available.
To highlight such errors, you can prepend `this.` to your suspicious `lParams` calls, and if they
are in red, then you used the wrong one for the `ViewGroup` you're in. The IDE should quickly
fix add the proper import at this point, and you can then safely remove the `this.` prefix.

To avoid this issue, you can be alert when you're typing/auto-completing `lParams` and
`defaultLParams` and make sure that you're selecting the extension for the type of the `ViewGroup`
you're in (direct parent of the child View you are adding).

#### ViewGroup extensions

**TK:** Talk about things like margin (common stuff), then about what can be added for more specific
ViewGroups
 
* The `add` extension function available on `ViewGroup` that does the same
as `v`, but also requires a `ViewGroup.LayoutParams` (or subclass) parameter
used to add the created View to the `ViewGroup`.
* `lParams` extension function on `LinearLayout` and `FrameLayout` (
[`ConstraintLayout`](../viewdsl-constraintlayout/README.md),
and [Design Support Library ViewGroups](../viewdsl-design/README.md) have
theirs too, and you can very easily make one for any other `ViewGroup`),
which allows to easily set layout parameters that you can use in
`add`, with type safety.
* `wrapContent` and `matchParent` inline extensions properties on
`ViewGroup` are convenience aliases to `ViewGroup.LayoutParams.WRAP_CONTENT`
and `ViewGroup.LayoutParams.MATCH_PARENT`.
* `horizontalMargin`, `verticalMargin` and `margin` for convenient margins
definition in layout parameters (`ViewGroup.MarginLayoutParams` which is the
base of nearly all LayoutParams).
* `startMargin` and `endMargin` which are compatible below API 17 (using LTR)
and fix the inconsistent name ordering (`leftMargin`, but `marginStart`?).
* `verticalLayout` and `horizontalLayout` which return a `LinearLayout` with
the orientation you expect to use with `v` or `add`.

## The Ui interface

This section doesn't just writes so many words about how **the `Ui` interface
has only 2 properties**. It explains why **it is useful**, how to **use it the right
way**, and the **possibilities** it gives you.

### Why this interface

As said above, you can put your UI code directly in an `Activity` or a `Fragment`,
but the fact you can doesn't mean you should. Mixing UI code with business logic,
data storage code, network calls and miscellaneous boilerplate in the same
"god" class will quickly make further work (like feature additions and maintenance)
very hard, because you're likely not a god programmer, and even if you are, your
coworkers, or successors, are likely not.

Xml layouts alleviate this issue by forcing you to put most of your UI code into
a separate xml file, but you often need complementary code (e.g. to handle
transitions, dynamic visibility), and this is often put into a `Fragment` or an
`Activity`, which makes things worse, as you now have your UI code spread over
at least two places that are tightly coupled.

### What it is made of

With Splitties View DSL, there's an [optional interface named `Ui`](
src/main/java/splitties/viewdsl/core/Ui.kt), whose implementations are meant
to contain your UI code.

It has a `ctx` property because in Android, a `Context` is needed to create a
`View`.
It has a `root` property because you need a `View` to display in the end.

Since you're using Kotlin code, you can put all the UI related logic in it too,
in a single place this time.

Also, since `Ui` is an interface, you can get creative by creating sub-interfaces
or sub-classes to have different implementations of the same UI, which is nice for
A/B testing, user preferences (different styles that the user can pick),
configuration (like screen orientation), and more.

### Implementing the Ui interface

When writing a `Ui` implementation, override the `ctx` property as the first
constructor parameter (e.g. `class MainUi(override val ctx: Context) : Ui {`),
and override the `root` parameter as a property with a backing field by
assigning it a `View` (e.g. `override val root = coordinatorLayout { ... }`).

Then create your views (usually putting them as final properties, like `root`),
and add them to the `ViewGroup`s they belong to, so they are direct, or indirect
children of `root` (in the likely case where you have multiple views in your UI
and `root` is therefore a `ViewGroup`).

### Using Ui implementations

To use a `Ui` implementation from an Activity, just call `setContentView(ui)`.
To use it from any other place, just get the `root` property. In a `Fragment`,
that will mean returning it from `onCreateView(…)`.

You can also use any function or property you've declared in your sub-interface
or implementation.

Here are two examples:
* Using a public property of type `Button` to set it an `OnClickListener` in
the place where the `Ui` is used (like an `Activity` or a `Fragment` that connects
your UI to a `ViewModel` and any other components).
* Call a method called `animateGoalReached()`.

## Additional modules

**There are additional splits for extended support. View DSL…**
* [AppCompat](../viewdsl-appcompat) provides proper styling to `Button`,
`TextView`, `EdiText` and other widgets.
* [AppCompat styles](../viewdsl-appcompat-styles) provides pre-styled AppCompat
views like `coloredFlatButton`.
* [ConstraintLayout](../viewdsl-constraintlayout) provides support for
`ConstraintLayout.LayoutParameters`.
* [Design](../viewdsl-design) provides extensions for design support library
`ViewGroup`s and bottom sheets.
* [IDE preview](../viewdsl-ide-preview) provides the ability to preview your
user interfaces right from the IDE.
* [RecyclerView](../viewdsl-recyclerview) provides extensions to have
scrollbars and proper `itemView` layout parameters.

## Usage

### Creating and adding Views

Use `v(…) { … }` to create a `View` from a `Context`, a `View` or a `Ui`:

```
v(::YourView, id = R.id.id_of_your_view, theme = R.style.your_theme) {
    // The created View is the receiver, configure it there.
}
```

In the snippet above:
* `::YourView` is a method reference that automatically
maps to the `YourView` constructor with a single `Context` parameter. It is
equivalent to the following inline lambda: `{ YourView(it) }` where `it` is
a `Context`.
* `id` is an optional parameter.
* `R.id.id_of_your_view` would be declared in xml, [as done in the sample](
../sample/src/main/res/values/view_ids.xml).
* `theme` is an optional parameter.
* The optional lambda has the created View as a receiver so you can
configure it.

Here's a more practical example below:

```kotlin
val headlineTextView = v(::textView, R.id.tv_headline) {
    textResource = R.string.welcome_to_my_awesome_app
    textAppearance = R.style.TextAppearance_AppCompat_Headline
}
```

You may have noticed we are using the `textView` function here instead of
the `TextView` constructor. `textView` is a method from the
[View DSL AppCompat split](../viewdsl-appcompat) and calls the
`AppCompatTextView` constructor, just like the layout inflater is doing
with `TextView`s from xml layouts when using an AppCompat theme. It can be
used here as a method reference too because its only parameter is of type
`Context`.

To create a `View` and add it directly to a `ViewGroup` with
layout parameters, you can use the `add` extension function on `ViewGroup`
instead of a combination of `v` and `ViewGroup`'s `addView` member method:

```
add(::YourView, id = R.id.id_of_your_view, theme = R.style.your_theme, lp = lParams()) {
    // The created View is the receiver, configure it there.
}
```

You can see the usage is the same as with `v`, excepted the additional,
required `lp` parameter. This parameter is the `ViewGroup.LayoutParams`
instance that is used to add the created `View` to the `ViewGroup` on which
the `add` extension function is called.

The snippet above assumes that the receiver (aka. `this`) is a `ViewGroup`
subclass that has an `lParams` extension function to generate typesafe layout
parameters. This
[split](../README.md#what-is-a-split "What is a split in Splitties?")
includes `lParams` extension functions for `FrameLayout` and `LinearLayout`.
`ConstraintLayout` support can be found in the
[View DSL ConstraintLayout split](../viewdsl-constraintlayout),
and support for
`CoordinatorLayout` and `AppBarLayout` can be found in the
[View DSL Design split](../viewdsl-design). You can also write
`lParams` extension functions yourself in a few lines for any other
`ViewGroup` (take a look at the ones from this project if you need to write
your own).

Below is a more practical example:

```kotlin
val uiRoot = v(::FrameLayout) {
    add(::textView, lParams(gravity = Gravity.CENTER)) {
        textResource = R.string.welcome_to_my_awesome_app
        textAppearance = R.style.TextAppearance_AppCompat_Headline
    }
}
```

The `uiRoot` property is a reference to a `FrameLayout` that has a `TextView`
at its center.

In the example above, if we needed to retain a reference to the `TextView`
for later use, we would transform the code like this:

```kotlin
val headlineTextView = v(::textView)  {
    textResource = R.string.welcome_to_my_awesome_app
    textAppearance = R.style.TextAppearance_AppCompat_Headline
}

val uiRoot = v(::FrameLayout) {
    add(headlineTextView, lParams(gravity = Gravity.CENTER))
}
```

Note that in the snippet above, the called `add` extension function is just
an alias to `addView` with exactly the same parameters. This alias exists so
it matches the `add` extension function that takes a `View` initialization
lambda.

### Organizing code in `Ui` implementations

When doing user interfaces with xml layouts, you usually put the static part
of the UI in one xml file, that you use in an `Activity` or a `Fragment`,
which includes the code for the dynamic part of the UI.

Splitties View DSL offers another approach, where your UI code, both static
and dynamic is in one file, that is not an `Activity` or a `Fragment`, so
you can easily switch from one to another with minimal refactoring.

It is based on the following interface named `Ui`:
```kotlin
interface Ui {
    val ctx: Context
    val root: View
}
```  
Its `ctx` property is meant to be overriden as a constructor parameter in
the implementations of the interface. Is is used by the `v` extension
function to pass a `Context` when creating the `View`s.
 
Its `root` property is meant to be used in `setContentView(…)` in an
`Activity` or returned from `onCreateView(…)` in a `Fragment`.

See concrete examples in [`MainUi`](
../sample/src/main/java/com/louiscad/splittiessample/main/MainUi.kt) and
[`DemoUi`](
../sample/src/main/java/com/louiscad/splittiessample/demo/DemoUi.kt) with
their respective Activities [`MainActivity`](
../sample/src/main/java/com/louiscad/splittiessample/main/MainActivity.kt)
and [`DemoActivity`](
../sample/src/main/java/com/louiscad/splittiessample/demo/DemoActivity.kt).

Note that you can preview `Ui` implementations in the IDE. [See the the
View DSL IDE preview split](../viewdsl-ide-preview/README.md). 

## Download

```groovy
implementation "com.louiscad.splitties:splitties-viewdsl:$splitties_version"
```
