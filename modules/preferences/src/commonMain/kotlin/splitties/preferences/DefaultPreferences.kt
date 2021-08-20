/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import splitties.experimental.ExperimentalSplittiesApi

/**
 * This should be overridden by only one object in an app.
 */
@ExperimentalSplittiesApi
abstract class DefaultPreferences(
    androidAvailableAtDirectBoot: Boolean = false
) : Preferences(
    androidAvailableAtDirectBoot = androidAvailableAtDirectBoot,
)
