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

package splitties.views.dsl.core

import android.app.Activity
import android.view.View
import kotlin.DeprecationLevel.HIDDEN

inline var Activity.contentView: View
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setContentView(value)

@Suppress("NOTHING_TO_INLINE")
inline fun Activity.setContentView(ui: Ui) = setContentView(ui.root)
