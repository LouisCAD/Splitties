package splitties.views.dsl.material

import android.content.Context
import com.google.android.material.button.MaterialButton
import splitties.views.dsl.material.styles.ButtonMaterialComponentsStyles

class MaterialComponentsStyles(@PublishedApi internal val ctx: Context) {
    init {
        ctx.theme.applyStyle(R.style.MaterialComponentsStyles, false)
    }

    /**
     * See [MaterialButton] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialButton.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/button/MaterialButton).
     */
    inline val button get() = ButtonMaterialComponentsStyles(ctx)
}
