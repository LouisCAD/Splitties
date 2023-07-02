/*
 * Copyright 2019-2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
@file:Suppress("nothing_to_inline")

package splitties.intents

import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import splitties.bitflags.withFlag
import splitties.init.appCtx

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 * @param options are ignored below API 16.
 */
inline fun Intent.toPendingActivity(
    reqCode: Int = 0,
    mutable: Boolean = false,
    oneShot: Boolean = false,
    cancelCurrent: Boolean = false,
    options: Bundle? = null,
    flags: Int = 0.withKnownFlags(mutable, oneShot, cancelCurrent)
): PendingIntent = if (SDK_INT >= 16) {
    PendingIntent.getActivity(appCtx, reqCode, this, flags, options)
} else PendingIntent.getActivity(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 * @param options are ignored below API 16.
 */
inline fun Array<Intent>.toPendingActivities(
    reqCode: Int = 0,
    mutable: Boolean = false,
    oneShot: Boolean = false,
    cancelCurrent: Boolean = false,
    options: Bundle? = null,
    flags: Int = 0.withKnownFlags(mutable, oneShot, cancelCurrent)
): PendingIntent = if (SDK_INT >= 16) {
    PendingIntent.getActivities(appCtx, reqCode, this, flags, options)
} else PendingIntent.getActivities(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
fun Intent.toPendingForegroundService(
    reqCode: Int = 0,
    mutable: Boolean = false,
    oneShot: Boolean = false,
    cancelCurrent: Boolean = false,
    flags: Int = 0.withKnownFlags(mutable, oneShot, cancelCurrent)
): PendingIntent = if (SDK_INT >= 26) {
    PendingIntent.getForegroundService(appCtx, reqCode, this, flags)
} else PendingIntent.getService(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingService(
    reqCode: Int = 0,
    mutable: Boolean = false,
    oneShot: Boolean = false,
    cancelCurrent: Boolean = false,
    flags: Int = 0.withKnownFlags(mutable, oneShot, cancelCurrent)
): PendingIntent = PendingIntent.getService(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingBroadcast(
    reqCode: Int = 0,
    mutable: Boolean = false,
    oneShot: Boolean = false,
    cancelCurrent: Boolean = false,
    flags: Int = 0.withKnownFlags(mutable, oneShot, cancelCurrent)
): PendingIntent = PendingIntent.getBroadcast(appCtx, reqCode, this, flags)

@PublishedApi
internal fun Int.withKnownFlags(
    isMutable: Boolean,
    isOneShot: Boolean,
    cancelCurrent: Boolean
): Int {
    var flags = this
    @Suppress("InlinedApi")
    val mutabilityFlag = if (isMutable) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_IMMUTABLE
    flags = flags.withFlag(mutabilityFlag)
    val newFlag = when {
        isOneShot -> PendingIntent.FLAG_ONE_SHOT
        cancelCurrent -> PendingIntent.FLAG_CANCEL_CURRENT
        else -> PendingIntent.FLAG_UPDATE_CURRENT
    }
    flags = flags.withFlag(newFlag)
    return flags
}
