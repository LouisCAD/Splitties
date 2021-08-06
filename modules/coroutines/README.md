# Coroutines

*General purpose extensions to kotlinx.coroutines.*

Supported platforms: **Linux** (x64), **MingW (x64)**, **macOS** (x64), **iOS** (arm32, arm64 & x64), **JS**, **JVM** (including Android).

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.coroutines`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-coroutines`.

## Offering a value to a `SendChannel` (including inside `callbackFlow`) safely

Calling the `offer` function in `SendChannel` as it can crash your program if the channel is closed,
as can be seen in this issue: [https://github.com/Kotlin/kotlinx.coroutines/issues/974](https://github.com/Kotlin/kotlinx.coroutines/issues/974).

To be safe from this pitfall, Splitties provides an extension named `offerCatching(…)` that returns
`false` if `offer` throws, making you safe in the edge cases where a call is made just after the
channel gets closed, as can happen when using `callbackFlow { … }` in multi-threaded environments.


## Cancellable infinite loops

The `repeatWhileActive { … }` top level function is like a `while (true) { … }` loop, except that:
- it evaluates to `Nothing`, allowing you to use it in any lambda, regardless of its return type.
- it will check for cancellation before each execution of the passed lambda, protecting from an
infinite loop if the code in the lambda doesn't check for cancellation but eventually ends running.

It also has an overload that takes a `Boolean` parameter named `ignoreInnerCancellations`. This one
is made for the cases where you're throwing a `CancellationException` to signal the cancellation of
this loop run/iteration, but want to continue looping again. Can be useful if you are using
`withTimeout { … }` or want to easily handle user requested cancellation. It will still check that
the scope has not been cancelled before each iteration, so it's perfectly safe to use among code
that must be cancellable.

#### Example:

```kotlin

suspend fun doStuffUntilCancelled(ui: SomeUi, someObject: SomeType): Nothing {
    repeatWhileActive {
        val command = ui.awaitSomeUserAction()
        someObject.doSomething(command.whatever)
        ui.awaitAnotherUserAction()
        val result = someObject.doSomethingElse()
        ui.showOutcome(result)
    }
}
```

## Racing coroutines

### Number of racers fixed at compile time

The `raceOf(…)` function takes a variable number of coroutines, that also have a `CoroutineScope` as
a receiver. The first coroutine to finish (includes that any child coroutines that might have been
launched are completed or cancelled too) will cancel the others, and its value will be returned by
`raceOf`.

_Note that calling `raceOf` with no racers is forbidden, and any attempt to do so will throw an
`IllegalArgumentException`. There's a deprecated overload of `raceOf()` taking no parameters to
prevent you from making this mistake. For variable number of racing coroutines, use `race` +
`launchRacer` documented below._

#### Simple example (unit test):

```kotlin
suspend fun testCoroutinesRacing() {
    val result = raceOf({
        delay(3)
        "slow"
    }, {
        delay(0)
        "fast"
    })
    assertEquals(expected = "fast", actual = result)
}
```

#### UI code example

```kotlin
suspend fun awaitUserChoice(ui: SomeUi, choices: List<Stuff>): Stuff? = raceOf({
    ui.awaitSomeUserAction(choices)
}, {
    ui.awaitDismissal()
    null
}, {
    ui.showSomethingInRealtimeUntilCancelled() // Returns Nothing, will run, but never "win".
})
```

### Dynamic racers and late racers

The `raceOf(…)` function might not suit your use case if the racing coroutines need to be
launched based on some conditions, that might also take some time to be evaluated.

These more advanced use-cases are satisfied by the `race { … }` function, where you can call
`launchRacer { … }` in its lambda.

If no racing coroutines are ever launched, the `race { … }` function will suspend until it is
cancelled.

#### Example
```kotlin
suspend fun awaitSomeActionTrigger(
    config: SomeConfig,
    deviceCapabilities: HardwareSupportInfo,
    defaultInput: SomeInput,
    awaitSpecialInput: suspend () -> Unit
) {
    race {
        launchRacer { defaultInput.awaitSomeAction() }

        if (deviceCapabilities.supportsSpecialInput) {
            launchRacer { awaitSpecialInput() }
            delay(config.delayBeforeHintMillis) // The race lambda can suspend
            showHintForSpecialInput()
        }
    }
}
```

## Suspending version of `lazy`

These 3 functions provide a `SuspendLazy` instance:

- `CoroutineScope.suspendLazy { … }`
- `suspendBlockingLazy { … }` (uses `Dispatchers.Default` by default)
- `suspendBlockingLazyIO { … }` (JVM-only for now, uses `Dispatchers.IO`)

They can be used almost like Kotlin's `lazy`, but you need parentheses to get the value.

Note that if an `Exception` or any `Throwable` is thrown in their initializing lambda,
the `SuspendLazy` instance will not be recoverable, so if retry logic is needed, it must
include instantiating a new `SuspendLazy`. _If you want it to allow retries on the same instance,
please open an issue and tell about your use case._

#### Example: Initializing a database only once

For an app-wide remote database:
```kotlin
val db: SomeDatabase = GlobalScope.suspendLazy {
    createDatabase().also { it.connect() } // Both functions suspend
}

fun doStuff(someData: SomeData) {
    db().runSomeTransaction(someData)
}
```

For an application-wide local database (e.g. in an Android or desktop JVM app):
```kotlin
val db: AppDatabase = suspendBlockingLazyIO { buildDatabase() }

fun doStuff(someData: SomeData) {
    db().runSomeTransaction(someData)
}
```

Passing a `SuspendLazy` type can be handy:
```kotlin
suspend fun doStuff(getDb: SuspendLazy<AppDatabase>, someData: SomeData) {
    val db = getDb()
    db.runSomeTransaction(someData)
}
```

#### Example: Offloading heavy object instantiation or expensive computation

Objects that need a significant amount of memory (example bitmap/images, large arrays/lists, or
large/deep data structures such as possibly non trivial trees) will block the current thread while
being instantiated, waiting for the CPU to find enough free memory (RAM), which might include moving
lots of stuff to have the contiguous space that can be required.

To avoid blocking the user interface and altering the perceived performance of the application,
you need to not run such code on the main/UI thread, but offload it to another thread such as one
from `Dispatchers.Default`. The `suspendBlockingLazy { … }` makes it very easy to do it right:

```kotlin
val expensiveThing = suspendBlockingLazy { DoHeavyInstantiation() }

fun doStuff(ui: SomeUi) { // Can run on main/UI thread.
    val thing = expensiveThing() // Will suspend until DoHeavyInstantiation() is done.
    ui.displaySomeDataNicely(thing)
}
```
