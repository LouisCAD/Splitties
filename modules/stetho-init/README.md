# Stetho Init

*Have [Stetho](https://github.com/facebook/stetho) for your debug builds,
without writing any code!*

This library uses an [InitProvider](
../initprovider/src/androidMain/kotlin/splitties/initprovider/InitProvider.kt
) (like [appCtx](
../appctx/src/androidMain/kotlin/splitties/init/AppCtxInitProvider.kt
), and similarly to Firebase) to initialize Stetho
automatically. You just have to include the dependency on your debug build
and voilà!

### Usage
Add the dependency to your debug build like in the example below:
```groovy
debugImplementation("com.louiscad.splitties:splitties-stetho-init:$splitties_version")
```

Assuming your Android device is visible from adb on your computer, you can
now navigate to [chrome://inspect](chrome://inspect) to see your updated
debug app in Chrome Dev Tools, no further configuration required.

## Download

```groovy
debugImplementation("com.louiscad.splitties:splitties-stetho-init:$splitties_version")
```
