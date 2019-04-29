# Fragment Args

*Fragment arguments without ceremony thanks to delegated properties.*

### Example

```kotlin
class YourFragment : Fragment() {

    var someRequiredId: Int by arg()
    var optionalArg: String? by argOrNull()
    var nonNullOptionalArg: String by argOrDefault("")
    var anotherNonNullOptionalArg: String by argOrElse { "splitties rock!".capitalize() }

    private fun yourMethod() = yourCode()
}

fun createYourFragment(someId: Int): Fragment = YourFragment().apply {
    someRequiredId = someId
}
```

## Download

```groovy
implementation("com.louiscad.splitties:splitties-fragmentargs:$splitties_version")
```
