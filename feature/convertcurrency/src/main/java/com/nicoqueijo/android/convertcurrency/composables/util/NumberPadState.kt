package com.nicoqueijo.android.convertcurrency.composables.util

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import java.util.Locale

data class NumberPadState(
    val locale: Locale,
    val onKeyboardButtonClick: ((KeyboardInput) -> Unit)? = null,
)