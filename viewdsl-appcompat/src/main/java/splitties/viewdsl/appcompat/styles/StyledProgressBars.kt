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
import android.widget.ProgressBar
import splitties.viewdsl.core.styles.AndroidStyles
import splitties.viewdsl.core.styles.invoke

private const val deprecationMessage = "Use AndroidStyles properties with invoke operator instead"
@Deprecated(deprecationMessage)
inline fun progressBar(ctx: Context): ProgressBar = AndroidStyles.progressBar.default(ctx)

@Deprecated(deprecationMessage)
inline fun largeProgressBar(ctx: Context) = AndroidStyles.progressBar.large(ctx)

@Deprecated(deprecationMessage)
inline fun horizontalProgressBar(ctx: Context): ProgressBar = AndroidStyles.progressBar.horizontal(ctx)
