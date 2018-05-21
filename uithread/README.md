# UI Thread

*Properties related to Android UI thread, and `checkUiThread()` precondition
checker.*

## Content

This split provides several properties related to Android UI Thread and a
precondition checked.

Here's the list:

* `mainLooper`: Shortcut to `Looper.getMainLooper()!!`
* `mainThread`:  Shortcut to `Looper.getMainLooper()!!.thread`
* `uiThread`: Alias of `mainThread`
* `isUiThread`: Boolean property that is true if the current Thread is the UI
Thread.
* `checkUiThread()`: Throws an `IllegalStateException` if not run on the UI
Thread.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-uithread:$splitties_version"
```
