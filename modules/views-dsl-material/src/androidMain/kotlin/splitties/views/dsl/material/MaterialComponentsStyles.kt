/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.dsl.material

import android.content.Context
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputLayout
import splitties.views.dsl.material.styles.*

class MaterialComponentsStyles(@PublishedApi internal val ctx: Context) {
    init {
        ctx.theme.applyStyle(R.style.MaterialComponentsStyles, false)
    }

    /**
     * See [BottomAppBar] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/BottomAppBar.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/bottomappbar/BottomAppBar).
     */
    inline val bottomAppBar get() = BottomAppBarStyles(ctx)

    /**
     * See [BottomNavigationView] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/BottomNavigationView.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView).
     */
    inline val bottomNavigationView get() = BottomNavigationViewStyles(ctx)

    /**
     * See [MaterialButton] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/MaterialButton.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/button/MaterialButton).
     */
    inline val button get() = ButtonMaterialComponentsStyles(ctx)

    /**
     * See [Chip] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/Chip.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/chip/Chip).
     */
    inline val chip get() = ChipStyles(ctx)

    /**
     * See [TabLayout] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/TabLayout.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/tabs/TabLayout).
     */
    inline val tabLayout get() = TabLayoutStyles(ctx)

    /**
     * See [TextInputLayout] documentation on [GitHub](https://github.com/material-components/material-components-android/blob/master/docs/components/TextInputLayout.md)
     * and on [d.android.com website](https://developer.android.com/reference/com/google/android/material/textfield/TextInputLayout).
     */
    inline val textInputLayout get() = TextInputLayoutStyles(ctx)
}
