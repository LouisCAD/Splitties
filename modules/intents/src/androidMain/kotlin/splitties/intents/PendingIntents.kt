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
fun Intent.toPendingActivity(
    reqCode: Int = 0,
    flags: Int = 0,
    options: Bundle? = null,
    mutable: Boolean = false
): PendingIntent {
    val actualFlags = flags.withMutability(mutable)
    return if (SDK_INT >= 16) {
        PendingIntent.getActivity(appCtx, reqCode, this, actualFlags, options)
    } else PendingIntent.getActivity(appCtx, reqCode, this, actualFlags)
}

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 * @param options are ignored below API 16.
 */
fun Array<Intent>.toPendingActivities(
    reqCode: Int = 0,
    flags: Int = 0,
    options: Bundle? = null,
    mutable: Boolean = false
): PendingIntent {
    val actualFlags = flags.withMutability(mutable)
    return if (SDK_INT >= 16) {
        PendingIntent.getActivities(appCtx, reqCode, this, actualFlags, options)
    } else PendingIntent.getActivities(appCtx, reqCode, this, actualFlags)
}

@PublishedApi
internal fun Int.withMutability(isMutable: Boolean): Int {
    @Suppress("InlinedApi")
    val mutabilityFlag = if (isMutable) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_IMMUTABLE
    return this.withFlag(mutabilityFlag)
}

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
fun Intent.toPendingForegroundService(
    reqCode: Int = 0,
    flags: Int = 0,
    mutable: Boolean = false
): PendingIntent = if (SDK_INT >= 26) {
    PendingIntent.getForegroundService(appCtx, reqCode, this, flags.withMutability(mutable))
} else toPendingService(reqCode, flags, mutable)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingService(
    reqCode: Int = 0,
    flags: Int = 0,
    mutable: Boolean = false
): PendingIntent {
    return PendingIntent.getService(appCtx, reqCode, this, flags.withMutability(mutable))
}

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
inline fun Intent.toPendingBroadcast(
    reqCode: Int = 0,
    flags: Int = 0,
    mutable: Boolean = false
): PendingIntent {
    return PendingIntent.getBroadcast(appCtx, reqCode, this, flags.withMutability(mutable))
}
