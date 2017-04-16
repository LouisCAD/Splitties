# Concurrency
This library provides a `lazy` delegate which supports three additional parameters:

* `UI_THREAD` The value can be accessed only from the UI thread.
* `UNIQUE_ACCESS_THREAD` The thread accessing the delegated value needs to be the first one that did so.
* `UNIQUE_THREAD` The delegated value must be accessed from the thread on which the delegate was created.

**Caution!** When using it, make sure you have `import splitties.concurrency.lazy` in your imports.

## Custom behavior
The library have 2 resource parameters to configure it's behavior. To change them, simply override their value in your app's resources.

The `splitties_concurrency_mode` integer resource which defaults to `@integer/mode_check` defines the behavior when a violation occurs:

* 0:`@integer/mode_check` (default) Throws an `IllegalStateException`.
* 1:`@integer/mode_report` Logs an `IllegalStateException` using `Timber.wtf(e)`.
* 2:`@integer/mode_unchecked` Ignores the violation.

The `use_stdlib_if_unchecked` bool resource which defaults to `true` calls `kotlin.lazy(NONE, initializer)` when the mode is unchecked if `true`. You may want to change it to `false` if you change the mode at runtime calling `Concurrency.mode = Concurrency.MODE_*`. This could be useful to log additional error to troubleshoot your alpha/beta/in-production app for example.

## Report mode
This library uses [Timber](https://github.com/JakeWharton/timber), a simple and flexible log utility for Android. You can easily forward the logs to your favorite error reporting system such as [Firebase Crash Reporting](https://firebase.google.com/docs/crash/) using a custom Timber Tree. `Timber.wtf` is called in report mode when a violation occurs.