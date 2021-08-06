# App Context

*Always have your application `Context` at hand with `appCtx`.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.appctx`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-appctx`.

This [split](../../README.md#what-is-a-split "What is a split in Splitties?")
provides two read-only properties:

- `appCtx` that returns your Application Context
- `directBootCtx` for your [direct boot](
https://developer.android.com/training/articles/direct-boot.html
) aware components where storage is involved, if any.

It also brings these 2 extensions functions on `Context` for advanced usages:
- `injectAsAppCtx()`
- `canLeakMemory()`

You can use `appCtx` and `directBootCtx` anywhere in your app (without
risking leaking a short-lived context such as an Activity or a Service).
This makes writing code that needs a `Context` for non Activity specific
purposes more convenient.

## Important: use the right context!
You may not want to use the Application Context in some cases.

### Context for storage
If you need a `Context` to access storage from a library
(for SharedPreferences, a database or other files), you should allow
passing a specific `Context` that could default as `appCtx`, so it is
possible for target apps to use a special `Context` like `directBootCtx`.

### Configuration dependent or themed Context
Devices on which your app/library runs may (will) change configuration
during the app's process lifecycle, such as screen density, language or
orientation.

Please, do not use `appCtx` or `directBootCtx` if you rely on a "scoped"
`Context` to access themed resources from an Activity, or
configuration dependent values/resources.

Note that in some cases, configuration dependent context usage may be ok if
your component handles `onConfigurationChanged()` properly. More generally,
if you wonder if using Application Context is ok, test your app against
configuration changes that may affect it and check it reacts correctly.

## How it works
This library takes advantage of Content Providers to automatically
initialize `appCtx` for you before even your [Application](
https://developer.android.com/reference/android/app/Application.html)'s
`onCreate()` is called! This library also takes advantage of
[manifest placeholders](
https://developer.android.com/studio/build/manifest-build-variables.html)
(with the default `${applicationId}`) and gradle manifest merging to avoid
two apps using this library clashing with same authority Content Providers.

This is the same trick used by Firebase to auto-initialize the library.
You can read more on this
[here](
https://firebase.googleblog.com/2016/12/how-does-firebase-initialize-on-android.html
).

## Advanced use cases

### Multi-process apps and libraries

While most apps run in a single-process, on the default one, some need to
run some components in different processes. If your app needs to access
`appCtx` or `directBootCtx` directly, or indirectly, in a component that
has its `android:process` tag in `AndroidManifest.xml` set to
`:the_name_of_your_private_process` or
`the_fully_qualified_name_of_your_shared_process`, you need to do the following:

Call `injectAsAppCtx()` in the `init` block (or constructor) of your custom `Application` subclass.

If you're making a library, an alternative solution that will not require further configuration on
the app side is to declare your own `ContentProvider` for that process and call
`injectAsAppCtx()` from its `onCreate` function.
