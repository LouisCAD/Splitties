/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.demo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * @see DemoAdapter.DemoViewHolder
 */
class DemoItem(
    @StringRes titleResId: Int,
    @StringRes detailResId: Int,
    @DrawableRes iconResId: Int
) : ImmutableBasicItem(titleResId, detailResId, iconResId)
