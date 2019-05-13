/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")
@file:UseExperimental(InternalSplittiesApi::class)

package splitties.preferences

import android.content.Context
import splitties.experimental.InternalSplittiesApi

abstract class Preferences(
    name: String,
    availableAtDirectBoot: Boolean = false,
    mode: Int = Context.MODE_PRIVATE
) : PreferencesBase(
    name = name,
    availableAtDirectBoot = availableAtDirectBoot,
    mode = mode
) {

    protected inline fun boolPref(
        key: String,
        defaultValue: Boolean
    ) = BoolPref(this, key = key, defaultValue = defaultValue)

    protected inline fun intPref(
        key: String,
        defaultValue: Int
    ) = IntPref(this, key = key, defaultValue = defaultValue)

    protected inline fun floatPref(
        key: String,
        defaultValue: Float
    ) = FloatPref(this, key = key, defaultValue = defaultValue)

    protected inline fun longPref(
        key: String,
        defaultValue: Long
    ) = LongPref(this, key = key, defaultValue = defaultValue)

    protected inline fun stringPref(
        key: String,
        defaultValue: String
    ) = StringPref(this, key = key, defaultValue = defaultValue)

    protected inline fun stringOrNullPref(
        key: String,
        defaultValue: String?
    ) = StringOrNullPref(this, key = key, defaultValue = defaultValue)

    protected inline fun stringSetPref(
        key: String,
        defaultValue: Set<String> = emptySet()
    ) = StringSetPref(this, key = key, defaultValue = defaultValue)

    protected inline fun stringSetOrNullPref(
        key: String,
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun BoolPref(
        key: String,
        defaultValue: Boolean
    ) = BoolPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun IntPref(
        key: String,
        defaultValue: Int
    ) = IntPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun FloatPref(
        key: String,
        defaultValue: Float
    ) = FloatPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun LongPref(
        key: String,
        defaultValue: Long
    ) = LongPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun StringPref(
        key: String,
        defaultValue: String
    ) = StringPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun StringOrNullPref(
        key: String,
        defaultValue: String?
    ) = StringOrNullPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun StringSetPref(
        key: String,
        defaultValue: Set<String> = emptySet()
    ) = StringSetPref(this, key = key, defaultValue = defaultValue)

    @Suppress("FunctionName")
    protected inline fun StringSetOrNullPref(
        key: String,
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPref(this, key = key, defaultValue = defaultValue)

    @DangerousRenaming
    protected inline fun boolPref(defaultValue: Boolean) = BoolPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun intPref(defaultValue: Int) = IntPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun floatPref(defaultValue: Float) = FloatPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun longPref(defaultValue: Long) = LongPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun stringPref(defaultValue: String) = StringPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun stringOrNullPref(defaultValue: String?) = StringOrNullPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun stringSetPref(defaultValue: Set<String> = emptySet()) = StringSetPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    @DangerousRenaming
    protected inline fun stringSetOrNullPref(
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPref(
        this,
        key = null,
        defaultValue = defaultValue
    )

    companion object
}
