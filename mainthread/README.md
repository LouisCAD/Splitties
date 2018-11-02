# Main Thread

*Properties and precondition checkers related to Android main thread.*

## Content

This split provides several properties related to Android main Thread and 2
precondition checkers.

Here's the list:

* `mainLooper`: Shortcut to `Looper.getMainLooper()!!` with caching.
* `mainThread`:  Shortcut to `Looper.getMainLooper()!!.thread` with caching.
* `isMainThread`: Boolean property that is true if the current Thread is the
main Thread.
* `checkMainThread()`: Throws an `IllegalStateException` if not run on the main
Thread.
* `checkNotMainThread()`: Throws an `IllegalStateException` if run on the main
Thread.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-mainthread:$splitties_version"
```
