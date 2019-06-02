/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress(
    "NOTHING_TO_INLINE", "SameParameterValue", "unused",
    "DeprecatedCallableAddReplaceWith"
)
@file:UseExperimental(InternalSplittiesApi::class)

package splitties.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import splitties.experimental.InternalSplittiesApi
import splitties.init.appCtx
import splitties.init.directBootCtx
import kotlin.reflect.KProperty

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

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun boolPref(defaultValue: Boolean) = BoolPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun intPref(defaultValue: Int) = IntPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun floatPref(defaultValue: Float) = FloatPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun longPref(defaultValue: Long) = LongPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun stringPref(defaultValue: String) = StringPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun stringOrNullPref(
        defaultValue: String?
    ) = StringOrNullPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun stringSetPref(
        defaultValue: Set<String> = emptySet()
    ) = StringSetPrefProvider(defaultValue)

    @Suppress("DEPRECATION")
    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected fun stringSetOrNullPref(
        defaultValue: Set<String>? = null
    ) = StringSetOrNullPrefProvider(defaultValue)

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class BoolPrefProvider internal constructor(private val defaultValue: Boolean) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): BoolPref {
            return BoolPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class IntPrefProvider internal constructor(private val defaultValue: Int) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): IntPref {
            return IntPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class FloatPrefProvider internal constructor(private val defaultValue: Float) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): FloatPref {
            return FloatPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class LongPrefProvider internal constructor(private val defaultValue: Long) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): LongPref {
            return LongPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class StringPrefProvider internal constructor(
        private val defaultValue: String
    ) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): StringPref {
            return StringPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class StringOrNullPrefProvider internal constructor(
        private val defaultValue: String?
    ) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): StringOrNullPref {
            return StringOrNullPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class StringSetPrefProvider internal constructor(
        private val defaultValue: Set<String>
    ) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): StringSetPref {
            return StringSetPref(property.name, defaultValue)
        }
    }

    @Deprecated(IMPLICIT_KEY_DEPRECATION, level = DeprecationLevel.WARNING)
    protected inner class StringSetOrNullPrefProvider internal constructor(
        private val defaultValue: Set<String>?
    ) {
        operator fun provideDelegate(thisRef: Any?, property: KProperty<*>): StringSetOrNullPref {
            return StringSetOrNullPref(property.name, defaultValue)
        }
    }

    companion object {
        private const val IMPLICIT_KEY_DEPRECATION = "Please specify the key explicitly. " +
                "The ability to implicitly use the property name as a key will be removed before " +
                "Splitties 3.0.0 after its deprecation level is raised to error in next version."
    }
}

@SuppressLint("CommitPrefEdits")
sealed class PreferencesBase(
    name: String,
    availableAtDirectBoot: Boolean = false,
    mode: Int = Context.MODE_PRIVATE
) {

    val prefs: SharedPreferences

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

    init {
        val storageCtx: Context = if (availableAtDirectBoot && SDK_INT > 24) {
            // Moving the sharedPreferences from is done by the system only if you had it outside
            // the direct boot available storage or if the device was running Android M or older,
            // and just got updated.
            directBootCtx.moveSharedPreferencesFrom(appCtx, name)
            directBootCtx
        } else appCtx
        prefs = storageCtx.getSharedPreferences(name, mode)
    }

    internal var editor: SharedPreferences.Editor by ResettableLazy { prefs.edit() }
        private set

    operator fun contains(o: Any) = prefs === o

    private var isEditing = false
    private var useCommitForEdit = false

    internal fun SharedPreferences.Editor.attemptApply() {
        if (isEditing) return
        apply()
    }
}
