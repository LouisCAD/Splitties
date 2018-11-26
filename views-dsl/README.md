# Views DSL

*Create UIs with readable Kotlin code.*

There's a whole document about [Views DSL vs xml layouts](Kotlin-UIs-vs-xml-layouts.md)
if you are not convinced yet.

*TL;DR:* Kotlin code is more concise than xml, and a small library like this
one is the proof of what is already possible with this great language.

Splitties Views DSL has been designed to be:
* Simple
* Concise
* Expressive
* Explicit
* Efficient
* Reliable
* Flexible

That's 7 key considerations, which are all necessary to make a great library.

## Introduction

As said above, Splitties Views DSL has been designed to be *simple*.

Consequently, you'll find no class in this split (API-wise, as strictly speaking,
all functions and properties, even top-level ones and extensions belong to a class
in the bytecode). That means you won't have to learn a whole new API to use
Splitties Views DSL. You'll just have to discover the extension functions and
properties as you need them to craft your Android user interfaces with Kotlin code.

It turns out that you just need a few extension functions and properties to
make UI-related code at least as readable as xml counterparts. Note that while
putting all of your UI code directly in an `Activity` or a `Fragment` is
possible with Splitties Views DSL (and can surely help for throwaway prototyping),
we will be recommending a cleaner, yet simple approach (spoiler: a custom class).

Before we dive into the details of the API, let's take a look at a simple example:

