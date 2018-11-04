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
@file:Suppress("NOTHING_TO_INLINE", "DeprecatedCallableAddReplaceWith")

package splitties.viewdsl.appcompat.styles

import android.content.Context
import android.widget.Button
import android.widget.ImageButton
import splitties.viewdsl.appcompat.AppCompatStyles
import splitties.viewdsl.core.styles.invoke

private const val deprecationMessage = "Use AppCompatStyles properties with invoke operator instead"

@Deprecated(deprecationMessage)
inline fun coloredButton(ctx: Context): Button = AppCompatStyles(ctx).button.colored(ctx)

@Deprecated(deprecationMessage)
inline fun flatButton(ctx: Context): Button = AppCompatStyles(ctx).button.flat(ctx)

@Deprecated(deprecationMessage)
inline fun coloredFlatButton(ctx: Context): Button = AppCompatStyles(ctx).button.flatColored(ctx)

@Deprecated(deprecationMessage)
inline fun imgActionButton(ctx: Context): ImageButton = AppCompatStyles(ctx).actionButton(ctx)
