# Alert Dialog

*Create simple alert dialogs with simple code*

## Example

```kotlin
import splitties.alertdialog.alert
import splitties.alertdialog.cancelButton
import splitties.alertdialog.messageResource
import splitties.alertdialog.okButton
import splitties.alertdialog.onShow
import splitties.alertdialog.positiveButton

class YourActivity : AppCompatActivity {

    //...

    private fun doIrreversibleStuffOrCancel() {
        alert {
            messageResource = R.string.dialog_msg_confirm_irreversible_stuff
            okButton { irreversibleStuff() }
            cancelButton()
        }.onShow {
            positiveButton.textColorResource = R.color.material_red_500
        }.show()
    }
}
```
