/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.preferences

import kotlinx.coroutines.flow.*
import splitties.preferences.DataStorePreferences
import splitties.preferences.DataStorePreferencesPreview
import splitties.preferences.SuspendPrefsAccessor

@OptIn(DataStorePreferencesPreview::class)
class SamplePreferences private constructor() : DataStorePreferences(name = "sample") {
    val enableAnnoyingFeaturesField = boolPref("annoying_features", defaultValue = true)
    var enableAnnoyingFeatures by enableAnnoyingFeaturesField
    val showAnnoyingPopupAtLaunchField = boolPref("annoying_popup_launch", defaultValue = true)
    var showAnnoyingPopupAtLaunch by showAnnoyingPopupAtLaunchField
    val showAnnoyingPopupInLoopField = boolPref("annoying_popup_loop", defaultValue = false)
    var showAnnoyingPopupInLoop by showAnnoyingPopupInLoopField

    val someFlow: Flow<Boolean>

    var somePrefField by boolPref("somePref", defaultValue = false).also {
        someFlow = it.valueFlow()
    }

    companion object : SuspendPrefsAccessor<SamplePreferences>(::SamplePreferences)
}
