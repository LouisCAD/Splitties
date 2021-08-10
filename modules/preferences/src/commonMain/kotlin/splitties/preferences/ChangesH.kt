/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import kotlinx.coroutines.flow.Flow

internal expect fun PreferencesStorage.changesFlow(
    key: String,
    emitAfterRegister: Boolean = false
): Flow<Unit>
