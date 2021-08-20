# Checked Lazy

*`mainThreadLazy` that checks property access on
 main thread, and `checkedLazy` to make your own variant.*

Supported platforms: **Android**.

_NOTE: Coroutines can replace the need for this split altogether in most cases._

## Setup

This dependency is not included in any of the [fun-packs](../../README.md#download),
because we don't believe all apps need its content nowadays.

Add it with [refreshVersions](https://github.com/jmfayard/refreshVersions):
`Splitties.checkedlazy`.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-checkedlazy`.

## Content

This library provides a `checkedLazy()` method that returns a `Lazy` delegate,
as well as `mainThreadLazy()`.

`checkedLazy()` takes as first parameter a function where you can implement an access check.

The second parameter is the lazy initializer, as in Kotlin stdlib `lazy`.

`mainThreadLazy { … }` is a shorthand for `checkedLazy(::checkMainThread) { … }`.
It's there because main thread checking is a common use case on Android due to
its synchronized nature and its omnipresence.

## Example

```kotlin
val noMainThreadChecker = noAccessOn(mainThread)

class YourClass {
    val greeting: String by mainThreadLazy { "Hello Splitties!" }
    val expensiveObject by checkedLazy(noMainThreadChecker) { doHeavyInstantiation() }
}
```
