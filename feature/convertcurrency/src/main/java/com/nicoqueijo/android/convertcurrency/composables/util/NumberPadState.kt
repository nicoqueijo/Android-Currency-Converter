package com.nicoqueijo.android.convertcurrency.composables.util

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import java.util.Locale

/**
 * Data class representing the state for the Number Pad.
 *
 * This class holds the locale for localization of the decimal separator, and the event handlers
 * for processing keyboard input when a button is clicked or long-clicked.
 *
 * @param locale The locale used to localize the decimal separator.
 * @param onKeyboardButtonClick Lambda function to handle keyboard button click events.
 * @param onKeyboardButtonLongClick Lambda function to handle keyboard button long-click events.
 */
data class NumberPadState(
    val locale: Locale,
    val onKeyboardButtonClick: ((KeyboardInput) -> Unit)? = null,
    val onKeyboardButtonLongClick: ((KeyboardInput) -> Unit)? = null,
)