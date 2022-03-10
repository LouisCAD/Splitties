/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.preview.sound

import android.net.Uri
import androidx.annotation.RawRes
import androidx.core.net.toUri
import splitties.init.appCtx

fun uriOf(@RawRes resId: Int): Uri = "android.resource://${appCtx.packageName}/$resId".toUri()
