/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package com.example.splitties.preview.vibrator

import android.Manifest
import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.os.Build.VERSION.SDK_INT
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import android.os.VibrationEffect as VibrationEffectApi26

sealed class VibrationEffect {
    companion object {

        /** The default vibration strength of the device. */
        @SuppressLint("InlinedApi")
        const val DEFAULT_AMPLITUDE = VibrationEffectApi26.DEFAULT_AMPLITUDE

        /**
         * Creates a waveform vibration that is **pre-Oreo compatible**.
         *
         * Waveform vibrations are a potentially repeating series of timing and amplitude pairs. For
         * each pair, the value in the amplitude array determines the strength of the vibration and
         * the value in the timing array determines how long it vibrates for.
         *
         * *Even indexes are off durations, and odd indexes are on durations. Therefore,* **the
         * first value is the time to wait before turning the vibrator on.**
         * An amplitude of 0
         * implies no vibration (i.e. off), and any pairs with a timing value of 0 will be ignored.
         *
         * To cause the pattern to repeat, pass the index into the timings array at which to start
         * the repetition, or -1 to disable repeating.
         *
         * @param timings The timing values of the timing / amplitude pairs in milliseconds.
         * Even indexes are off durations, and odd indexes are on durations. Therefore, **the first
         * value is the time to wait before turning the vibrator on.** It will usually be 0. Timing
         * values of 0 will cause the pair to be ignored.
         * @param amplitudes The amplitude values of the timing / amplitude pairs, **ignored on
         * pre-Oreo devices**. Amplitude values must be between 0 and 255, or equal to
         * [VibrationEffect.DEFAULT_AMPLITUDE]. An amplitude value of 0 implies the motor is
         * off.
         * @param repeat The index into the timings array at which to repeat, or -1 if you you don't
         * want to repeat.
         */
        fun createWaveform(
            timings: LongArray,
            amplitudes: IntArray? = null,
            repeat: Int = -1
        ): VibrationEffect = WaveForm(timings, amplitudes, repeat)

        /**
         * Creates a one shot vibration that is **pre-Oreo compatible**.
         *
         * One shot vibrations will vibrate constantly for the specified period of time at the
         * specified amplitude, and then stop.
         *
         * @param milliseconds The number of milliseconds to vibrate. Must be a positive number.
         * @param amplitude The strength of the vibration, **ignored on pre-Oreo devices**. Must
         * be a value between 1 and 255, or [VibrationEffect.DEFAULT_AMPLITUDE].
         */
        fun createOneShot(
            milliseconds: Long,
            amplitude: Int = DEFAULT_AMPLITUDE
        ): VibrationEffect = OneShot(milliseconds, amplitude)
    }
}

@RequiresApi(21)
@RequiresPermission(Manifest.permission.VIBRATE)
fun Vibrator.vibrate(vibe: VibrationEffect, audioAttributes: AudioAttributes) {
    @Suppress("DEPRECATION")
    if (SDK_INT >= 26) vibrate(vibe.toPlatformVibe(), audioAttributes)
    else when (vibe) {
        is WaveForm -> vibrate(vibe.timings, vibe.repeat, audioAttributes)
        is OneShot -> vibrate(vibe.milliseconds, audioAttributes)
    }
}

@RequiresPermission(Manifest.permission.VIBRATE)
fun Vibrator.vibrate(vibe: VibrationEffect) {
    @Suppress("DEPRECATION")
    if (SDK_INT >= 26) vibrate(vibe.toPlatformVibe())
    else when (vibe) {
        is WaveForm -> vibrate(vibe.timings, vibe.repeat)
        is OneShot -> vibrate(vibe.milliseconds)
    }
}

private class WaveForm(
    val timings: LongArray,
    val amplitudes: IntArray?,
    val repeat: Int
) : VibrationEffect()

private class OneShot(val milliseconds: Long, val amplitude: Int) : VibrationEffect()

@RequiresApi(26)
private fun VibrationEffect.toPlatformVibe(): VibrationEffectApi26 = when (this) {
    is WaveForm -> when (amplitudes) {
        null -> VibrationEffectApi26.createWaveform(timings, repeat)
        else -> VibrationEffectApi26.createWaveform(timings, amplitudes, repeat)
    }
    is OneShot -> VibrationEffectApi26.createOneShot(milliseconds, amplitude)
}

/**
 * Returns the duration of the vibration effect.
 * The repeat property of wave form patterns is ignored because those effects
 * are repeated until cancelled, with iterations count consequently unknown.
 */
fun VibrationEffect.durationMillis(): Long = when (this) {
    is WaveForm -> timings.sum()
    is OneShot -> milliseconds
}
