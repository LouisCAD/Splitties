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

package splitties.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.widget.ImageView
import kotlin.DeprecationLevel.HIDDEN

/**
 * Sets a drawable resource as the content of this ImageView.
 *
 * **This does Bitmap reading and decoding on the UI thread, which can cause a latency hiccup.**
 * If that's a concern, consider using [imageDrawable] or [imageBitmap] and [BitmapFactory] instead.
 */
inline var ImageView.imageResource: Int
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(@DrawableRes value) = setImageResource(value)

/**
 * Sets a drawable as the content of this ImageView.
 * A null value will clear the content.
 */
inline var ImageView.imageDrawable: Drawable?
    get() = drawable
    set(value) = setImageDrawable(value)

/**
 * Sets a Bitmap as the content of this ImageView.
 * @see BitmapFactory
 */
inline var ImageView.imageBitmap: Bitmap
    @Deprecated(NO_GETTER, level = HIDDEN) get() = noGetter
    set(value) = setImageBitmap(value)
