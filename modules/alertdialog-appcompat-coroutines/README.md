# Alert Dialog AppCompat Coroutines

*`showAndAwait` extension functions for AppCompat AlertDialog.*

## Example

```kotlin
import splitties.alertdialog.appcompat.alertDialog
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.coroutines.showAndAwait
import splitties.resources.txt

suspend fun shouldWeReallyDeleteFromTrash(): Boolean = alertDialog(
    message = txt(R.string.dialog_msg_confirm_delete_from_trash)
).showAndAwait(
    okValue = true,
    cancelValue = false,
    dismissValue = false
)
```

## Download

```groovy
implementation("com.louiscad.splitties:splitties-alertdialog-appcompat-coroutines:$splitties_version"))
```
