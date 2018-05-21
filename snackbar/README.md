# Snackbar

*Grab a snack without ceremony with `snack(…)` and `longSnack(…)`*

This [split](../README.md#what-is-a-split "What is a split in Splitties?")
provides extensions to show a 
[`Snackbar`](https://material.io/components/android/catalog/snackbar/),
boilerplate free. It also has a small extension functions based DSL to
add an action and execute action on dismiss.

## Usage

On a `CoordinatorLayout`, call `snack(…)`, `longSnack(…)` (if you're really
hungry), or `snackForever(…)` for an indefinite duration, with a string
resource id, or a `CharSequence`.

You can add optional braces to access the `Snackbar` instance before it is
shown, so you can add an action (using `action(…) { … }`) and add callback
for dismissal (using `onDismiss(…)`).

Note that `snackbar(…)`, `longSnack(…)` and `snackForever(…)` return the
created `Snackbar` instance. That means you can as well add `onDismiss(…)`
on the result of the call instead of inside the optional inline lambda.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-snackbar:$splitties_version"
```
