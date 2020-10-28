# Lifecycle Coroutines

*Coroutines integration with [`Lifecycle`][lifecycle]s.*

Supported platforms: **Android**.

**Note:**
This split exposes its dependency on `AndroidX Lifecycle runtime KTX`, which notably brings
`lifecycleScope` for `LifecycleOwner` (`Activity`, `Fragment`, `LifecycleService`…) and
`coroutineScope` for `Lifecycle`. See the [documentation of Android Lifecycle here](https://developer.android.com/reference/kotlin/androidx/lifecycle/package-summary).

Extension functions:

| **Name** | **Description**
| -------- | ---------------
| `Lifecycle.awaitResumed` | A suspending function that returns/resumes as soon as the state of the Lifecycle is resumed.
| `Lifecycle.awaitStarted` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least started.
| `Lifecycle.awaitCreated` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least created.
| `Lifecycle.awaitState` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least the passed state.
| `Lifecycle.isStartedFlow` | Returns a `Flow` whose value is `true` while the lifecycle is started. An experimental overload takes a timeout.
| `Lifecycle.isResumedFlow` | Returns a `Flow` whose value is `true` while the lifecycle is resumed. An experimental overload takes a timeout.
| `Lifecycle.stateFlow` | Returns a `Flow` whose value reflects the current `Lifecycle.STATE`.
| `Lifecycle.createJob` | A job that is active while the state is at least the passed one.
| `Lifecycle.createScope` | A scope that dispatches on Android Main thread and is active while the state is at least the passed one.

## Example

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            someSuspendFunction()
            lifecycle.awaitResumed()
            showSomethingWithAnimation()
        }

        isStartedFlow(5.seconds).transformLatest { isStarted ->
            if (isStarted) {
                emitAll(someDataUpdates())
            }
        }.onEach { dataSnapshot ->
            ui.updateLatestData(dataSnapshot)
        }.launchIn(lifecycleScope)

        lifecycleScope.launch {
            isResumedFlow().collectLatest { isResumed ->
                if (isResumed) {
                    stats.usageTracker.countTimeSpentUntilCancelled()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val startedScope = lifecycle.createScope(activeWhile = Lifecycle.State.STARTED)
        startedScope.launch {
            aCancellableSuspendFunction()
            yetAnotherCancellableSuspendFunction()
        }
        startedScope.aMethodThatWillLaunchSomeCoroutines()
    }
}
```

## Download

```groovy
implementation("com.louiscad.splitties:splitties-lifecycle-coroutines:{{version.splitties3}}")
```

[lifecycle]: https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle
