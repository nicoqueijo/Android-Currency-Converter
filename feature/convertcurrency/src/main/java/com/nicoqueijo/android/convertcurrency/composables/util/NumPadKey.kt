package com.nicoqueijo.android.convertcurrency.composables.util

/**
 * Enum class that represents keys on the number pad. Each key is associated with a character value
 * that is displayed on the corresponding key of the keyboard.
 */
enum class NumPadKey(var value: Char) {
    ONE(value = '1'),
    TWO(value = '2'),
    THREE(value = '3'),
    FOUR(value = '4'),
    FIVE(value = '5'),
    SIX(value = '6'),
    SEVEN(value = '7'),
    EIGHT(value = '8'),
    NINE(value = '9'),
    DECIMAL_SEPARATOR(value = '.'),
    ZERO(value = '0'),
    BACKSPACE(value = '⌫')
}
