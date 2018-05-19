/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("NOTHING_TO_INLINE")

package splitties.intents

import android.app.PendingIntent
import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.JELLY_BEAN
import android.os.Build.VERSION_CODES.O
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
): PendingIntent = if (SDK_INT >= JELLY_BEAN) {
    PendingIntent.getActivity(appCtx, reqCode, this, flags, options)
} else PendingIntent.getActivity(appCtx, reqCode, this, flags)

/**
 * @param reqCode Can be left to default (0) if this [PendingIntent] is unique as defined from
 * [Intent.filterEquals].
 */
fun Intent.toPendingForegroundService(
        reqCode: Int = 0,
        flags: Int = 0
): PendingIntent = if (SDK_INT >= O) {
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
