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
but Splitties View DSL provides something more readable, more concise,
and with a few features, like themes, styles, and seamless AppCompat
support, without the boilerplate.

### Creating and configuring views

#### The most generic way: `v`

**TK (for all docs of the project): take inspiration from kotlinx.coroutines guide and Android KTX comparison doc.
kotlinx.coroutines is interesting because it show snippets after each concept to understand step by step.
Android KTX is interesting because it's so concise and familiar to Android devs.**

**TK:** Highlight the difference between the two signatures and the fact that they are both inlined.
Also, talk about styles support.

* The `v` extension function available on `Ui`, `View` and `Context` that
lets you concisely create a View using an (inlined) method reference that
takes a `Context` parameter (like all proper View constructors) with an
optional id, an optional theme id and an optional lambda to configure the
View.

#### `styledV`, the generic way, which supports xml defined styles
**TK:** Don't be too lengthy explaining how it works.

#### The most beautiful ways: explicitly named aliases to the generic way

**TK:** frameLayout, textView, button, horizontalLayout, etc.

#### View extensions

**TK:** Talk about Views split and what can be added.

### Laying out the views

#### `ViewGroup.add(…)`, an alias to `ViewGroup.addView(…)`

**TK:** Explain it's to prevent repeating the word again and again when it's already obvious you're adding a view,
and add it is inline.

#### The `lParams` extension functions

**TK:** Add a beware section for missing imports that lead to wrong layout params type in nested
ViewGroups of different types, and write advise explicitly to remember that fact.

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
