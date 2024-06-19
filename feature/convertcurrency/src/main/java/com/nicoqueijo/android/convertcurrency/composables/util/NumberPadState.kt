package com.nicoqueijo.android.convertcurrency.composables.util

import com.nicoqueijo.android.convertcurrency.composables.NumberPad
import com.nicoqueijo.android.convertcurrency.composables.util.Digit

/**
 * UI State object to be used by [NumberPad] Composable.
 * @property onDigitButtonClick The event to be executed among the click of a digit button.
 * @property onDecimalSeparatorButtonClick The event to be executed among the click of the decimal
 * separator button.
 * @property onBackspaceButtonClick The event to be executed among the click of the backspace button.
 */
data class NumberPadState(
    val onDigitButtonClick: ((Digit) -> Unit)? = null,
    val onDecimalSeparatorButtonClick: (() -> Unit)? = null,
    val onBackspaceButtonClick: (() -> Unit)? = null,
)