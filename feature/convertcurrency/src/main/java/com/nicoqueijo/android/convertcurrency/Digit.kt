package com.nicoqueijo.android.convertcurrency

/**
 * Sealed class that allows the use of digits with safety. Using a [Digit] ensures that a number
 * between 0 and 9, inclusive, is being used.
 */
sealed class Digit(val value: Char) {
    data object Zero : Digit(value = '0')
    data object One : Digit(value = '1')
    data object Two : Digit(value = '2')
    data object Three : Digit(value = '3')
    data object Four : Digit(value = '4')
    data object Five : Digit(value = '5')
    data object Six : Digit(value = '6')
    data object Seven : Digit(value = '7')
    data object Eight : Digit(value = '8')
    data object Nine : Digit(value = '9')
}
