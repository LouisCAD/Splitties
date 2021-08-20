# Alert Dialog AppCompat Coroutines

*`showAndAwait` extension functions for AppCompat AlertDialog.*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.alertdialogAppcompatCoroutines`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-alertdialog-appcompat-coroutines`.

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
