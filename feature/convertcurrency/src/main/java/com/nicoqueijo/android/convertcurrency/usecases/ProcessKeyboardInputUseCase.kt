package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.Currency

class ProcessKeyboardInputUseCase {

    operator fun invoke(
        keyboardInput: KeyboardInput,
        focusedCurrency: Currency?,
        selectedCurrencies: List<Currency>,
    ) {
        when (keyboardInput) {
            is KeyboardInput.Number -> {

            }

            KeyboardInput.Backspace -> {

            }

            KeyboardInput.DecimalSeparator -> {

            }
        }
    }

    private fun validate() {

    }
}