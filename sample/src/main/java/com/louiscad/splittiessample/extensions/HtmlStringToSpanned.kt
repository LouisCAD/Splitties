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
package com.louiscad.splittiessample.extensions

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.text.Html
import android.text.Spanned

/**
 * Returns displayable styled text from the provided HTML string. Any `<img>` tags in the
 * HTML will use the specified ImageGetter to request a representation of the image (use null
 * if you don't want this) and the specified TagHandler to handle unknown tags (specify null if
 * you don't want this).
 *
 * This uses TagSoup to handle real HTML, including all of the brokenness found in the wild.
 *
 * @param flags **Ignored on pre-Nougat devices**. Look for `Html.FROM_HTML_*` constants.
 */
fun String.fromHtml(
    flags: Int = 0,
    imageGetter: Html.ImageGetter? = null,
    tagHandler: Html.TagHandler? = null
): Spanned = if (SDK_INT >= N) Html.fromHtml(this, flags, imageGetter, tagHandler)
else @Suppress("DEPRECATION") Html.fromHtml(this, imageGetter, tagHandler)
