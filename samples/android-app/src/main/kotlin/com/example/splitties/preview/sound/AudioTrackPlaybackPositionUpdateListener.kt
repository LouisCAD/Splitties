/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.preview.sound

import android.media.AudioTrack
import kotlinx.coroutines.channels.Channel

class AudioTrackPlaybackPositionUpdateListener(audioTrack: AudioTrack) {

    suspend fun awaitMarker() = markerChannel.receive()
    suspend fun awaitPeriodicNotification() = periodicNotificationChannel.receive()

    private val markerChannel = Channel<Unit>(Channel.UNLIMITED)
    private val periodicNotificationChannel = Channel<Unit>(Channel.UNLIMITED)

    private val listener = object : AudioTrack.OnPlaybackPositionUpdateListener {
        override fun onMarkerReached(track: AudioTrack) {
            markerChannel.trySend(Unit)
        }

        override fun onPeriodicNotification(track: AudioTrack) {
            periodicNotificationChannel.trySend(Unit)
        }
    }

    init {
        audioTrack.setPlaybackPositionUpdateListener(listener)
    }
}
