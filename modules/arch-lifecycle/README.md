# Arch Lifecycle

*Extensions to get `ViewModel`s, use `LiveData` and observe `Lifecycle`s.*

Supported platforms: **Android**.

This makes using Android Architecture Components nicer in Kotlin.

## Setup

This dependency is not included in any of the [fun-packs](../../README.md#download),
because we don't believe all apps need its content nowadays.

Add it with [refreshVersions](https://github.com/jmfayard/refreshVersions):
`Splitties.archLifecycle`.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-arch-lifecycle`.

## Content

### LifecycleObserver

This is a `LifecycleEventObserver` sub-interface that has lifecycle state
change methods (like `onResume(…)` or `onPause(…)`) with default
implementations, so you override only the ones you need.

### ViewModel providers

AndroidX Activity KTX and Fragment KTX provide convenient delegates to get a `ViewModel` subclass instance, but they lack a facility when you need to pass arguments to a `ViewModel`.

Splitties brings a version that takes a lambda, allowing you to run any logic before instantiating the ViewModel, including accessing the created `Activity` or `Fragment`.

```kotlin
class YourActivity : AppCompatActivity() {

    private val viewModel: SomeViewModel by viewModels() // From androidx.activity KTX
    private val anotherViewModel by viewModels<AnotherViewModel>() // From androidx.activity KTX

    private val yourViewModel by viewModels { // From Splitties
        YourViewModel(someParams)
    }
}
```

```kotlin
class SomeFragment : Fragment() {

    private val viewModel: SomeViewModel by viewModels() // From androidx.activity KTX
    private val anotherViewModel: AnotherViewModel by activityViewModels() // From androidx.activity KTX

    private val yourViewModel by viewModels { // From Splitties
        YourViewModel(someParams)
    }
}
```

### LiveData observing and map extension

#### `observe` and `observeNotNull` extension functions on `LifecycleOwner`

These extensions make observing a `LiveData` null-safe (despite `LiveData` not being null-safe itself), giving you the choice of whether you want to deal with nulls or ignore them.

```kotlin
class YourActivity : AppCompatActivity() {

    private val viewModel by activityScope<YourViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        observe(viewModel.yourLiveData) { data: YourData? ->
            updateUi(data)
        }
        observeNotNull(viewModel.anotherLiveData) {
            doSomething(it.someProperty)
            doSomethingElse(it)
        }
    }
}
```

#### `map` extension function on `LiveData`

```kotlin
class YourViewModel : ViewModel() {

    val yourLiveData: LiveData<YourData> = createYourLiveData()
    val anotherLiveData = yourLiveData.map { it?.someProperty }
}
```

Note that the `map` lambda runs on UI thread, so very light operations like
getting a property is right, but long/blocking operations are not (would
result in lags or ANRs).
