# Alert Dialog AppCompat

*Create simple alert dialogs with simple code*

## Example

```kotlin
import alert
import cancelButton
import setMessageResource
import okButton
import onShow
import getPositiveButton

class YourActivity : AppCompatActivity {

    //...

    private fun doIrreversibleStuffOrCancel() {
        alert {
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
implementation("com.louiscad.splitties:splitties-alertdialog-appcompat:$splitties_version")
```
