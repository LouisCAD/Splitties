# Alert Dialog

*Create simple alert dialogs with simple code*

Supported platforms: **Android**.

You may be looking for [the Material version](../alertdialog-material) or
[the AppCompat version](../alertdialog-appcompat/README.md).

## Setup

This dependency is not included in any of the [fun-packs](../../README.md#download),
because typical Android apps use the Material or AppCompat version.

However, it might prove handy for WearOS apps where you should use the platform one.

Add it with [refreshVersions](https://github.com/jmfayard/refreshVersions):
`Splitties.alertdialog`.

For reference, the maven coordinates of this module are `com.louiscad.splitties:splitties-alertdialog`.

## Example

```kotlin
import splitties.alertdialog.alertDialog
import splitties.alertdialog.cancelButton
import splitties.alertdialog.messageResource
import splitties.alertdialog.okButton
import splitties.alertdialog.onShow
import splitties.alertdialog.positiveButton

class YourActivity : AppCompatActivity {

    //...

    private fun doIrreversibleStuffOrCancel() {
        alertDialog {
            messageResource = R.string.dialog_msg_confirm_irreversible_stuff
            okButton { irreversibleStuff() }
            cancelButton()
        }.onShow {
            positiveButton.textColorResource = R.color.red_500
        }.show()
    }
}
```
