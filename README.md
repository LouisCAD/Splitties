# Splitties
### What is this
This project is split in small modules, distributed as independent Android libraries, so you can include in your project only what you need.

I try to do my best to make the footprint of each module as small as possible and as performant as possible.

### Why I made this
I personally often copy/pasted the code which makes these libraries into the projects I worked on, so I made this library to make it a one line operation, and make potential updates to this code easier. Now, everyone can use this work.

***Below is a list of the libraries included in this project***
## App Context
*Have a `Context` everywhere.*

Designed for Kotlin.
### Why?
I was tired of searching for a reference to the current context everytime, and tired of not being able to use it in Kotlin's `object` singletons, so I found a safe way to have a reference to the application Context everywhere.
### Usage
When you don't need configuration dependent or themed `Context`, just use `appCtx` from any Kotlin code to get a reference to your application Context. This library also provides a lazily initialized `directBootCtx` for direct boot aware apps.

[Read more here](appctx/README.md)

## Concurrency
*Single thread `lazy` implementations, with reporting via [Timber](https://github.com/JakeWharton/timber) support.*

Designed for Kotlin.
### What it does
Provides `lazy` implementations that can check for access on single thread or UI thread.

Also provides the `mainLooper` and `isUiThread` properties.

### Usage
Write `lazy` as usual, but make sure you have `import splitties.concurrency.lazy` in your imports. If not, you're using the wrong implementation. Note that you can still use regular lazy implementations with this import thanks to inline proxy function to Kotlin's stdlib lazy methods.
You can customize the behavior when a violation occurs. The default one is to throw and `IllegalStateException` but as it's done in the sample, you can change it to report using [Timber](https://github.com/JakeWharton/timber) or to ignore it. When using the report mode, it's easy to forward it to your favorite error reporting system such as [Firebase Crash Reporting](https://firebase.google.com/docs/crash/) using a custom Timber Tree.

[Read more here](concurrency/README.md)

## Material Lists
*List Items for RecyclerView implementing [Material Design Guidelines](https://material.io/guidelines/)*

*Warning! I found a few display issues when the text is longer than the width of the RecyclerView, so I don't recommend to use this right now, but it'll be fixed in next version!*

Written in Java only, but the simple API is Kotlin friendly. Uses ConstraintLayout and LinearLayout.

You can directly use the layout files.
Not all lists items documented in the guidelines are implemented for now. If you want another one not implemented, please, file an issue.

The goal of this library is to finally provide the most efficient and lightweight implementation for all [list items](https://material.io/guidelines/components/lists.html) and [list control](https://material.io/guidelines/components/lists-controls.html) items described in the Material Design Guidelines.

Here are the available list items:

* `R.layout.list_item_single_line_icon`
* `R.layout.list_item_switch_two_lines_icon`
* `R.layout.list_item_two_lines_icon_switch`
* `R.layout.list_item_two_lines_icon`

See an example in the sample.

## Preferences
*Property syntax for Android's SharedPreferences.*

For use in Kotlin.

### Usage
Define your preferences in an `object` or a `class` like in the example below:
```kotlin
import splitties.preferences.Preferences

object GamePreferences : Preferences("gameState") {
    var magicNumber by intPref(0) // Key is the property name.
    var currentLevel by IntPref("currentLevel", 1)
    var bossesFought by IntPref("bossBattleVictories", 0)
    var lastTimePlayed by LongPref("lastSessionTime", 0L)
    var pseudo by StringPref("playerPseudo", "Player 1")
}
```
### How it works
This library uses Kotlin's property delegation to make using SharedPreferences as easy as accessing a property on an object. It relies on the `appCtx` module of this library to allow usage in `object` in Kotlin, and can support storage on device encrypted storage for devices supporting Direct Boot. See [the source code](preferences/src/main/java/splitties/preferences) for more information

## Selectable Views
*Selectable TextView, ViewGroups and RecyclerView list items made easy.*

Made in Java. Works exactly the same in Kotlin.
### What it does
It adds a `foreground` attribute to `TextView` and common ViewGroups (`ConstraintLayout`, `LinearLayout` and `RelativeLayout`) which defaults to `@android:attr/selectableItemBackground`, allowing visual feedback when the user selects the View. This can be useful in a `RecyclerView`.
### Usage
Just use `SelectableLinearLayout`, `SelectableRelativeLayout` or `SelectableConstraintLayout` in your layouts, or extend them, and if you want to customize the foreground, follow this example:
```xml
<SelectableConstraintLayout
    ...
    app:foreground="@drawable/your_state_drawable>
    ...
</SelectableConstraintLayout>
```
Note that the `foreground` atrribute does not clash with android's one (for API 23+), and applies to subclasses.
### Explanation
`FrameLayout` is historically the only `ViewGroup` which supports a foreground drawable attribute. The foreground attribute is often used with value `@android:attr/selectableItemBackground` to provide visual feedback when the user touches a selectable element such as a list item in a RecyclerView, which translates as a Ripple effect starting from Android Lollipop. As a consequence, people usually wrap their `RelativeLayout`, `LinearLayout` or other `ViewGroup` with a `FrameLayout` just to provide this foreground attribute. This is unefficient, espacially if this is replicated as it is in a RecyclerView. The foreground attribute is part of the `View` class since Android Marshmallow now, but few apps can have their minSdk set to 23, so I made this library which provides a `foreground` attribute that defaults to `@android:attr/selectableItemBackground` for popular `ViewGroups` and `TextView`.

## Stetho init
*Have [Stetho](https://github.com/facebook/stetho) without writing any code!*

Made in Java (because using Kotlin here would be overkill).
### What it does
This library uses a Content Provider (like Firebase) to initialize Stetho automatically. You just have to include the dependency on your debug build and voilà!
### Usage
Just add the dependency to your debug build like in the example below:
```groovy
debugCompile "xyz.louiscad.splitties:splitties-stetho-init:$splittiesVersion"
```

## Typesafe RecyclerView
Written in Kotlin and Java. Works similarly in both languages.
### What it does
It makes using RecyclerView simpler, with less boilerplate for basic or less basic usages.
### Usage
Download or clone the project and open it in Android Studio to see the sample. Alternatively, take a look at these classes:
[DemoAdapter](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/adapter/DemoAdapter.java), [DemoListItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/widget/DemoListItem.java), [DemoItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/model/DemoItem.java) and [ImmutableBasicItem](https://github.com/LouisCAD/Splitties/blob/master/sample/src/main/java/xyz/louiscad/splittiessample/ui/model/ImmutableBasicItem.java).
### Explanation
This modules consists of two `ViewHolder` subclasses that make it typesafe, and easier to use for the common use case which is to bind a ViewHolder to a POJO. See the sample to understand how it works.

## Download
If you use gradle and have `jcenter()` (default for new Android Studio projects) in your repositories, in your project's `build.gradle` file, add the version of the library:
```groovy
allProjects {
    ext {
        splittiesVersion = '1.3.0'
    }
}
```
Here are all the artifacts of this library. Just use the ones you need:
```groovy
compile "xyz.louiscad.splitties:splitties-appctx:$splittiesVersion"
compile "xyz.louiscad.splitties:splitties-concurrency:$splittiesVersion"
compile "xyz.louiscad.splitties:splitties-material-lists:$splittiesVersion"
compile "xyz.louiscad.splitties:splitties-preferences:$splittiesVersion"
compile "xyz.louiscad.splitties:splitties-selectableviews:$splittiesVersion"
debugCompile "xyz.louiscad.splitties:splitties-stetho-init:$splittiesVersion"
compile "xyz.louiscad.splitties:splitties-typesaferecyclerview:$splittiesVersion"
```
For maven and alternative build-systems, check the [Bintray page](https://bintray.com/louiscad/splitties/splitties).

## New versions notifications
To get notified for new versions, be sure to click on "Watch" on the [splitties Bintray page](https://bintray.com/louiscad/splitties/splitties).

## Improve this library
Included classes are not exhaustive, so if you find some code that follows this library's philosophy and you think it should be here, feel free to open an issue.

Documentation contributions are also welcome, but only high quality is expected.

And of course, the same goes if you find a bug.

Finally, if you find a typo, please, submit a pull-request without opening an issue.

## License
This library is published under Apache License version 2.0 which you can see [here](https://github.com/LouisCAD/Reusables/blob/master/LICENSE).
