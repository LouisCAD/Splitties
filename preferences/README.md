# Preferences

*Property syntax for Android's SharedPreferences.*

This library uses Kotlin's property delegation to make using
SharedPreferences as easy as accessing a property on an object. It
relies on the `appCtx` module of this library to allow usage in `object`,
and can support storage on device encrypted storage for devices
supporting Direct Boot. See [the source code](
/src/main/java/splitties/preferences) for more information.

## Table of contents

* [Defining the preferences properties in an object](#defining-the-preferences-properties-in-an-object)
  * [Why `object` and not `class`?](#why-object-and-not-class)
* [Loading the preferences without blocking the main thread](#loading-the-preferences-without-blocking-the-main-thread)
* [Download](#download)

## Defining the preferences properties in an object

Define your preferences in an `object` that extends
`splitties.preferences.Preferences`, like in the example below:
```kotlin
import splitties.preferences.Preferences

object GamePreferences : Preferences("gameState") {
    var magicNumber by intPref(0) // The property name is used as the key.
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
    var favoriteCharacter by stringOrNullPref()
}
```

Then just use the properties:

```kotlin
fun setResponseOfTheUltimateQuestionOfLifeTheUniverseAndEverything() {
    GamePreferences.magicNumber = 42
}

fun doSomeMagic() {
    toast("Magic: ${GamePreferences.magicNumber}!")
}

fun resetProgress() {
    GamePreferences.edit { // Batch edit
        currentLevel = 1
        bossesFought = 0
    }
}
```

The supported types are:
* `Boolean`
* `Int`
* `Float`
* `Long`
* `String`
* `String?`
* `Set<String>`
* `Set<String>?`

For **default SharedPreferences**, make an `object` that extends
`DefaultPreferences` instead of `Preferences`.

### Why `object` and not `class`?

Unless you use coroutines (read more about this in next section just below),
a `class` instead of an `object` is not recommended because it would mean
you can instantiate it multiple times, while the underlying preferences
xml file is cached for the rest of your app's process lifetime once loaded,
so in a `class` you'd be allocating the delegates more times than needed,
leading to an additional, unneeded, small pressure on the garbage collector.

However, you may make an abstract subclass of `Preferences` for specific
use cases where adding logic to base `Preferences` or sharing some
properties may be desirable. (If you do, please open an issue to tell us
about this use case. It may become an example shown here.)

## Loading the preferences without blocking the main thread

The `object` approach described above has several advantages, one of
the most significant being ease of use anywhere in your app, but that
also means you can easily access it from the main thread, and the first
time you access the object, the underlying xml file where the preferences
are stored is loaded, which may block the main thread for longer that you
would want, possibly dropping a few frames.

With coroutines, it's easy to offload something on another thread, and
this split embraces this capability.

Let's see a modified version of the `GamePreferences` described above,
before passing in review each change.

```kotlin
import splitties.preferences.SuspendPrefsAccessor
import splitties.preferences.Preferences

class GamePreferences private constructor() : Preferences("gameState") {
    companion object : SuspendPrefsAccessor<GamePreferences>(::GamePreferences)

    var magicNumber by intPref(0) // The property name is used as the key.
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
    var favoriteCharacter by stringOrNullPref()
}
```

Here are all the changes:
- We moved from `object` to `class`.
- We added a `private constructor()`.
- We added a `companion object` that extends the `SuspendPrefsAccessor` abstract
class and calls its constructor with a reference to the constructor.

With this change, we can no longer access the `GamePreferences` singleton directly
from anywhere… unless we are in a coroutine!

From any `suspend` function, you
just have to call `GamePreferences()` like you were calling a constructor, but
in reality, it is a function call that suspends while loading the preferences
for the first time in process life in `Dispatchers.IO`.

If the preferences have already been loaded, it immediately returns the now
instantiated singleton.

If you have non suspending functions that would need to access the preferences,
you have two options: pass your `Preferences` subclass as a parameter, or make
it a `suspend` function.

## Download

```groovy
implementation "com.louiscad.splitties:splitties-preferences:$splitties_version"
```
