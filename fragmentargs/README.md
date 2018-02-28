# Fragment Args

*Fragment arguments without ceremony thanks to delegated properties.*

### Example

```kotlin
class YourFragment : Fragment() {

    var someRequiredId: Int by arg()
    var optionalArg: String? by argOrNull()

    private fun yourMethod() = yourCode()
}

fun createYourFragment(someId: Int): Fragment = YourFragment().apply {
    someRequiredId = someId
}
```
