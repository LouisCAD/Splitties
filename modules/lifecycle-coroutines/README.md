# Lifecycle Coroutines

*Coroutines integration with [`Lifecycle`][lifecycle]s.*

Extension properties:

| **Name** | **Description**
| -------- | ---------------
| `LifecycleOwner.lifecycleScope` | A scope that dispatches on Android Main thread and is active until the LifecycleOwner is destroyed.
| `Lifecycle.coroutineScope` | A scope that dispatches on Android Main thread and is active until the Lifecycle is destroyed.
| `Lifecycle.job` | A job that is cancelled when the Lifecycle is destroyed.

Extension functions:

| **Name** | **Description**
| -------- | ---------------
| `Lifecycle.awaitResumed` | A suspending function that returns/resumes as soon as the state of the Lifecycle is resumed.
| `Lifecycle.awaitStarted` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least started.
| `Lifecycle.awaitCreated` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least created.
| `Lifecycle.awaitState` | A suspending function that returns/resumes as soon as the state of the Lifecycle is at least the passed state.
| `Lifecycle.createJob` | A job that is active while the state is at least the passed one.
| `Lifecycle.createScope` | A scope that dispatches on Android Main thread and is active while the state is at least the passed one.

## Example

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            someSuspendFunction()
            someOtherSuspendFunction()
            someCancellableSuspendFunction()
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

## Performance fix for Dispatchers.Main initialization

Note that this split includes a workaround for [this performance issue in kotlinx.coroutines](
https://github.com/Kotlin/kotlinx.coroutines/issues/878) caused by `ServiceLoader` doing I/O.
An alternative to `Dispatchers.Main` is used internally so it doesn't cause slow cold starts for
your app. You can use it directly too as it is defined as `Dispatchers.MainAndroid`, but note that
it will be removed when a proper fix in kotlinx.coroutines or in the Android toolchain is released.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-lifecycle-coroutines:$splitties_version")
```

[lifecycle]: https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle
