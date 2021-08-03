/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")
@file:OptIn(InternalSplittiesApi::class)

package splitties.resources

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.util.TypedValue
import android.view.View
import androidx.annotation.ArrayRes
import androidx.annotation.AttrRes
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import splitties.experimental.InternalSplittiesApi
import splitties.init.appCtx

inline fun Context.txt(@StringRes stringResId: Int): CharSequence = resources.getText(stringResId)
inline fun Fragment.txt(@StringRes stringResId: Int) = context!!.txt(stringResId)
inline fun View.txt(@StringRes stringResId: Int) = context.txt(stringResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appTxt(@StringRes stringResId: Int) = appCtx.txt(stringResId)

inline fun Context.str(
    @StringRes stringResId: Int,
    vararg formatArgs: Any?
): String = resources.getString(stringResId, *formatArgs)

inline fun Fragment.str(
    @StringRes stringResId: Int,
    vararg formatArgs: Any?
) = context!!.str(stringResId, *formatArgs)

inline fun View.str(
    @StringRes stringResId: Int,
    vararg formatArgs: Any?
) = context.str(stringResId, *formatArgs)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStr(
    @StringRes stringResId: Int,
    vararg formatArgs: Any?
) = appCtx.str(stringResId, *formatArgs)

inline fun Context.str(@StringRes stringResId: Int): String = resources.getString(stringResId)
inline fun Fragment.str(@StringRes stringResId: Int) = context!!.str(stringResId)
inline fun View.str(@StringRes stringResId: Int) = context.str(stringResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStr(@StringRes stringResId: Int) = appCtx.str(stringResId)

inline fun Context.qtyTxt(@PluralsRes stringResId: Int, quantity: Int): CharSequence {
    return resources.getQuantityText(stringResId, quantity)
}

inline fun Fragment.qtyTxt(
    @PluralsRes stringResId: Int,
    quantity: Int
) = context!!.qtyTxt(stringResId, quantity)

inline fun View.qtyTxt(
    @PluralsRes stringResId: Int,
    quantity: Int
) = context.qtyTxt(stringResId, quantity)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appQtyTxt(
    @PluralsRes stringResId: Int,
    quantity: Int
) = appCtx.qtyTxt(stringResId, quantity)

inline fun Context.qtyStr(@PluralsRes stringResId: Int, quantity: Int): String {
    return resources.getQuantityString(stringResId, quantity)
}

inline fun Fragment.qtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int
) = context!!.qtyStr(stringResId, quantity)

inline fun View.qtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int
) = context.qtyStr(stringResId, quantity)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appQtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int
) = appCtx.qtyStr(stringResId, quantity)

inline fun Context.qtyStr(
    @PluralsRes stringResId: Int, quantity: Int,
    vararg formatArgs: Any?
): String = resources.getQuantityString(stringResId, quantity, *formatArgs)

inline fun Fragment.qtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int,
    vararg formatArgs: Any?
) = context!!.qtyStr(stringResId, quantity, *formatArgs)

inline fun View.qtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int,
    vararg formatArgs: Any?
) = context.qtyStr(stringResId, quantity, *formatArgs)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appQtyStr(
    @PluralsRes stringResId: Int,
    quantity: Int,
    vararg formatArgs: Any?
) = appCtx.qtyStr(stringResId, quantity, *formatArgs)

inline fun Context.txtArray(
    @ArrayRes stringResId: Int
): Array<out CharSequence> = resources.getTextArray(stringResId)

inline fun Fragment.txtArray(@ArrayRes stringResId: Int) = context!!.txtArray(stringResId)
inline fun View.txtArray(@ArrayRes stringResId: Int) = context.txtArray(stringResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appTxtArray(@ArrayRes stringResId: Int) = appCtx.txtArray(stringResId)

inline fun Context.strArray(
    @ArrayRes stringResId: Int
): Array<String> = resources.getStringArray(stringResId)

inline fun Fragment.strArray(@ArrayRes stringResId: Int) = context!!.strArray(stringResId)
inline fun View.strArray(@ArrayRes stringResId: Int) = context.strArray(stringResId)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStrArray(@ArrayRes stringResId: Int) = appCtx.strArray(stringResId)

private fun TypedValue.checkOfStringType() {
    require(type == TypedValue.TYPE_STRING) {
        unexpectedThemeAttributeTypeErrorMessage(expectedKind = "string")
    }
}

// Styled resources below

fun Context.styledTxt(@AttrRes attr: Int): CharSequence = withResolvedThemeAttribute(attr) {
    checkOfStringType()
    string
}

inline fun Fragment.styledTxt(@AttrRes attr: Int) = context!!.styledTxt(attr)
inline fun View.styledTxt(@AttrRes attr: Int) = context.styledTxt(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledTxt(@AttrRes attr: Int) = appCtx.styledTxt(attr)


fun Context.styledStr(@AttrRes attr: Int): String = withResolvedThemeAttribute(attr) {
    checkOfStringType()
    string.toString()
}

inline fun Fragment.styledStr(@AttrRes attr: Int) = context!!.styledStr(attr)
inline fun View.styledStr(@AttrRes attr: Int) = context.styledStr(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledStr(@AttrRes attr: Int) = appCtx.styledStr(attr)

fun Context.styledStr(
    @AttrRes attr: Int,
    vararg formatArgs: Any?
): String = withResolvedThemeAttribute(attr) {
    checkOfStringType()
    val locale = resources.configuration.let {
        if (SDK_INT >= 24) it.locales[0] else @Suppress("deprecation") it.locale
    }
    String.format(locale, string.toString(), *formatArgs)
}

inline fun Fragment.styledStr(
    @AttrRes attr: Int,
    vararg formatArgs: Any?
) = context!!.styledStr(attr, *formatArgs)

inline fun View.styledStr(
    @AttrRes attr: Int,
    vararg formatArgs: Any?
) = context.styledStr(attr, *formatArgs)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledStr(
    @AttrRes attr: Int,
    vararg formatArgs: Any?
) = appCtx.styledStr(attr, *formatArgs)

fun Context.styledTxtArray(
    @AttrRes attr: Int
): Array<out CharSequence> = txtArray(resolveThemeAttribute(attr))

inline fun Fragment.styledTxtArray(@AttrRes attr: Int) = context!!.styledTxtArray(attr)
inline fun View.styledTxtArray(@AttrRes attr: Int) = context.styledTxtArray(attr)
/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
inline fun appStyledTxtArray(@AttrRes attr: Int) = appCtx.styledTxtArray(attr)
