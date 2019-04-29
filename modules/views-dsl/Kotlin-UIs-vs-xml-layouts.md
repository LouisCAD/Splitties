# Why a Kotlin Views DSL over xml layouts?

There are reasons Splitties Views DSL, an alternative to xml layouts has been
built for Android.

This document is a simple pros and cons of each approach so you can judge by
yourself.

## Facts

* If we don't count imports and copyright headers, the xml version of [MainUi](
../../samples/android-app/src/androidMain/kotlin/com/example/splitties/main/MainUi.kt
) (from the sample) was more than 100% lengthier (50 lines with Kotlin,
102 lines in xml, across the [activity_main.xml](
https://github.com/LouisCAD/Splitties/blob/v1.3.0/sample/src/main/res/layout/activity_main.xml
) and [content_main.xml](
https://github.com/LouisCAD/Splitties/blob/v1.3.0/sample/src/main/res/layout/content_main.xml
) files.).

## Pros and cons

### Pros of xml layouts

* You can _almost_ instantly preview a layout file from the IDE (despite the increased RAM and CPU
usage).
* You can declare a new compile time constant id resource on the fly with
`@+id/some_new_id`
* You **kind of** have a WYSIWYG (what you see is what you get) experience in
the IDE. Kind of because it includes only xml layouts and compiled custom views.
Logic in code can't be previewed in the IDE with xml layouts, unless it is in a custom view.

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

### Pros of Splitties Views DSL

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
* **You can preview layouts in Android Studio**, with any included logic being taken into account.
* Layout parameters are not mixed with View config.
* **No reflection** involved.
* No need for `findViewById(…)` and the implied lookup costs.

### Cons of Splitties Views DSL

* Preview requires a running the `compileDebugKotlin` gradle task (no need to perform a full build,
also, you're less likely to need preview thanks to additional type safety and more readable UI code).
* You can't create a compile time constant View id on the fly (but you can
have ids generated automatically at runtime, declare an id resource
easily or reuse one declared on-the-fly from an existing xml layout).
