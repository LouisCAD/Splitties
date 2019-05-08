# Bundle

*`BundleSpec` to use `Bundle` with property syntax for `Intent` extras
and more.*

## Non-exhaustive list of use cases

* `Intent` extras of Activities, BroadcastReceivers, Services, etc.
* Instance state of Activities, Fragments and Views.

Can be used for `Activity` extras, but also in `Service` extras,
`BroadcastReceiver` and other parts where an `Intent` or a `Bundle` is used.

## Usage

### For `Activity` extras

1. Let's say you have an `Activity` subclass named `YourActivity`.
1. In `YourActivity`, add a nested `object` named `ExtrasSpec`.
2. Make it extend `BundleSpec`.
3. For each required `Intent` extra you need, in `ExtrasSpec`, add a
`var` property with explicit non null type, delegated `by bundle()`.
3. For each optional `Intent` extra you need, still in `ExtrasSpec`, add a
`var` property with either and explicit nullable type, delegated `by bundleOrNull()`,
or with a non null type, delegated `by bundleOrDefault(…)` or `by bundleOrElse { … }`.
4. When setting up the `Intent` to start your `Activity`, call
`putExtras(YourActivity.ExtrasSpec) { … }` on it, setting values on the
`ExtrasSpec` properties in the lambda.
5. From `YourActivity`, call `withExtras(ExtrasSpec) { … }` to get the extras
as properties from the lambda (the result of the call is the result of the
lambda).

### Other usages

Only `Activity` has a `withExtras(…) { … }` extension, but you
can use `with(…) { … }` extension on any `Bundle` and any `Intent` `extras`.

The `putExtras(…) { … }` extension works on any `Intent`, Activity or not.

See the examples below.

### Important details

See the implementation of the `put(…)` extension on `Bundle` to check all
the supported types (it is likely to suit your needs).

You may only access the delegated properties of a `BundleSpec` subclass
inside the `withExtras(…) { … }` lambda or inside the
`someBundle.with(…) { … }` lambda. If you violate this rule, an
`IllegalStateException` will be thrown.

The implementation has been optimized for efficiency. The delegates under
`bundle()` and `bundleOrNull()` are singletons and are shared for all
properties app-wide.
The versions that accept a key or a default value can't be singletons, but since
you're likely using them in `object` backed specs, they are instantiated only
once per property, having a minimal memory impact (especially when compared to
the cost of data serialization in Bundles).

## Examples

### Extras in an Activity

```kotlin
class DemoActivity : AppCompatActivity() {

    object ExtrasSpec : BundleSpec() {
        var userName: String by bundle() // Required extra
        var showGreetingToast by bundleOrDefault(false) // Optional extra, defaults to false
        var optionalExtra: String? by bundleOrNull() // Optional extra
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        withExtras(ExtrasSpec) { // Populates ExtrasSpec with actual extras
            if (showGreetingToast) toast(android.R.string.ok)
            optionalExtras?.forEach {
                Timber.i("Character from optional extra: $it")
            }
        }
        restOfYourCode()
    }
}

class StartDemoActivity : AppCompatActivity() {

    private fun someFunction(name: String, isUserPolite: Boolean = false) {
        startActivity(Intent(this, DemoActivity::class.java).apply {
            putExtras(DemoActivity.ExtrasSpec) {
                userName = name
                showGreetingToast = isUserPolite
            }
        })
    }
}
```

### Extras in a BroadcastReceiver

```kotlin
class AirplaneModeReceiver : BroadcastReceiver() {

    object ExtrasSpec : BundleSpec() {
        var isAirplaneModeOn: Boolean by bundle("state")
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_AIRPLANE_MODE_CHANGED) return
        val isAirplaneModeOn = intent.extras.with(ExtrasSpec) { isAirplaneModeOn }
        handleAirplaneMode(isAirplaneModeOn)
    }
}
```

### Instance State in an Activity

```kotlin
class DemoActivity : AppCompatActivity() {

    private object InstanceStateSpec : BundleSpec() {
        var startTime: Long by bundle()
    }

    private var startTimestamp = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimestamp = savedInstanceState?.with(InstanceStateSpec) {
            startTime
        } ?: System.currentTimeMillis()
        restOfYourCode()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.with(InstanceStateSpec) {
            startTime = startTimestamp
        }
        super.onSaveInstanceState(outState)
    }
}
```

## Download

```groovy
implementation("com.louiscad.splitties:splitties-bundle:$splitties_version")
```
