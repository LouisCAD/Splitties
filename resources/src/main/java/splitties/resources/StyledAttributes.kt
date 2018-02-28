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

package splitties.resources

import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.AttrRes
import splitties.uithread.isUiThread

private val uiThreadConfinedCachedAttrArray = IntArray(1)
private val cachedAttrArray = IntArray(1)

inline fun <T> Context.withStyledAttributes(
        @AttrRes attrRes: Int,
        func: TypedArray.(firstIndex: Int) -> T
): T = obtainStyledAttr(attrRes).let { styledAttrs ->
    styledAttrs.func(styledAttrs.getIndex(0)).also { styledAttrs.recycle() }
}

@PublishedApi
internal fun Context.obtainStyledAttr(@AttrRes attrRes: Int): TypedArray = if (isUiThread) {
    uiThreadConfinedCachedAttrArray[0] = attrRes
    obtainStyledAttributes(uiThreadConfinedCachedAttrArray)
} else synchronized(cachedAttrArray) {
    cachedAttrArray[0] = attrRes
    obtainStyledAttributes(cachedAttrArray)
}
