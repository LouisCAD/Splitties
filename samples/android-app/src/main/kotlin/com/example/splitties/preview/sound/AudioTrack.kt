/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("EXPERIMENTAL_API_USAGE")

package com.example.splitties.preview.sound

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.invoke
import kotlinx.coroutines.withContext
import kotlin.experimental.and
import kotlin.math.PI
import kotlin.math.sin

private const val defaultSamplingRate = 8000

fun generateTone(
    freqHz: Double = 440.0,
    durationMs: Int = 250,
    streamType: Int = AudioManager.STREAM_NOTIFICATION
): AudioTrack {
    val count = (44100.0 * 2.0 * (durationMs / 1000.0)).toInt() and 1.inv()
    val samples = ShortArray(count)
    for (i in 0 until count step 2) {
        val sample = (sin(2 * PI * i / (44100.0 / freqHz)) * 0x7FFF).toInt().toShort()
        samples[i] = sample
        samples[i + 1] = sample
    }
    val track = audioTrack(
        streamType = streamType,
        sampleRateInHz = 44100,
        channelConfig = AudioFormat.CHANNEL_OUT_STEREO,
        audioFormat = AudioFormat.ENCODING_PCM_16BIT,
        bufferSizeInBytes = count * (Short.SIZE_BITS / 8),
        mode = AudioTrack.MODE_STATIC
    )
    track.write(samples, 0, count)
    track.notificationMarkerPosition = samples.size / 2
    return track
}

private fun generateTone(
    frequency: Double = 440.0,
    durationInMillis: Int = 250,
    amplitude: Double = 1.0,
    samplingRate: Int = defaultSamplingRate
): ByteArray {
    require(amplitude in 0.0..1.0)
    val samplesCount = (durationInMillis * samplingRate) / 1000
    val samples = DoubleArray(samplesCount)
    val generatedSound = ByteArray(2 * samplesCount)
    // fill out the array
    for (i in 0 until samplesCount) {
        samples[i] = sin(2.0 * PI * i.toDouble() / (samplingRate / frequency))
    }

    val amplitude16bits = Short.MAX_VALUE * amplitude

    // convert to 16 bit pcm sound array
    // assumes the sample buffer is normalised.
    var idx = 0
    for (dVal in samples) {
        // scale to maximum amplitude
        val value = (dVal * amplitude16bits).toInt().toShort()
        // in 16 bit wav PCM, first byte is the low order byte
        generatedSound[idx++] = (value and 0x00ff).toByte()
        generatedSound[idx++] = ((value.toInt() and 0xff00) ushr 8).toByte()
    }
    return generatedSound
}

private suspend fun playSound(sound: ByteArray, samplingRate: Int) {
    val audioTrack = audioTrack(
        streamType = AudioManager.STREAM_MUSIC,
        sampleRateInHz = samplingRate,
        channelConfig = AudioFormat.CHANNEL_OUT_MONO,
        audioFormat = AudioFormat.ENCODING_PCM_16BIT,
        bufferSizeInBytes = sound.size / 2,
        mode = AudioTrack.MODE_STATIC
    )
    audioTrack.write(sound, 0, sound.size)
    audioTrack.notificationMarkerPosition = sound.size / 4
    val playback = AudioTrackPlaybackPositionUpdateListener(audioTrack)
    audioTrack.setVolume(0f)
    audioTrack.play()
    try {
        audioTrack.raiseVolumeSmoothly()
        //audioTrack.setVolume(1/99f)
        playback.awaitMarker()
        audioTrack.stop()
        audioTrack.playbackHeadPosition = 0
    } catch (e: Exception) {
        withContext(NonCancellable) {
            audioTrack.fadeVolumeDown()
        }
        throw e
    } finally {
        audioTrack.release()
    }
}

private suspend fun AudioTrack.raiseVolumeSmoothly(
    fadeDurationMillis: Long = 50,
    totalSteps: Int = fadeDurationMillis.toInt()
) {
    val targetVolume = AudioTrack.getMaxVolume()
    for (step in 1..totalSteps) {
        setVolume((targetVolume * step / (totalSteps)).also {
            println("Raising: $it")
        })
        delay(fadeDurationMillis / totalSteps)
    }
}

private suspend fun AudioTrack.fadeVolumeDown(
    fadeDurationMillis: Long = 200,
    totalSteps: Int = fadeDurationMillis.toInt()
) {
    val maxVolume = AudioTrack.getMaxVolume()
    for (step in totalSteps downTo 1) {
        setVolume((maxVolume * step / (totalSteps - 1)).also {
            println("Fading down: $it")
        })
        delay(fadeDurationMillis / totalSteps)
    }
}

suspend fun playDiapason(durationInMillis: Int = 1000) = Dispatchers.Default {
    val sound = generateTone(frequency = 440.0, durationInMillis = durationInMillis)
    playSound(sound, defaultSamplingRate)
}

@Suppress("DEPRECATION")
private fun audioTrack(
    streamType: Int,
    sampleRateInHz: Int,
    channelConfig: Int,
    audioFormat: Int,
    bufferSizeInBytes: Int,
    mode: Int
): AudioTrack = AudioTrack(
    streamType,
    sampleRateInHz,
    channelConfig,
    audioFormat,
    bufferSizeInBytes,
    mode
)
