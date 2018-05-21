# View DSL

*Create UIs with readable Kotlin code.*

## Why a View DSL over xml layouts?

### Pros of xml layouts

* You can almost instantly preview a layout file (despite the huge RAM and CPU
usage)
* You can directly use xml styles with the `style` attribute
* You can declare a new id resource on the fly with `@+id/some_new_id`

### Cons of xml layouts

* You repeat `android` and `app` over and over, cluttering the code, resulting
in **hardly readable code**.
* Layout parameters and view attributes are mixed together and are not so well
ordered in some cases such as when using `ConstraintLayout`.
* Some things that affect your Views can only be done from code, leading to 2
problems: You can't preview them, and your **UI code is split over at least
2 files**.
* xml layout inflation involves **reflection**, which can slow down UI
creating, on large layouts and/or lower end devices.
* Some attributes that could make layout files easier to read like
**`horitontalMargin` and `verticalMargin` are only available on API 26+
in xml**.
* **If you support API 16 and lower, LTR support is subpar**.
When specifying start/end margins, you still need to specify left and right
values for API 16 or older (while start could default to left when RTL is not
supported…). You also need to keep them in sync manually when you change the
values.
* You can't take advantage of Kotlin extensions and other Kotlin features.
* If you want to make your UI dynamic, you need to use code, and xml is not
really code, so you need to split your UI logic, being more error-prone in
the long run.
* Reusing parts of UI is hard and verbose, because you can only include other
files, but there's no things like functions for example.

### Pros of Splitties View DSL

* It is **concise** (most layouts converted from xml end up shorter, sometimes
even when counting the import lines).
* Is is **more expressive** and you can make it even better by defining
extension function or properties on the Views, LayoutParams, etc, so you can
hide a set of operations behind a simple function call. There are no
restrictions on the language features you can use in your UI code, you're not
just limited to xml available attributes and resources.
* You can **reuse UI code** in much more and easier ways than you can do
with xml layouts + code.
* Your **`Ui` can implement an interface** so you can easily swap
implementations. This can be handy for A/B testing, allowing more user
preferences to tweak or completely change the UI, and more.
* Layout direction defaults to LTR before API 17 and you can keep using
start/end without added boilerplate.
* **You can preview layouts in Android Studio** (requires build), with any
included logic being taken into account.
* Layout parameters are not mixed with View config.
* **No reflection** involved.
* No need for `findViewById(…)` and the implied lookup costs.

### Cons of Splitties View DSL

* Using xml defined styles (the ones you use with the `style` attribute)
can't be done directly in code and requires a small workaround (thankfully
an easy one: you need to create an xml file for the View you want to style
and to define a method to inflate it).
* Preview requires a build (but you're less likely to need preview thanks
to additional type safety and more expressive UI code).
* You can't create a View id on the fly (but you can declare an id resource
easily or reuse one declared on-the-fly from an existing xml layout).

## Content

Splitties View DSL is made of a few things you can build upon:

* [An optional interface named `Ui`](
src/main/java/splitties/viewdsl/core/Ui.kt) whose implementations are meant
to contain your UI code (instead of having it spread over an xml file and
an `Activity` or a `Fragment`).
* The `v` extension function available on `Ui`, `View` and `Context` that
lets you concisely create a View using an (inlined) method reference that
takes a `Context` parameter (like all proper View constructors) with an
optional id, an optional theme id and an optional lambda to configure the
View.
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
