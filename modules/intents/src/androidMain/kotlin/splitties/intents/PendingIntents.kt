/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("NOTHING_TO_INLINE")

package splitties.intents

import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import splitties.init.appCtx

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 * @param options are ignored below API 16.
 */
fun Intent.toPendingActivity(
    reqCode: Int = 0,
    flags: Int = 0,
    options: Bundle? = null
): PendingIntent = if (SDK_INT >= 16) {
    PendingIntent.getActivity(appCtx, reqCode, this, flags, options)
} else PendingIntent.getActivity(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 * @param options are ignored below API 16.
 */
fun Array<Intent>.toPendingActivities(
    reqCode: Int = 0,
    flags: Int = 0,
    options: Bundle? = null
): PendingIntent = if (SDK_INT >= 16) {
    PendingIntent.getActivities(appCtx, reqCode, this, flags, options)
} else PendingIntent.getActivities(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
fun Intent.toPendingForegroundService(
    reqCode: Int = 0,
    flags: Int = 0
): PendingIntent = if (SDK_INT >= 26) {
    PendingIntent.getForegroundService(appCtx, reqCode, this, flags)
} else toPendingService(reqCode, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingService(
    reqCode: Int = 0,
    flags: Int = 0
): PendingIntent = PendingIntent.getService(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingBroadcast(
    reqCode: Int = 0,
    flags: Int = 0
): PendingIntent = PendingIntent.getBroadcast(appCtx, reqCode, this, flags)
