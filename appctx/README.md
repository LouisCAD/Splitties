# App Context

*Always have your application `Context` at hand with `appCtx`.*

This [split](../README.md#what-is-a-split "What is a split in Splitties?")
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
While most apps run on single-process, on the default one, some need to
run some components in different processes. If your app needs to access
`appCtx` or `directBootCtx` directly, or indirectly in a component that
has it's `android:process` tag in `AndroidManifest.xml` set to
`:the_name_of_your_private_process` or
`the_fully_qualified_name_of_your_shared_process`, you have 3 solutions:
1. Do nothing and let `appCtx` init itself with reflection on first access
2. Call `injectAsAppCtx()` in the `onCreate()` method of your custom
`Application` subclass.
3. Subclass `AppCtxInitProvider` and declare it correctly in your
`AndroidManifest.xml` for each non default process. This option may be the
best one if you're making a library that has its own process as no further
configuration will be required on the app side. See instructions below:

#### How to declare an AppCtxInitProvider correctly (for multi-process usage)

1. Subclass `AppCtxInitProvider`
2. Register your subclass as a `provider` in your AndroidManifest.xml file.
3. Specify it's `android:process` attribute to the name of your app's non
default process. (beware of typos)
4. Set `android:exported` to false.
5. Set `android:authorities` to something other than
`${applicationId}.appctxinitprovider`. The suggested naming convention is
`${applicationId}.appctxinitprovider.the_name_of_your_process` so the
content provider authority doesn't clash with another one from your app,
or from a third-party app.
6. If your component is Direct Boot aware, add
`android:directBootAware="true"`.
7. If you use `appCtx` or `directBootCtx` in another Content Provider,
make sure to specify `android:initOrder` to a higher value than
the one of the other Content Provider which use the ctx properties.

The result should look like this:

```xml
<provider
    android:name=".SecondProcessInitProvider"
    android:authorities="${applicationId}.appctxinitprovider.second_process"
    android:exported="false"
    android:initOrder="900"
    android:process=":second_process"/>
```

```kotlin
import splitties.init.AppCtxInitProvider

class SecondProcessInitProvider : AppCtxInitProvider()
```

## Download

```groovy
implementation "com.louiscad.splitties:splitties-appctx:$splitties_version"
```
