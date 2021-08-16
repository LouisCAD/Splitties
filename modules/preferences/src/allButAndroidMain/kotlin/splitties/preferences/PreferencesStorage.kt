/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import splitties.experimental.NonSymmetricalApi

actual interface PreferencesStorage {

    /**
     * Retrieve all values from the preferences.
     *
     *
     * Note that you *must not* modify the collection returned
     * by this method, or alter any of its contents.  The consistency of your
     * stored data is not guaranteed if you do.
     *
     * @return Returns a map containing a list of pairs key/value representing
     * the preferences.
     *
     * @throws NullPointerException
     */
    actual fun getAll(): Map<String, *>

    /**
     * Retrieve a String value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a String.
     *
     * @throws ClassCastException
     */
    actual fun getString(key: String, defValue: String?): String?

    /**
     * Retrieve a set of String values from the preferences.
     *
     *
     * Note that you *must not* modify the set instance returned
     * by this call.  The consistency of the stored data is not guaranteed
     * if you do, nor is your ability to modify the instance at all.
     *
     * @param key The name of the preference to retrieve.
     * @param defValues Values to return if this preference does not exist.
     *
     * @return Returns the preference values if they exist, or defValues.
     * Throws ClassCastException if there is a preference with this name
     * that is not a Set.
     *
     * @throws ClassCastException
     */
    actual fun getStringSet(
        key: String,
        defValues: Set<String?>?
    ): Set<String?>?

    /**
     * Retrieve an int value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * an int.
     *
     * @throws ClassCastException
     */
    actual fun getInt(key: String, defValue: Int): Int

    /**
     * Retrieve a long value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a long.
     *
     * @throws ClassCastException
     */
    actual fun getLong(key: String, defValue: Long): Long

    /**
     * Retrieve a float value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a float.
     *
     * @throws ClassCastException
     */
    actual fun getFloat(key: String, defValue: Float): Float

    /**
     * Retrieve a boolean value from the preferences.
     *
     * @param key The name of the preference to retrieve.
     * @param defValue Value to return if this preference does not exist.
     *
     * @return Returns the preference value if it exists, or defValue.  Throws
     * ClassCastException if there is a preference with this name that is not
     * a boolean.
     *
     * @throws ClassCastException
     */
    actual fun getBoolean(key: String, defValue: Boolean): Boolean

    /**
     * Checks whether the preferences contains a preference.
     *
     * @param key The name of the preference to check.
     * @return Returns true if the preference exists in the preferences,
     * otherwise false.
     */
    actual operator fun contains(key: String): Boolean

    /**
     * Create a new SharedPreferencesEditor for these preferences, through which you can make
     * modifications to the data in the preferences and atomically commit those
     * changes back to the SharedPreferences object.
     *
     *
     * Note that you *must* call [PreferencesEditor.commit] to have any
     * changes you perform in the SharedPreferencesEditor actually show up in the
     * SharedPreferences.
     *
     * @return Returns a new instance of the [PreferencesEditor] interface, allowing
     * you to modify the values in this SharedPreferences object.
     */
    actual fun edit(): PreferencesEditor

    /**
     * Registers a callback to be invoked when a change happens to a preference.
     *
     *
     * **Caution:** The preference manager does
     * not currently store a strong reference to the listener. You must store a
     * strong reference to the listener, or it will be susceptible to garbage
     * collection. We recommend you keep a reference to the listener in the
     * instance data of an object that will exist as long as you need the
     * listener.
     *
     * @param listener The callback that will run.
     * @see .unregisterOnSharedPreferenceChangeListener
     */
    @NonSymmetricalApi
    actual fun registerOnSharedPreferenceChangeListener(listener: OnPreferenceChangeListener)

    /**
     * Unregisters a previous callback.
     *
     * @param listener The callback that should be unregistered.
     * @see .registerOnSharedPreferenceChangeListener
     */
    @NonSymmetricalApi
    actual fun unregisterOnSharedPreferenceChangeListener(listener: OnPreferenceChangeListener)

}

/**
 * Interface definition for a callback to be invoked when a shared
 * preference is changed.
 */
actual interface OnPreferenceChangeListener {
    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     *
     *
     * This callback will be run on your main thread.
     *
     * @param preferencesStorage The [PreferencesStorage] that received
     * the change.
     * @param key The key of the preference that was changed, added, or
     * removed.
     */
    @NonSymmetricalApi
    actual fun onSharedPreferenceChanged(
        preferencesStorage: PreferencesStorage,
        key: String
    )
}

