# Alert Dialog Material

*Create simple alert dialogs in material design with simple code*

Supported platforms: **Android**.

## Setup

If you want to use this dependency without using one of the [fun packs](../../README.md#download),
you can use `Splitties.alertdialogMaterial`, provided you have [refreshVersions](https://github.com/jmfayard/refreshVersions) added to the project.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-alertdialog-material`.

## Example

```kotlin
import splitties.alertdialog.material.materialAlertDialog
import splitties.alertdialog.appcompat.cancelButton
import splitties.alertdialog.appcompat.messageResource
import splitties.alertdialog.appcompat.okButton
import splitties.alertdialog.appcompat.onShow
import splitties.alertdialog.appcompat.positiveButton

class YourActivity : AppCompatActivity {

    //...

    private fun doIrreversibleStuffOrCancel() {
        materialAlertDialog {
            messageResource = R.string.dialog_msg_confirm_irreversible_stuff
            okButton { irreversibleStuff() }
            cancelButton()
        }.onShow {
            positiveButton.textColorResource = R.color.red_500
        }.show()
    }
}
```
