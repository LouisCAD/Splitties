/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views

import android.widget.EditText
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
inline var EditText.type: InputType<*>
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(newType) {
        inputType = newType.value
    }