/**
 * Interface used for modifying values in a [PreferencesStorage]
 * object.  All changes you make in an editor are batched, and not copied
 * back to the original [PreferencesStorage] until you call [.commit]
 * or [.apply]
 */
actual interface PreferencesEditor {
    /**
     * Set a String value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.  Passing `null`
     * for this argument is equivalent to calling [.remove] with
     * this key.
     *
     * @return Returns a reference to the same Editor object, so you can
     * chain put calls together.
     */
    actual fun putString(
        key: String,
        value: String?
    ): PreferencesEditor

    /**
     * Set a set of String values in the preferences editor, to be written
     * back once [.commit] or [.apply] is called.
     *
     * @param key The name of the preference to modify.
     * @param values The set of new values for the preference.  Passing `null`
     * for this argument is equivalent to calling [.remove] with
     * this key.
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun putStringSet(
        key: String,
        values: Set<String?>?
    ): PreferencesEditor

    /**
     * Set an int value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun putInt(
        key: String,
        value: Int
    ): PreferencesEditor

    /**
     * Set a long value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun putLong(
        key: String,
        value: Long
    ): PreferencesEditor

    /**
     * Set a float value in the preferences editor, to be written back once
     * [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun putFloat(
        key: String,
        value: Float
    ): PreferencesEditor

    /**
     * Set a boolean value in the preferences editor, to be written back
     * once [.commit] or [.apply] are called.
     *
     * @param key The name of the preference to modify.
     * @param value The new value for the preference.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun putBoolean(
        key: String,
        value: Boolean
    ): PreferencesEditor

    /**
     * Mark in the editor that a preference value should be removed, which
     * will be done in the actual preferences once [.commit] is
     * called.
     *
     *
     * Note that when committing back to the preferences, all removals
     * are done first, regardless of whether you called remove before
     * or after put methods on this editor.
     *
     * @param key The name of the preference to remove.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun remove(key: String): PreferencesEditor

    /**
     * Mark in the editor to remove *all* values from the
     * preferences.  Once commit is called, the only remaining preferences
     * will be any that you have defined in this editor.
     *
     *
     * Note that when committing back to the preferences, the clear
     * is done first, regardless of whether you called clear before
     * or after put methods on this editor.
     *
     * @return Returns a reference to the same SharedPreferencesEditor object, so you can
     * chain put calls together.
     */
    actual fun clear(): PreferencesEditor

    /**
     * Commit your preferences changes back from this SharedPreferencesEditor to the
     * [PreferencesStorage] object it is editing.  This atomically
     * performs the requested modifications, replacing whatever is currently
     * in the SharedPreferences.
     *
     *
     * Note that when two editors are modifying preferences at the same
     * time, the last one to call commit wins.
     *
     *
     * If you don't care about the return value and you're
     * using this from your application's main thread, consider
     * using [.apply] instead.
     *
     * @return Returns true if the new values were successfully written
     * to persistent storage.
     */
    actual fun commit(): Boolean

    /**
     * Commit your preferences changes back from this SharedPreferencesEditor to the
     * [PreferencesStorage] object it is editing.  This atomically
     * performs the requested modifications, replacing whatever is currently
     * in the SharedPreferences.
     *
     *
     * Note that when two editors are modifying preferences at the same
     * time, the last one to call apply wins.
     *
     *
     * Unlike [.commit], which writes its preferences out
     * to persistent storage synchronously, [.apply]
     * commits its changes to the in-memory
     * [PreferencesStorage] immediately but starts an
     * asynchronous commit to disk and you won't be notified of
     * any failures.  If another editor on this
     * [PreferencesStorage] does a regular [.commit]
     * while a [.apply] is still outstanding, the
     * [.commit] will block until all async commits are
     * completed as well as the commit itself.
     *
     *
     * As [PreferencesStorage] instances are singletons within
     * a process, it's safe to replace any instance of [.commit] with
     * [.apply] if you were already ignoring the return value.
     *
     *
     * You don't need to worry about Android component
     * lifecycles and their interaction with `apply()`
     * writing to disk.  The framework makes sure in-flight disk
     * writes from `apply()` complete before switching
     * states.
     *
     *
     * The [PreferencesEditor] interface
     * isn't expected to be implemented directly.  However, if you
     * previously did implement it and are now getting errors
     * about missing `apply()`, you can simply call
     * [.commit] from `apply()`.
     */
    actual fun apply()

}
