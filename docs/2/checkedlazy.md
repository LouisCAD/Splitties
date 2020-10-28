# Checked Lazy
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

## Download

```groovy
implementation "com.louiscad.splitties:splitties-checkedlazy:$splitties_version"
```
