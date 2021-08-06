# Stetho Init

*Have [Stetho](https://github.com/facebook/stetho) for your debug builds,
without writing any code!*

Supported platforms: **Android**.

This library automatically initializes Stetho (it's relying on AndroidX App Startup).

You just have to include the dependency on your debug build and voilà!

### Setup

Add the dependency to your **debug** build like in the example below.

Provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project:

```kts
debugImplementation(Splitties.stethoInit)
```

Or with full maven coordinates:

```groovy
debugImplementation("com.louiscad.splitties:splitties-stetho-init:$splitties_version")
```

### Usage

Assuming your Android device is visible from adb on your computer, you can
now navigate to [chrome://inspect](chrome://inspect) to see your updated
debug app in Chrome Dev Tools, no further configuration required.
