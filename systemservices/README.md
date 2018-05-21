# System Services

*No more `context.getSystemService(NAME_OF_SERVICE) as NameOfManager`.*

This library contains all the Android System Services as of API 27.
It allows accessing them with a simple property.

Most System Services use the application Context and are available
anywhere in the code of your app, but some that can vary depending on the
`Context` (like `LayoutInflater` or `WindowManager`) are extensions on
`Context`, `View` or `AccessibilityService`.

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

## Download

```groovy
implementation "com.louiscad.splitties:splitties-systemservices:$splitties_version"
```
