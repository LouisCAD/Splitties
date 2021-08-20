# System Services

*No more `context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.*

Supported platforms: **Android**.

This library contains all the Android System Services as of API 29.
It allows accessing them with a simple property.

Most System Services use the application Context and are available
anywhere in the code of your app, but some that can vary depending on the
`Context` (like `LayoutInflater` or `WindowManager`) are extensions on
`Context`, `View` or `AccessibilityService`.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.systemservices`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-systemservices`.

## Example

Before:

```kotlin
fun performSomeVibration(vibe: VibrationEffect) {
    val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(vibe)
}
```

After:

```kotlin
fun performSomeVibration(vibe: VibrationEffect) {
    vibrator.vibrate(vibe)
}
```
