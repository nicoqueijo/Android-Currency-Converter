package com.nicoqueijo.android.convertcurrency.util

import com.nicoqueijo.android.convertcurrency.composables.util.Digit

/**
 * Represents different types of keyboard inputs.
 */
sealed class KeyboardInput {
    /**
     * Represents a numerical input.
     *
     * @property digit The digit that was input.
     */
    class Number(val digit: Digit) : KeyboardInput()

    /**
     * Represents the input of a decimal separator.
     */
    data object DecimalSeparator : KeyboardInput()

    /**
     * Represents a backspace input.
     *
     * @property isLongClick Indicates if the backspace was a long click.
     */
    class Backspace(val isLongClick: Boolean = false) : KeyboardInput()
}
