/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress(
    "NOTHING_TO_INLINE", "SameParameterValue", "unused",
    "DeprecatedCallableAddReplaceWith"
)

package splitties.preferences

import splitties.experimental.InternalSplittiesApi

abstract class Preferences(prefs: PreferencesStorage) : PreferencesBase(prefs) {

    constructor(
        name: String,
        androidAvailableAtDirectBoot: Boolean = false
    ) : this(
        getPreferencesStorage(
            name = name,
            androidAvailableAtDirectBoot = androidAvailableAtDirectBoot
        )
    )

    internal constructor(
        androidAvailableAtDirectBoot: Boolean
    ) : this(
        getPreferencesStorage(
            name = null,
            androidAvailableAtDirectBoot = androidAvailableAtDirectBoot
        )
    )

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
        defaultValue: Set<String?> = emptySet()
    ) = StringSetPref(this, key = key, defaultValue = defaultValue)

    protected inline fun stringSetOrNullPref(
        key: String,
        defaultValue: Set<String?>? = null
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
        defaultValue: String? = null
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

    companion object
}

sealed class PreferencesBase(val prefs: PreferencesStorage) {

    @InternalSplittiesApi
    fun beginEdit(blocking: Boolean = false) {
        useCommitForEdit = blocking
        isEditing = true
    }

    @InternalSplittiesApi
    fun endEdit() {
        if (useCommitForEdit) editor.commit() else editor.apply()
        isEditing = false
    }

    @InternalSplittiesApi
    fun abortEdit() {
        editor = editor // Invalidates the editor stored in the delegate
        isEditing = false
    }

    internal var editor: PreferencesEditor by ResettableLazy { prefs.edit() }
        private set

    operator fun contains(o: Any) = prefs === o

    private var isEditing = false
    private var useCommitForEdit = false

    internal fun PreferencesEditor.attemptApply() {
        if (isEditing) return
        apply()
    }
}
