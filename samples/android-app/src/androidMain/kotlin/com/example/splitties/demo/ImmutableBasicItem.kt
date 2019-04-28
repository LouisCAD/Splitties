/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.demo

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

/**
 * Designed for [RecyclerView] items that won't change title, detail and icon.
 */
open class ImmutableBasicItem(
    @StringRes val titleResId: Int,
    @StringRes val detailResId: Int,
    @DrawableRes val iconResId: Int
)
