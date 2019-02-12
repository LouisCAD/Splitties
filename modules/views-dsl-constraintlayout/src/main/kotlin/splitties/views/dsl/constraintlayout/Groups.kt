package splitties.views.dsl.constraintlayout

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import splitties.views.dsl.core.add
import splitties.views.dsl.core.view
import splitties.views.dsl.core.wrapContent
import splitties.views.existingOrNewId

fun ConstraintLayout.group(vararg views: View): Group = add(view(::Group) {
    referencedIds = IntArray(views.size) { views[it].existingOrNewId }
}, lParams(wrapContent, wrapContent))
