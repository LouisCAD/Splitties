package splitties.views

import android.widget.EditText
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
inline var EditText.type: InputType<*>
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(newType) {
        inputType = newType.value
    }
