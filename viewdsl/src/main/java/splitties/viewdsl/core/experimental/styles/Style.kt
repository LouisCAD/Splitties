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
package splitties.viewdsl.core.experimental.styles

import android.content.Context
import android.view.View
import splitties.viewdsl.core.ViewFactory
import splitties.viewdsl.core.experimental.ViewInstantiator

interface Style<in V : View>

interface MutatingStyle<V : View> : Style<V> {
    fun V.applyStyle()
}

/** These styles bypass all [ViewInstantiator]s installed in the [ViewFactory]. */
interface InstantiatingStyle<V : View> : Style<V> {
    fun instantiateStyledView(context: Context): V
}
