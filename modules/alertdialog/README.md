# Alert Dialog

*Create simple alert dialogs with simple code*

You may be looking for [the AppCompat version](../alertdialog-appcompat/README.md).

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

## Download

```groovy
implementation("com.louiscad.splitties:splitties-alertdialog:$splitties_version")
```
