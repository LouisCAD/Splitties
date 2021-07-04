# Init Provider

*Base class for `ContentProvider`s used for automatic initialization
purposes.*

Supported platforms: **Android**.

## This split is deprecated

Each `ContentProvider` adds a small app startup penalty,
which can add-up to significant delays for cold start.

Please, use [AndroidX App Startup](https://developer.android.com/topic/libraries/app-startup)
instead to avoid that problem.

This split **will be removed** before Splitties 3.0.0 release.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-initprovider:$splitties_version")
```
