package com.nicoqueijo.android.core.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun BigDecimal.roundToFourDecimalPlaces(): BigDecimal = setScale(4, RoundingMode.HALF_DOWN)