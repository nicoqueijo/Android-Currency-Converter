package com.nicoqueijo.android.core.extensions

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * TODO: Delete if not used
 */
fun Context.vibrate(duration: Long = 10L) {
    val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(
        VibrationEffect.createOneShot(
            duration,
            VibrationEffect.DEFAULT_AMPLITUDE
        )
    )
}