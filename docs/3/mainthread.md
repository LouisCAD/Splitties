# Main Thread

*Properties and precondition checkers related to Android, iOS and macOS main thread.*

Supported platforms: **macOS** (x64), **iOS** (arm32, arm64 & x64), **Android**, **JS**.

## Content

This split provides several properties related to main Thread and 2
precondition checkers.

Here's the list:

* `mainLooper`: Shortcut to `Looper.getMainLooper()!!` with caching (Android only).
* `mainThread`:  Shortcut to `Looper.getMainLooper()!!.thread` with caching (Android only).
* `isMainThread`: Boolean property that is true if the current Thread is the
main Thread (always `true` in JS).
* `checkMainThread()`: Throws an `IllegalStateException` if not run on the main
Thread.
* `checkNotMainThread()`: Throws an `IllegalStateException` if run on the main
Thread (always throws in JS).

## Download

```groovy
implementation("com.louiscad.splitties:splitties-mainthread:{{version.splitties3}}")
```
