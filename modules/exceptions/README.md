# Exceptions

*`unexpectedValue(…)`, `unsupportedAction(…)` and similar functions that return `Nothing`.*

Here's the list of these functions:
* `unexpectedValue(…)` (puts the value in the `IllegalStateException` message)
* `illegalArg(…)` (shortcut to `throw IllegalArgumentException(…)`)
* `unsupported(…)` (shortcut to `throw UnsupportedOperationException(…)`)
* `unsupportedAction(…)` (designed for unsupported `Intent` actions)

## Download

```groovy
implementation("com.louiscad.splitties:splitties-exceptions:$splitties_version")
```
