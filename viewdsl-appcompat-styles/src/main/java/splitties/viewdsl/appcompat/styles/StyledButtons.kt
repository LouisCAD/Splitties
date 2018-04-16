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

@file:Suppress("NOTHING_TO_INLINE")

package splitties.viewdsl.appcompat.styles

import android.content.Context
import android.widget.Button
import android.widget.ImageButton
import splitties.views.inflate

inline fun coloredButton(ctx: Context): Button = ctx.inflate(R.layout.button_colored)
inline fun flatButton(ctx: Context): Button = ctx.inflate(R.layout.button_borderless)
inline fun coloredFlatButton(ctx: Context): Button = ctx.inflate(R.layout.button_borderless_colored)
inline fun imgActionButton(ctx: Context): ImageButton = ctx.inflate(R.layout.image_button_action)
