@file:Suppress("NOTHING_TO_INLINE")

package splitties.resources

import android.app.Fragment
import android.content.Context
import android.support.annotation.DimenRes
import android.view.View
import splitties.init.appCtx
import android.support.v4.app.Fragment as SupportFragment

inline fun Context.dimen(@DimenRes dimenResId: Int): Float = resources.getDimension(dimenResId)
inline fun SupportFragment.dimen(@DimenRes dimenResId: Int) = context!!.dimen(dimenResId)
inline fun Fragment.dimen(@DimenRes dimenResId: Int) = activity.dimen(dimenResId)
inline fun View.dimen(@DimenRes dimenResId: Int) = context.dimen(dimenResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appDimen(@DimenRes dimenResId: Int) = appCtx.dimen(dimenResId)

inline fun Context.dimenPxSize(@DimenRes dimenResId: Int): Int = resources.getDimensionPixelSize(dimenResId)
inline fun SupportFragment.dimenPxSize(@DimenRes dimenResId: Int) = context!!.dimenPxSize(dimenResId)
inline fun Fragment.dimenPxSize(@DimenRes dimenResId: Int) = activity.dimenPxSize(dimenResId)
inline fun View.dimenPxSize(@DimenRes dimenResId: Int) = context.dimenPxSize(dimenResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appDimenPxSize(@DimenRes dimenResId: Int) = appCtx.dimenPxSize(dimenResId)

inline fun Context.dimenPxOffset(@DimenRes dimenResId: Int): Int = resources.getDimensionPixelOffset(dimenResId)
inline fun SupportFragment.dimenPxOffset(@DimenRes dimenResId: Int) = context!!.dimenPxOffset(dimenResId)
inline fun Fragment.dimenPxOffset(@DimenRes dimenResId: Int) = activity.dimenPxOffset(dimenResId)
inline fun View.dimenPxOffset(@DimenRes dimenResId: Int) = context.dimenPxOffset(dimenResId)
/** Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet). */
inline fun appDimenPxOffset(@DimenRes dimenResId: Int) = appCtx.dimenPxOffset(dimenResId)
