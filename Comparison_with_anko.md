# How Splitties differs from Anko

There's Splitties Views DSL, and there's Anko layouts.
There's many splits, and there's Anko commons.

Let's see what are the differences and why Splitties was built while Anko already existed under full awareness of the founder of the Splitties project.

**TL;DR first**: Splitties is lighter, more explicit, more flexible and… more split.

## Anko layouts: their issues that Splitties Views DSL avoided

### A subclass for each `View` you want to use

To use a `View` in an Anko layout, in addition to a function for DSL usage, a subclass of it, specially tailored to work with Anko layouts is needed.

Splitties Views DSL does not require such a thing at all. You can just pass the constructor
reference to the `view` function (e.g. `view(::MyShinyCustomView)`), and if you want, for your convenience, you can make an "alias function" (e.g. `myShinyCustomView()`), which can (should) be `inline`. Consequently, Splitties is lighter, and more flexible.

### Views added _implicitly_

In DSL fashion, Anko layouts implicitly add any view you create using the DSL to the receiver `ViewGroup` if any, or set it as a content view if in an `Activity`.

In the real world, you rarely add a View without specifying the `LayoutParams`, so this implicit behavior only makes it easier to forget specifying the layout params, and realizing it later, possibly losing time in your development process.

Splitties never adds a `View` implicitly. You always have to supply `LayoutParams` to the `add` function. That also makes creating a `View` without adding it at instantiation place straightforward.

## Anko commons: misc extensions that could be split

Anko commons has several extension functions or helpers for Android to avoid some boilerplate. However, you need to have the full dependency even if you use only a few parts in it, which can make your app bigger (before proguard at least).

Splitties, from day one was split (that's where its name comes from). You can use only one module with, let's say, Splitties Intents, but not Splitties Fragment Args. This avoids to integrate what you know you don't need, and helps making your app smaller.
