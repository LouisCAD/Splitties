# Preferences

*Property syntax for Android's `SharedPreferences`/`DataStore` or iOS/macOS `NSUserDefaults`.*

Supported platforms: **macOS** (x64), **iOS** (arm32, arm64 & x64), **Android**.

This library uses Kotlin's property delegation to make using
`SharedPreferences` and `DataStore<Preferences>` as easy as accessing a property on an object,
and provides an `NSUserDefaults` backed implementation for macOS and iOS.

On Android, it relies on the `appCtx` module of this library to allow usage in `object`,
and can support storage on device encrypted storage for devices
supporting Direct Boot. See [the source code](
/src/androidMain/kotlin/splitties/preferences) for more information.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.preferences`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-preferences`.

## Table of contents

* [Defining the preferences properties in an object](#defining-the-preferences-properties-in-an-object)
  * [Why `object` and not `class`?](#why-object-and-not-class)
* [Loading the preferences without blocking the main thread](#loading-the-preferences-without-blocking-the-main-thread)
* [Using AndroidX DataStore instead of SharedPreferences under the hood](#using-androidx-datastore-instead-of-sharedpreferences-under-the-hood)

## Defining the preferences properties in an object

Define your preferences in an `object` that extends
`splitties.preferences.Preferences`, like in the example below:
```kotlin
import splitties.preferences.Preferences

object GamePreferences : Preferences("gameState") {
  var magicNumber by intPref("magicNumber", defaultValue = 0)
  var currentLevel by intPref("currentLevel", 1)
  var bossesFought by intPref("bossBattleVictories", 0)
  var lastTimePlayed by longPref("lastSessionTime", 0L)

  val pseudoFlow: Flow<String>
  var pseudo by stringPref("playerPseudo", "Player 1").also {
    pseudoFlow = it.valueFlow()
  }

  val favoriteCharacterFlow: Flow<String?>
  var favoriteCharacter by stringOrNullPref("favoriteCharacter").also {
    favoriteCharacterFlow = it.valueFlow()
  }
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

Note that for better encapsulation, you might want to keep the mutable delegated properties private
in some cases, and expose functions and flows instead.

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

_Note that this feature is currently only supported on Android.
Feel free to open an issue if you want it on other platforms._

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

    var magicNumber by intPref("magicNumber", defaultValue = 0)
    var currentLevel by intPref("currentLevel", 1)
    var bossesFought by intPref("bossBattleVictories", 0)
    var lastTimePlayed by longPref("lastSessionTime", 0L)
    
    val pseudoFlow: Flow<String>
    var pseudo by stringPref("playerPseudo", "Player 1").also {
      pseudoFlow = it.valueFlow()
    }
    
    val favoriteCharacterFlow: Flow<String?>
    var favoriteCharacter by stringOrNullPref("favoriteCharacter").also {
      favoriteCharacterFlow = it.valueFlow()
    }
}
```

Here are all the changes:
1. We moved from `object` to `class`.
2. We added a `private constructor()`.
3. We added a `companion object` that extends the `SuspendPrefsAccessor` abstract
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

## Using AndroidX DataStore instead of SharedPreferences under the hood

Android's `SharedPreferences` has one performance drawback: it can perform disk I/O
on the main thread on its own when the `onStop` callback of an `Activity` is called.
The only way to ensure your app will not be affected by the potential ANRs
(Application Not Responding) it can cause is to replace it with something else.

The AndroidX team made [DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
just for that, but its API is not compatible, which would require significant code changes in host projects. 

If you use this (Splitties Preferences) however, all the hard work has been done for you.

There are just 3 quick steps to perform to use DataStore instead of SharedPreferences:

1. Make sure you use `SuspendPrefsAccessor`, as described in the section above (not doing so will crash on first access).
2. Extend `DataStorePreferences` instead of `Preferences`.
3. Opt-in by adding `@OptIn(DataStorePreferencesPreview::class)` to your class.

Once you're done, test your app to make sure it still works fine.

Data migration is automatically done if you're moving from SharedPreferences and keep the same name.
If you were using `DefaultPreferences`, you can pass `null` to `name`.

Be sure that there's only one class that uses the same name, or DataStore will throw.

Finally, note that direct boot (aka. deviceProtectedStorage) is not supported yet, but is being considered.
Add a 👍 on [this issue](https://github.com/LouisCAD/Splitties/issues/290) to raise the priority.
