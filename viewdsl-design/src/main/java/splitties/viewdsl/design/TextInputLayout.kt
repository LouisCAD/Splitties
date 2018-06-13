/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
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
package splitties.viewdsl.design

import android.support.annotation.IdRes
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import splitties.viewdsl.core.add
import splitties.viewdsl.core.matchParent
import splitties.viewdsl.core.v
import splitties.viewdsl.core.wrapContent
import android.widget.LinearLayout.LayoutParams as LP

inline fun TextInputLayout.addInput(
        @IdRes id: Int,
        initView: TextInputEditText.() -> Unit = {}
) = add(v(::TextInputEditText, id, initView = initView), LP(matchParent, wrapContent))
