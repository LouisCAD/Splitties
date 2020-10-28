# Intents

*Transform `companion object`s into powerful typesafe intent specs, and
create `PendingIntent`s the clean and easy way.*

## Table of contents

* [Using `companion object`s as typesafe and key-safe intent specs](#using-companion-objects-as-typesafe-and-key-safe-intent-specs)
  * [The problem about intents in Android](#the-problem-about-intents-in-android)
  * [The solution](#the-solution)
  * [IntentSpec interfaces](#intentspec-interfaces)
  * [IntentSpec implementations providers](#intentspec-implementations-providers)
  * [Using an IntentSpec](#using-an-intentspec)
  * [IntentSpec example](#intentspec-example)
* [Creating `PendingIntent`s the clean and easy way](#creating-pendingintents-the-clean-and-easy-way)
* [Download](#download)

## Using `companion object`s as typesafe and key-safe intent specs

### The problem about intents in Android

An Android component that can receive an Intent (like an `Activity` or a
`BroacastReceiver`) can expect an action, or extras. In case of an action,
the string must match exactly in order to work properly. In case of an extra,
the type must also match. This makes it not typesafe at all. You need to write
some documentation to explain which constants to use, with which types,
and you need to be sure to read the doc afterwards, to ensure you do it right.

*There should be a better way.*

### The solution

This split provides a few interfaces that your `companion object`s can
implement using delegation so they become an intent specification, that you
can use to build and `Intent`, start an `Activity`, start a `Service` or send
a broadcast in a type safe way.

### IntentSpec interfaces

The `IntentSpec` interface has two properties:
* `klass` which is the class of the component
* `extrasSpec` which is a `BundleSpec` (defined in the [Bundle](../bundle)
split).

The `ActivityIntentSpec`, `BroadcastReceiverIntentSpec` and
`ServiceIntentSpec` interfaces all extend the `IntentSpec` interface, but
add a bound to the type parameter of the class. You should use them when
defining the intent spec of an `Activity`, `BroadcastReceiver` or `Service`.

### IntentSpec implementations providers

A few methods provide implementation of the interfaces mentioned above:
* `activitySpec`
* `activityWithoutExtrasSpec` where `ExtrasSpec` is `Nothing`
* `receiverSpec`
* `receiverWithoutExtrasSpec` where `ExtrasSpec` is `Nothing`
* `serviceSpec`
* `serviceWithoutExtrasSpec` where `ExtrasSpec` is `Nothing`

They are designed to be used with `by`. Examples:
* `companion object : ActivityIntentSpec<ProductDetailsActivity, ExtrasSpec> by activitySpec(ExtrasSpec)`
* `companion object : BroadcastReceiverIntentSpec<NotifDismissReceiver, ExtrasSpec> by receiverSpec(ExtrasSpec)`

### Using an IntentSpec

The `intent` method is an extension for `IntentSpec`. It takes a lambda where
the receiver is the `Intent`. The first parameter is the `companion object`
itself so you can access any constant or method defined in it easily. The
second parameter of the lambda is optional, it is the extrasSpec.

The `start` method is an extension for `Context` and for `Fragment` that
takes the target `ActivityIntentSpec` as its first parameter. It then calls the
`intent` extension function mentioned above, and the optional expected lambda
has the same parameters as for `intent`. Finally, it calls `startActivity`
with the created `Intent`.

The `sendBroadcast` method is an extension for `Context` that takes the
target `BroadcastReceiverIntentSpec` as its first parameter. It then calls
the `intent` extension function mentioned above, and the optional expected
lambda has the same parameters as for `intent`. Finally, it calls
`sendBroadcast` with the created `Intent`.

### IntentSpec example

Let's take the example shown in the [Bundle](../bundle) README, adding an
`IntentSpec` to it. Notice the new `companion object` and how we start the
`DemoActivity` with the proper extras from the `StartDemoActivity` now.

```kotlin
class DemoActivity : AppCompatActivity() {

    companion object : ActivityIntentSpec<DemoActivity, ExtrasSpec> by activitySpec(ExtrasSpec) {
        const val someText = "Splitties is great!"
    }

    object ExtrasSpec : BundleSpec() {
        var showGreetingToast: Boolean by bundle() // Required extra
        var optionalExtra: String? by bundleOrNull() // Optional extra
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        withExtras(ExtrasSpec) { // Populates ExtrasSpec with actual extras
            if (showGreetingToast) toast(android.R.string.ok)
            optionalExtras?.let { longToast(it) }
        }
        restOfYourCode()
    }
}

class StartDemoActivity : AppCompatActivity() {

    private fun someFunction(isUserPolite: Boolean = false) {
        start(DemoActivity) { intentSpec, extrasSpec -> // Magic happens here!
            extrasSpec.showGreetingToast = isUserPolite
            extrasSpec.optionalExtra = intentSpec.someText
        }
    }
}
```

## Creating `PendingIntent`s the clean and easy way

It's fair to say that the `PendingIntent` Android API is not designed for Kotlin,
and for a reason, it has been there since API level 1, the first Android version.

As a result, code using this API is not always the most readable part of a
Kotlin codebase.

This split provides several extension functions for `Intent` that return a
`PendingIntent`:
* `toPendingActivity()`
* `toPendingService()`
* `toPendingForegroundService()`, which also works before API 26
* `toPendingBroadcast()`

and an extension for `Array<Intent>`: `toPendingActivities()`.

All these functions have two optional parameters that default to zero:
`reqCode` and `flags`.

The `toPendingActivity()` and `toPendingActivities()` functions also have
an `options: Bundle?` parameter that defaults to `null` (and is ignored
below API 16).

## Download

```groovy
implementation "com.louiscad.splitties:splitties-intents:$splitties_version"
```
