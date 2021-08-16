/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import splitties.init.appCtx
import splitties.init.directBootCtx

internal actual fun getPreferencesStorage(
    name: String?,
    androidAvailableAtDirectBoot: Boolean
): PreferencesStorage = getSharedPreferences(name, androidAvailableAtDirectBoot)

internal fun getSharedPreferences(
    name: String?,
    androidAvailableAtDirectBoot: Boolean
): PreferencesStorage {
    val actualName = name ?: "${appCtx.packageName}_preferences"
    val storageCtx: Context = if (androidAvailableAtDirectBoot && SDK_INT > 24) {
        // Moving the sharedPreferences from is done by the system only if you had it outside
        // the direct boot available storage or if the device was running Android M or older,
        // and just got updated.
        directBootCtx.moveSharedPreferencesFrom(appCtx, actualName)
        directBootCtx
    } else appCtx
    return storageCtx.getSharedPreferences(actualName, Context.MODE_PRIVATE)
}

actual typealias PreferencesStorage = android.content.SharedPreferences
actual typealias OnPreferenceChangeListener = android.content.SharedPreferences.OnSharedPreferenceChangeListener
actual typealias PreferencesEditor = android.content.SharedPreferences.Editor
