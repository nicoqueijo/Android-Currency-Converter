package com.nicoqueijo.android.core.extensions

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Extension function for [BigDecimal] that rounds the value to four decimal places.
 *
 * @return A [BigDecimal] rounded to four decimal places using [RoundingMode.HALF_DOWN].
 */
fun BigDecimal.roundToFourDecimalPlaces(): BigDecimal = setScale(4, RoundingMode.HALF_DOWN)