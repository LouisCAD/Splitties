# Lifecycle Coroutines

*Coroutines integration with [`Lifecycle`][lifecycle]s.*

Extension properties:

| **Name** | **Description**
| -------- | ---------------
| `Lifecycle.coroutineScope` | A scope that dispatches on Android Main thread and is active until the Lifecycle is destroyed.
| `LifecycleOwner.coroutineScope` | A scope that dispatches on Android Main thread and is active until the LifecycleOwner is destroyed.
| `Lifecycle.job` | A job that is cancelled when the Lifecycle is destroyed.

Extension functions:

| **Name** | **Description**
| -------- | ---------------
| `Lifecycle.createJob` | A job that is active while the state is at least the passed one.
| `Lifecycle.createScope` | A scope that dispatches on Android Main thread and is active while the state is at least the passed one.

## Example

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.coroutineScope.launch {
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

## Download

```groovy
implementation "com.louiscad.splitties:splitties-lifecycle-coroutines:$splitties_version"
```

[lifecycle]: https://developer.android.com/reference/kotlin/androidx/lifecycle/Lifecycle
