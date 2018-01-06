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

import android.app.Fragment
import android.content.Context
import android.support.annotation.ArrayRes
import android.support.annotation.PluralsRes
import android.support.annotation.StringRes
import android.view.View
import splitties.init.appCtx
import android.support.v4.app.Fragment as SupportFragment

inline fun Context.txt(@StringRes stringResId: Int): CharSequence = resources.getText(stringResId)
inline fun SupportFragment.txt(@StringRes stringResId: Int) = context!!.txt(stringResId)
inline fun Fragment.txt(@StringRes stringResId: Int) = activity.txt(stringResId)
inline fun View.txt(@StringRes stringResId: Int) = context.txt(stringResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appTxt(@StringRes stringResId: Int) = appCtx.txt(stringResId)

inline fun Context.str(@StringRes stringResId: Int, vararg formatArgs: Any): String = resources.getString(stringResId, *formatArgs)
inline fun SupportFragment.str(@StringRes stringResId: Int, vararg formatArgs: Any) = context!!.str(stringResId, *formatArgs)
inline fun Fragment.str(@StringRes stringResId: Int, vararg formatArgs: Any) = activity.str(stringResId, *formatArgs)
inline fun View.str(@StringRes stringResId: Int, vararg formatArgs: Any) = context.str(stringResId, *formatArgs)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appStr(@StringRes stringResId: Int, vararg formatArgs: Any) = appCtx.str(stringResId, *formatArgs)

inline fun Context.str(@StringRes stringResId: Int): String = resources.getString(stringResId)
inline fun SupportFragment.str(@StringRes stringResId: Int) = context!!.str(stringResId)
inline fun Fragment.str(@StringRes stringResId: Int) = activity.str(stringResId)
inline fun View.str(@StringRes stringResId: Int) = context.str(stringResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appStr(@StringRes stringResId: Int) = appCtx.str(stringResId)

inline fun Context.qtyTxt(@PluralsRes stringResId: Int, quantity: Int): CharSequence {
    return resources.getQuantityText(stringResId, quantity)
}

inline fun SupportFragment.qtyTxt(@PluralsRes stringResId: Int, quantity: Int) = context!!.qtyTxt(stringResId, quantity)
inline fun Fragment.qtyTxt(@PluralsRes stringResId: Int, quantity: Int) = activity.qtyTxt(stringResId, quantity)
inline fun View.qtyTxt(@PluralsRes stringResId: Int, quantity: Int) = context.qtyTxt(stringResId, quantity)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appQtyTxt(@PluralsRes stringResId: Int, quantity: Int) = appCtx.qtyTxt(stringResId, quantity)

inline fun Context.qtyStr(@PluralsRes stringResId: Int, quantity: Int): String {
    return resources.getQuantityString(stringResId, quantity)
}

inline fun SupportFragment.qtyStr(@PluralsRes stringResId: Int, quantity: Int) = context!!.qtyStr(stringResId, quantity)
inline fun Fragment.qtyStr(@PluralsRes stringResId: Int, quantity: Int) = activity.qtyStr(stringResId, quantity)
inline fun View.qtyStr(@PluralsRes stringResId: Int, quantity: Int) = context.qtyStr(stringResId, quantity)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appQtyStr(@PluralsRes stringResId: Int, quantity: Int) = appCtx.qtyStr(stringResId, quantity)

inline fun Context.qtyStr(@PluralsRes stringResId: Int, quantity: Int, vararg formatArgs: Any): String {
    return resources.getQuantityString(stringResId, quantity, *formatArgs)
}

inline fun SupportFragment.qtyStr(@PluralsRes stringResId: Int, quantity: Int, vararg formatArgs: Any) = context!!.qtyStr(stringResId, quantity, *formatArgs)
inline fun Fragment.qtyStr(@PluralsRes stringResId: Int, quantity: Int, vararg formatArgs: Any) = activity.qtyStr(stringResId, quantity, *formatArgs)
inline fun View.qtyStr(@PluralsRes stringResId: Int, quantity: Int, vararg formatArgs: Any) = context.qtyStr(stringResId, quantity, *formatArgs)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appQtyStr(@PluralsRes stringResId: Int, quantity: Int, vararg formatArgs: Any) = appCtx.qtyStr(stringResId, quantity, *formatArgs)

inline fun Context.txtArray(@ArrayRes stringResId: Int): Array<out CharSequence> = resources.getTextArray(stringResId)
inline fun SupportFragment.txtArray(@ArrayRes stringResId: Int) = context!!.txtArray(stringResId)
inline fun Fragment.txtArray(@ArrayRes stringResId: Int) = activity.txtArray(stringResId)
inline fun View.txtArray(@ArrayRes stringResId: Int) = context.txtArray(stringResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appTxtArray(@ArrayRes stringResId: Int) = appCtx.txtArray(stringResId)

inline fun Context.strArray(@ArrayRes stringResId: Int): Array<String> = resources.getStringArray(stringResId)
inline fun SupportFragment.strArray(@ArrayRes stringResId: Int) = context!!.strArray(stringResId)
inline fun Fragment.strArray(@ArrayRes stringResId: Int) = activity.strArray(stringResId)
inline fun View.strArray(@ArrayRes stringResId: Int) = context.strArray(stringResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appStrArray(@ArrayRes stringResId: Int) = appCtx.strArray(stringResId)
