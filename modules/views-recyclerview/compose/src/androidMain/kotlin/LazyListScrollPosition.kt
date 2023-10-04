/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.recyclerview.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue

internal class LazyListScrollPosition(
    initialIndex: Int = 0,
    initialScrollOffset: Int = 0
) {
    var index by mutableIntStateOf(initialIndex)

    var scrollOffset by mutableIntStateOf(initialScrollOffset)
}
