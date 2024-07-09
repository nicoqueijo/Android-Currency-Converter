package com.nicoqueijo.android.core.extensions

/**
 * Extension function for [Long] that converts milliseconds to seconds.
 *
 * @return The value in seconds.
 */
fun Long.toSeconds() = this / 1_000L

/**
 * Extension function for [Long] that converts seconds to milliseconds.
 *
 * @return The value in milliseconds.
 */
fun Long.toMillis() = this * 1_000L