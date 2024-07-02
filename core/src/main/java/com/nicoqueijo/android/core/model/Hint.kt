package com.nicoqueijo.android.core.model

/**
 * [number] is used for conversion. Has no grouping separators and uses . as decimal separator for mathematical computations.
 * [formattedNumber] has grouping and decimal separators according to locale.
 */
data class Hint(
    val number: String = "1",
    val formattedNumber: String = "1"
)