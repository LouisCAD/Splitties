# Arch Lifecycle

*Extensions to get `ViewModel`s, use `LiveData` and observe `Lifecycle`s.*

This makes using Android Architecture Components nicer in Kotlin.

## Content

### LifecycleObserver

This is a `GenericLifecycleObserver` sub-interface that has lifecycle state
change methods (like `onResume(…)` or `onPause(…)`) with default
implementations so you override only the ones you need.

### ViewModel providers

ViewModels are instantiated by host `Activity` scope.

The `activityScope<YourViewModel>()` extensions on `FragmentActivity` and
support `Fragment` return a `Lazy<YourViewModel` instance that you can
use on a delegated property in your `Activity` or `Fragment`:

```kotlin
class YourActivity : AppCompatActivity() {
    private val viewModel: YourViewModel by activityScope()
    private val anotherViewModel by activityScope<AnotherViewModel>()
}
```

```kotlin
class SomeFragment : Fragment() {
    private val viewModel by activityScope<YourViewModel>()
}
```

### LiveData observing and map extension

#### `observe` and `observeNotNull` extension functions on `LifecycleOwner`

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

## Download

```groovy
implementation "com.louiscad.splitties:splitties-arch-lifecycle:$splitties_version"
```
