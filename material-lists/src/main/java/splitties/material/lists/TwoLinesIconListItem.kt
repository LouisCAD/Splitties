/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.material.lists

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import splitties.selectableviews.constraintlayout.SelectableConstraintLayout
import splitties.views.inflateAndAttach

@Deprecated(
        message = "Use IconTwoLinesListItem instead. It is more efficient and " +
                "fixes bugs on long first lines",
        replaceWith = ReplaceWith(
                expression = "IconTwoLinesListItem",
                imports = ["splitties.material.lists.IconTwoLinesListItem"]
        )
)
class TwoLinesIconListItem @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : SelectableConstraintLayout(context, attrs, defStyleAttr) {

    init {
        inflateAndAttach(R.layout.content_list_item_two_lines_icon)
    }

    val icon: ImageView = findViewById(R.id.icon)
    val firstLine: TextView = findViewById(R.id.firstLine)
    val secondLine: TextView = findViewById(R.id.secondLine)
}