```kotlin
val launchDemoBtn = button {
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
    * [The most generic way: `view`](#the-most-generic-way-view)
    * [Using styles defined in xml](#using-styles-defined-in-xml)
      * [Using Android styles](#using-android-styles)
      * [Using AppCompat styles](#using-appcompat-styles)
      * [Using any other xml style](#using-any-other-xml-style)
    * [The most beautiful ways: explicitly named aliases to the generic way](#the-most-beautiful-ways-explicitly-named-aliases-to-the-generic-way)
    * [View extensions](#view-extensions)
    * [Inflating existing xml layouts](#inflating-existing-xml-layouts)
  * [Laying out the views](#laying-out-the-views)
    * [`ViewGroup.add(…)`, an alias to `ViewGroup.addView(…)`](#viewgroupadd-an-alias-to-viewgroupaddview)
    * [ViewGroups extension functions to instantiate LayoutParams](#viewgroups-extension-functions-to-instantiate-layoutparams)
      * [WARNING regarding `lParams` and `defaultLParams` usage](#warning-regarding-lparams-and-defaultlparams-usage)
    * [Other extensions for `ViewGroup`](#other-extensions-for-viewgroup)
* [The interface for user interfaces, named `Ui`](#the-interface-for-user-interfaces-named-ui)
  * [Why this interface](#why-this-interface)
  * [What it is made of](#what-it-is-made-of)
  * [Implementing the interface](#implementing-the-ui-interface)
  * [Using Ui implementations](#using-ui-implementations)
  * [Simple examples](#simple-examples)
  * [Possibilities brought by the `Ui` interface](#possibilities-brought-by-the-ui-interface)
    * [IDE Preview](#ide-preview)
    * [Modular user interface contracts](#modular-user-interface-contracts)
    * [Easier multi form factors support](#easier-multi-form-factors-support)
    * [Multiplatform user interface contracts](#multiplatform-user-interface-contracts)
    * [Testing](#testing)
    * [Redesign](#redesign)
    * [A/B Testing](#ab-testing)
* [Additional modules](#additional-modules)
* [Download](#download)

## The extensions

Splitties is primarily made of extension functions and properties, to create
views with minimal code but maximum flexibility.

Just calling the constructor, then calling needed methods in an `apply { ... }`
block could be enough to use Kotlin instead of xml for your user interfaces,
but **Splitties Views DSL allows something more readable**, more concise,
and with a few features, like themes, styles, and seamless AppCompat
support, **without the boilerplate**.

### Creating and configuring views

#### The most generic way: `view`

The `view` extension functions are a primitive of Splitties Views DSL.
They are generic, so they allow you to instantiate a `View` of any type.

There are 6 functions named `view`, because there's **2 overload types**, and they
are made available for 3 receiver types: `Ui`, `View` and `Context`.

With respect to efficiency, they are all **inline**. That means no unnecessary
allocation that would slightly decrease performance otherwise.

Both overloads allow the following 3 **optional** parameters:
* `@IdRes id: Int`, the id of the View. Example argument: `R.id.input_name`, given
you declared it in xml, [as done in the sample](../sample/src/main/res/values/view_ids.xml)
* `@StyleRes theme: Int`, resource of a theme overlay that will be applied to
the View. Example argument: `R.style.AppTheme_AppBarOverlay`
* `initView: V.() -> Unit`, a lambda that is like `apply` for the created View. 

**The first overload** of `view` takes a required first parameter that is a function
taking a `Context`, and returning a `View`. Since constructors are also
methods in Kotlin, you can directly use a method reference like so:
`view(::View)`.
The same goes for any other `View` subclass (e.g. `view(::FrameLayout)`).
You can also use a lambda instead: `view({ FrameLayout(it) })`. In fact, that's
how you should do it while autocomplete for method references is not optimal,
then use the IDE quick action (<kbd>alt</kbd>/<kbd>⌥ option</kbd> + <kbd>⏎ enter</kbd>)
to convert it to method reference.
You can of course use any custom method reference that is not a reference to
a constructor as long as that method takes a `Context` parameter and returns a
`View` or any subclass of it.

Here's a simple but typical example of this first overload:
```kotlin
val myView: MyCustomView = view(::MyCustomView, R.id.my_view) {
    backgroundColor = Color.BLACK
}
```

**The second overload** of `view` takes no required parameter, but relies on explicit
(reified) type parameter to work properly. Just `v<TextView>()` is enough to
instantiate a `TextView`. However, this version relies on a "view factory" that
can automatically provide subclasses of the requested type as necessary. If
you use the Views DSL AppCompat, you'll automatically receive instances of
`AppCompatButton` with `v<Button>` thanks to the underlying View factory.

Here's a simple example of this second overload:
```kotlin
val submitBtn = v<Button>(R.id.btn_submit) {
    textResource = R.string.submit
}
```

#### Using styles defined in xml

There are some times where you need to use an xml defined style,
such as when using a style defined in AppCompat like `Widget_AppCompat_Button_Colored`.

Splitties makes it really easy to use xml styles defined in Android and AppCompat.

It also gives you the ability to do the same for custom or third-party styles defined
in xml.

##### Using Android styles

Let's say you want to create a horizontal `ProgressBar` instance. First, type the
following with autocomplete/auto-imports:
```kotlin
val progressbar = AndroidStyles.progressBar.horizontal.invoke(ctx)
```

Then, remove `.invoke` to have function call syntax directly on the `horizontal`
property. It should look like this:

```kotlin
val progressbar = AndroidStyles.progressBar.horizontal(ctx)
```

Other styles defined in the Android platform are provided in `AndroidStyles`.
Just let auto-completion guide you.

Note that you have **exactly the same optional parameters as `view`**, including the optional lambda.

##### Using AppCompat styles

Since AppCompat styles are not included by default inside the theme, you need to
load them first. This is simply done with the following code:
```kotlin
private val appCompatStyles = AppCompatStyles(ctx)
```

You can then use styles using the `AppCompatStyles` instance. Here's an example:
```kotlin
val bePoliteBtn = appCompatStyles.button.colored(ctx) {
    textResource = R.string.be_polite
}
```

Don't forget to first write `.invoke` before the parenthesis to have the IDE import
the function (you can immediately remove it afterwards).

##### Using any other xml style

The `colored`, `horizontal` and other properties you can find in the `AndroidStyles`
and the `AppCompatStyles` have the `XmlStyle` type. It is easy to instantiate it and
support any xml style after the style is loaded into the current theme, but before
we see how it's done, let's see what is this type.

The `XmlStyle` **inline** class has:
* A type parameter, for the target `View` type.
* A single `Int` value, a theme attribute (`@AttrRes`, not `@StyleRes`).

As you can see, its constructor doesn't expect a style resource (e.g.
`R.style.Widget_AppCompat_ActionButton`), but a theme attribute resource (e.g.
`R.attr.Widget_AppCompat_ActionButton`). This is because of a limitation in
Android where you can programmatically only use xml styles that are inside a theme.
That doesn't mean that you will have to pollute all the themes you're using
with styles definitions though.

Android allows you to combine multiple themes with the `applyStyle(…)` method
that you can call on any `theme`, which any `Context` has. That way, you can
apply a theme that already includes references the xml styles you need with
only one line of code. This is what the `AppCompatStyles(ctx)` function
mentioned does under the hood.

Here's a short example:
```kotlin
ctx.theme.applyStyle(R.style.ThirdPartyStyles, false)
val clapButton = XmlStyle<Button>(R.attr.Widget_ThirdParty_FancyButton)(ctx) {
    imageResource = R.drawable.ic_clap_white_24dp
}
```

The first line makes sure the `theme` associated to the `Context` named `ctx`
can resolve all the style attributes defined into `R.style.ThirdPartyStyles`,
such as `R.attr.Widget_ThirdParty_FancyButton`.

To make this work, you have to do the following:
1. Declare `ThirdPartyStyles` in xml (usually in a file named `styles.xml`)
2. Declare the `Widget_ThirdParty_FancyButton` attribute (usually in a file named
`attrs.xml`)
3. Declare `Widget_ThirdParty_FancyButton` into `ThirdPartyStyles` and
make sure it references the style target resource
(named `Widget_ThirdParty_FancyButton` too). Using the same name for the target
style and the attribute is a recommendation (for clarity), but not a requirement.

After this is done, you can make a class to group related styles, as done in the
[Views DSL AppCompat split](../views-dsl-appcompat), so you get type
inference, and a nicer syntax.

#### The most beautiful ways: explicitly named aliases to the generic way

Instead of using `v<Button>(…) { … }` to create a `Button` instance, you can use
`button(…) { … }`. The parameters are exactly the same as `view`.

Such methods exist for most `View`s and `ViewGroup`s included in Android, and
there's more in the [additional modules](#additional-modules).

You can see implementations for [Views](src/main/java/splitties/views/dsl/core/Views.kt)
and [ViewGroups](src/main/java/splitties/views/dsl/core/ViewsGroups.kt).

These methods are a bit more natural to read and to write, but they are really
just **inline** aliases, purely syntactic sugar.

You can define your own if you want. Just make sure to write it first for
`Context` and add two overloads for `View` and `Ui` that delegate to the
one for `Context`. Also, remember to make them inline to avoid lambda allocation.

#### View extensions

For even more expressive UI code, Splitties Views DSL has a transitive dependency on
the [Views split](../views) that provides a useful set of Kotlin-friendly extension
functions and properties dedicated to `View`s and some of their subclasses.

#### Inflating existing xml layouts

Splitties Views DSL works well with xml layouts too!

The `inflate` extension functions is a variant to the `view` function [mentioned
earlier in this guide](#the-most-generic-way-view) which has an additional first
parameter: the layout resource id you want to inflate.

Also, if the xml layout defines an id for the root view, it will be kept, unless
you specified an explicit id (including `View.NO_ID`).

Just like `view`, `inflate` is defined for `Context`, `View` and `Ui`.

Here's a short example:
```kotlin
val mySecretFancyView = inflate(R.layout.my_fancy_layout) {
    isVisible = false
}
```

### Laying out the views

#### `ViewGroup.add(…)`, an alias to `ViewGroup.addView(…)`

To add a `View` to a `ViewGroup` in code, you can use `View.addView(…)`. However,
this can become quite redundant to have `View` repeated over and over when it's
already obvious that you are in a UI centric class that passes a parameter that is
clearly a `View`.

That's why this split has an **inline** alias to it named just `add(…)` for `ViewGroup`.
It has the extra benefit of returning the passed `View`, which can be handy in some
situations.

The `ViewGroup.add(…)` function requires an instance of `ViewGroup.LayoutParams`,
see how Splitties helps instantiating it with minimal, yet explicit code.

#### ViewGroups extension functions to instantiate LayoutParams

Splitties provides several methods named `lParams(…) { … }` for the 2 Android's
built-in `ViewGroup`s: `LinearLayout` and `FrameLayout`. You can find support
for additional `ViewGroup`s in the [additional modules](#additional-modules).

These methods make it easy to instantiate LayoutParams with typesafe and readable code (unlike xml).

Here's the contract that every `lParams` or alike function must respect:
1. The receiver is the type of the target `ViewGroup` subclass.
2. The function returns the `LayoutParams` for the target `ViewGroup`.
3. The first parameter is `width` and defaults to `wrapContent`, unless otherwise noted.
4. The second parameter is `height` and defaults to the same value as `width`, unless otherwise
noted.
5. The `width` or `height` parameters may be missing in case they shall always have the same value
for this target `ViewGroup` or for this function.
5. There may be additional parameters, with default values if possible.
6. The last parameter is a lambda with `LayoutParams` as a receiver and is executed exactly once,
last (i.e. after any logic that the `lParams` implementation may have).
7. If the `lParams` function targets a `ViewGroup` that has a superclass that also has its own
`LayoutParams`, and its own `lParams` function, it should be named `defaultLParams` instead to
prevent any overload resolution ambiguity. A great example is `AppBarLayout` that is a child class
of `LinearLayout` and has such extension functions for `LayoutParams`.
8. In case the function is specialized for non default use case (e.g. adding an `AppBarLayout` into
a `CoordinatorLayout`), it can have a custom name, but should always end with `LParams` (e.g.
`appBarLParams`).

##### **WARNING** regarding `lParams` and `defaultLParams` usage:

`lParams` and similar functions are resolved based on the type of their receiver.
However, unless you prepend `lParams` or `defaultLParams` call with `this.`, the received is picked
implicitly, and can be indirect, possibly causing the wrong `lParams` method to be used.

Here's **a short, example**:

You're in a `FrameLayout` (because you're writing a subclass of it, or because of a lambda
receiver, like inside `frameLayout { … }`).
You call `constraintLayout { … }` and start adding views inside it, but when you call `lParams`,
you may use the implementation for `FrameLayout`, and wonder why the
`ConstraintLayout.LayoutParams` properties and extensions are not available.

To highlight such errors, you can prepend `this.` to your suspicious `lParams` calls, and if they
are in red, then you used the wrong one for the `ViewGroup` you're in. The IDE should quickly
fix it, adding the proper import at this point.

After this is done, you can then safely remove the `this.` prefix.

To avoid this issue, you can be alert when you're typing/auto-completing `lParams` and
`defaultLParams` and make sure that you're selecting the extension for the type of the `ViewGroup`
you're in (direct parent of the child View you are adding).

#### Other extensions for `ViewGroup`
 
* `wrapContent` and `matchParent` inline extensions properties on
`ViewGroup` are convenience aliases to `ViewGroup.LayoutParams.WRAP_CONTENT`
and `ViewGroup.LayoutParams.MATCH_PARENT`.
* `horizontalMargin`, `verticalMargin` and `margin` for convenient margins
definition in layout parameters (`ViewGroup.MarginLayoutParams` which is the
base of nearly all LayoutParams).
* `startMargin` and `endMargin` which are compatible below API 17 (using LTR)
and fix the inconsistent name ordering (`leftMargin`, but `marginStart`?).

## The interface for user interfaces, named `Ui`

This section doesn't just writes so many words about how **the `Ui` interface
has only 2 properties**. It explains why **it is useful**, how to **use it the right
way**, and the **possibilities** it offers.

FYI, the declaration of this interface looks like this:

```kotlin
interface Ui {
    val ctx: Context
    val root: View
}
```

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

With Splitties Views DSL, there's an [optional interface named `Ui`](
src/main/java/splitties/views/dsl/core/Ui.kt), whose implementations are meant
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

To use a `Ui` implementation from an `Activity` subclass, just call
`setContentView(ui)`.
To use it from any other place, just get the `root` property. In a `Fragment`
subclass, that will mean returning it from `onCreateView(…)`.

You can also use any function or property you've declared in your sub-interface
or implementation.

Here are two examples:
* Using a public property of type `Button` to set it an `OnClickListener` in
the place where the `Ui` is used (like an `Activity` or a `Fragment` that connects
your UI to a `ViewModel` and any other components).
* Call a method called `animateGoalReached()`.

### Simple examples

See concrete examples in [`MainUi`](
../sample/src/main/java/com/louiscad/splittiessample/main/MainUi.kt) and
[`DemoUi`](
../sample/src/main/java/com/louiscad/splittiessample/demo/DemoUi.kt) with
their respective Activities [`MainActivity`](
../sample/src/main/java/com/louiscad/splittiessample/main/MainActivity.kt)
and [`DemoActivity`](
../sample/src/main/java/com/louiscad/splittiessample/demo/DemoActivity.kt).

### Possibilities brought by the `Ui` interface

#### IDE Preview

You can preview `Ui` implementations in the IDE. [See the
Views DSL IDE preview split](../views-dsl-ide-preview/README.md).

#### Modular user interface contracts

While having a dedicated class for user interface, that is agnostic
from where it will be used (Activity, Fragment, IDE Preview…), is a
great first step to a modular user interface code, you can go further.

Instead of exposing your `Ui` implementation directly to the Activity or
Fragment, you can decide to write several interfaces that define a contract
that your Activity, Fragment, ViewModel (beware of leaks), or whatever will
need, and implement all of these with one or more classes.

For example, let's say you are developing an email app. You write two interfaces:
`InboxUi` and `ComposeUi` that both extend the `Ui` interface. You add to the
interfaces all the functions (including any `suspend fun`), properties and other
symbols you may need to expose to the Activity, Fragment, ViewModel or whatever.
Then you implement these two interfaces, with either one class or two, depending
on whether you want to display them separately or not.

#### Easier multi form factors support

Modular UI contracts open the door to a great benefit: an easier way to
support multiple form factors (smartphones, smartwatches, tablets, laptops, cars…).

In the previous example, we highlighted the fact that you could have multiple
interfaces that expose the needed symbols, and then decide to implement these
interfaces in one, or multiple classes.

This can help you support different form factors with zero, or only a few
changes in non-UI code as it is no longer relies on a specific implementation.

It is planned to add such examples in the samples of this repository.
If you want to have them faster, please open an issue so the examples can be
discussed. Also, maybe you, or someone you know, can contribute.

#### Multiplatform user interface contracts

Here's an example of how you may write multiplatform user interface contracts:

In Kotlin common code, you would write an interface that is platform agnostic
but declares the needed symbols that all platforms can share:

Continuing our email app example, you would write these two interfaces: 
```kotlin
interface InboxUiContract {
    // Whatever you need
}

