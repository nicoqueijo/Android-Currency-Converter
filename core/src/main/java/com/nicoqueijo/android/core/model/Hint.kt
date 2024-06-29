package com.nicoqueijo.android.core.model

/**
 * TODO: Kdoc this
 * number is used for conversion. No grouping separators and uses . as decimal separator for mathematical computations.
 * formatted number has grouping and decimal separators according to locale
 */
data class Hint(
    val number: String = "1",
    val formattedNumber: String = "1"
)