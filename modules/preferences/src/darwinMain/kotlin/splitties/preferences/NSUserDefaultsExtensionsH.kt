/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.preferences

import platform.Foundation.NSUserDefaults

internal expect fun NSUserDefaults.setLong(value: Long, forKey: String)
internal expect fun NSUserDefaults.setInt(value: Int, forKey: String)

internal expect fun NSUserDefaults.longForKey(key: String): Long
internal expect fun NSUserDefaults.intForKey(key: String): Int