interface ComposeUiContract {
    // Whatever you need
}
```

Then write to sub-interfaces for each platform you want to support, Android and
iOS in this example:
```kotlin
interface AndroidInboxUi : InboxUiContract, Ui
```

```kotlin
interface IOSInboxUi : InboxUiContract {
    val root: UIView
}
```

And you may finally implement them for each platform, still supporting multiple
form-factors and platform variants if needed.

_The two common interfaces (`InboxUiContract` and `ComposeUiContract`) could
be replaced by abstract classes in case you need to have backing fields, final
declarations or final implementations, as long as they don't reference
Splitties `Ui` interface and no platform specific code._

#### Testing

Having your user interface as an `interface` can make it easy to mock it, and
simulate user interactions for testing purposes.

#### Redesign

If you expect an `interface` for the user interface, then it becomes easy to
replace an implementation by another one in case you're redesigning your app.
You can also split your UI contracts (the `interface`s) into smaller subsets
before starting a redesign if needed, this can be helpful if you want to move
some UI controls to another area of the application, or just organize things
differently.

#### A/B Testing

When you have multiple UI `interface`s implementations, you can then swap them
at runtime for A/B testing, allowing you to test which UI works the best for
what you determined.

## Additional modules

**There are additional splits for extended support. Views DSL…**
* [AppCompat](../views-dsl-appcompat) provides proper styling to `Button`,
`TextView`, `EdiText` and other widgets.
views like `coloredFlatButton`.
* [ConstraintLayout](../views-dsl-constraintlayout) provides support for
`ConstraintLayout.LayoutParameters`.
* [Design](../views-dsl-design) provides extensions for design support library
`ViewGroup`s and bottom sheets.
* [IDE preview](../views-dsl-ide-preview) provides the ability to preview your
user interfaces right from the IDE.
* [RecyclerView](../views-dsl-recyclerview) provides extensions to have
scrollbars and proper `itemView` layout parameters.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-views-dsl:$splitties_version"
```
