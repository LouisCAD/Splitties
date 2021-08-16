/*
 * Copyright 2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

@DataStorePreferencesPreview
abstract class DataStorePreferences(prefs: PreferencesStorage) : Preferences(prefs) {

    constructor(
        name: String?
    ) : this(
        getDataStoreBackedSharedPreferences(name = name)
    )
}
