# Exceptions

*`illegal(…)` and similar functions that return `Nothing`, handy for
impossible or illegal `when` branches.*

Here's the list of these functions:
* `unexpectedValue(…)` (puts the value in the Exception message)
* `illegal(…)` (shortcut to `throw IllegalStateException(…)`)
* `illegalArg(…)` (shortcut to `throw IllegalArgumentException(…)`)
* `unsupported(…)` (shortcut to `throw UnsupportedOperationException(…)`)
* `unsupportedAction(…)` (designed for unsupported `Intent` actions)

## Download

```groovy
implementation "com.louiscad.splitties:splitties-exceptions:$splitties_version"
```
