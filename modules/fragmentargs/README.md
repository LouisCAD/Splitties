# Fragment Args

*Fragment arguments without ceremony thanks to delegated properties.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.fragmentargs`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-fragmentargs`.

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
