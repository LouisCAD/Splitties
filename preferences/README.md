# Preferences

*Property syntax for Android's SharedPreferences.*

For use in Kotlin. This library uses Kotlin's property delegation to make
using SharedPreferences as easy as accessing a property on an object. It
relies on the `appCtx` module of this library to allow usage in `object` in
Kotlin, and can support storage on device encrypted storage for devices
supporting Direct Boot. See [the source code](
/src/main/java/splitties/preferences) for more information

### Usage
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

#### Why `object` and not `class`?

Using a `class` instead of an `object` is not recommended because it would
mean you can instantiate it multiple times, while the underlying preferences
xml file is cached for the rest of your app's process lifetime once loaded,
so in a `class` you'd be allocating the delegates more times than needed,
leading to an additional, unneeded, small pressure on the garbage collector.

However, you may make an abstract subclass of `Preferences` for specific
use cases where adding logic to base `Preferences` or sharing some
properties may be desirable. (If you do, please open an issue to tell us
about this use case. It may become an example shown here.)

## Download

```groovy
implementation "com.louiscad.splitties:splitties-preferences:$splitties_version"
```
