# Views Coroutines

*Android Views + Kotlin coroutines.*

Extension functions:

| **Name** | **Description**
| -------- | ---------------
| `View.awaitOneClick` | Suspends until the view is clicked. Cancellable.
| `View.awaitOneLongClick` | Suspends until the view is long clicked. Cancellable.
| `View.visibleUntilClicked` | Makes the view visible until it is clicked and the passed lambda is executed. Cancellable.
| `View.visibleUntilLongClicked` | Makes the view visible until it is long clicked and the passed lambda is executed. Cancellable.
| `View.visibleInScope` | Makes the view visible while the passed lambda is executing.
| `View.goneInScope` | Makes the view gone while the passed lambda is executing.
| `View.invisibleInScope` | Makes the view invisible while the passed lambda is executing.

## Download

```groovy
implementation("com.louiscad.splitties:splitties-views-coroutines:$splitties_version")
```
