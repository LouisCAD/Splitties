/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.extensions

import android.os.Build.VERSION.SDK_INT
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
): Spanned = if (SDK_INT >= 24) Html.fromHtml(this, flags, imageGetter, tagHandler)
else @Suppress("DEPRECATION") Html.fromHtml(this, imageGetter, tagHandler)
