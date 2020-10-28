# Fragments

*Start activities from fragments and do transactions with minimal boilerplate.*

## Content

### Starting Activities

The `start` extension function for `Context` takes advantage of reified type
parameters to allow you to write such code: `start<AboutActivity>()`.

There's an optional lambda where the `Intent` is the receiver so you can
edit it (e.g. adding flags) before the activity is started with it.

The `startActivity` extension function for `Context` is designed for implicit
intents. It expects the `Intent` action as first parameter, and takes a
lambda to edit the intent further, like `start`.

## Fragment transactions

The `fragmentTransaction` extension function for `FragmentActivity` allows
to make a fragment transaction without having to write `beginTransaction` and
the commit call repeatedly.

It has two optional parameters:
* `now`, that defaults to `true` so you can get the `Fragment` immediately
after by default.
* `allowStateLoss` that defaults to `false`.

And the lambda is the required body of your transaction where you deal with
your Fragments (e.g. adding, replacing…).

The `addToBackStack()` extension function for `FragmentTransaction` calls
`addToBackStack(null)`. It effectively makes the name parameter optional.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-fragments:$splitties_version"
```
