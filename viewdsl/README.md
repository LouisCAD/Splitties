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

### Cons of Splitties View DSL

* Using xml defined styles (the ones you use with the `style` attribute)
can't be done directly in code and requires a small workaround (thankfully
an easy one: you need to create an xml file for the View you want to style
and to define a method to inflate it).
* Preview requires a build (but you're less likely to need preview thanks
to additional type safety and more expressive UI code).
