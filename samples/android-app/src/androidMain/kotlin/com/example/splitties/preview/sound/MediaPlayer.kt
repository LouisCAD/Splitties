/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.preview.sound

import android.media.MediaPlayer
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun MediaPlayer.awaitPreparation(): Unit = try {
    suspendCancellableCoroutine { continuation ->
        setOnPreparedListener { continuation.resume(Unit) }
        prepareAsync()
    }
} finally {
    setOnPreparedListener(null)
}

suspend fun MediaPlayer.playAndComplete(): Unit = try {
    suspendCancellableCoroutine { continuation ->
        setOnCompletionListener { continuation.resume(Unit) }
        start()
    }
} finally {
    setOnCompletionListener(null)
}
