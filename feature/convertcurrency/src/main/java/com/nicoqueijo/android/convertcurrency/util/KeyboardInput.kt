package com.nicoqueijo.android.convertcurrency.util

import com.nicoqueijo.android.convertcurrency.composables.util.Digit

sealed class KeyboardInput {
    class Number(val digit: Digit) : KeyboardInput()
    data object DecimalSeparator : KeyboardInput()
    class Backspace(val isLongClick: Boolean = false) : KeyboardInput()
}