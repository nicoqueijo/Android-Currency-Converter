package com.nicoqueijo.android.core.model

/**
 * Data class representing a hint with a number and its formatted version.
 *
 * @property number The number used for conversions. Has no grouping separators and uses . as the decimal
 * separator for mathematical computations.
 * @property formattedNumber The formatted version of the number with grouping and decimal separators
 * according to locale.
 */
data class Hint(
    val number: String = "1",
    val formattedNumber: String = "1"
)