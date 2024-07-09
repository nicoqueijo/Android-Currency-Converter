package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position

class ProcessKeyboardInputUseCase {

    operator fun invoke(
        keyboardInput: KeyboardInput,
        currencies: List<Currency>,
    ): InputResult {
        val currenciesCopy = currencies.deepCopy()
        if (currenciesCopy.isEmpty()) { // No currencies to process
            return InputResult()
        }
        val focusedCurrency = currenciesCopy.single { currency ->
            currency.isFocused
        }
        var existingText = focusedCurrency.conversion.valueAsString
        var isInputValid = true
        when (keyboardInput) {
            is KeyboardInput.Number -> {
                val digitPressed = keyboardInput.digit.value
                var input = existingText + digitPressed
                input = cleanInput(input)
                isInputValid = isInputValid(
                    input = input,
                    focusedCurrency = focusedCurrency
                )
            }

            KeyboardInput.DecimalSeparator -> {
                var input = "$existingText."
                input = cleanInput(input)
                isInputValid = isInputValid(
                    input = input,
                    focusedCurrency = focusedCurrency
                )
            }

            is KeyboardInput.Backspace -> {
                existingText = if (keyboardInput.isLongClick) "" else existingText.dropLast(1)
                focusedCurrency.conversion.valueAsString = existingText
            }
        }
        focusedCurrency.isInputValid = isInputValid
        return InputResult(
            currencies = currenciesCopy,
            isInputValid = isInputValid,
        )
    }

    private fun cleanInput(input: String): String {
        var cleanInput = input
        when {
            "." == cleanInput -> cleanInput = "0."
            Regex("0[^.]").matches(cleanInput) -> cleanInput =
                input[Position.SECOND.value].toString()
        }
        return cleanInput
    }

    private fun isInputValid(
        input: String,
        focusedCurrency: Currency?,
    ): Boolean {
        return validateLength(input, focusedCurrency) &&
                validateDecimalPlaces(input, focusedCurrency) &&
                validateDecimalSeparator(input = input, focusedCurrency)
    }

    private fun validateLength(
        input: String,
        focusedCurrency: Currency?,
    ): Boolean {
        val maxDigitsAllowed = 20
        if (!input.contains(".") && input.length > maxDigitsAllowed) {
            focusedCurrency?.conversion?.valueAsString = input.dropLast(1)
            return false
        }
        focusedCurrency?.conversion?.valueAsString = input
        return true
    }

    private fun validateDecimalPlaces(
        input: String,
        focusedCurrency: Currency?,
    ): Boolean {
        val maxDecimalPlacesAllowed = 4
        if (input.contains(".") &&
            input.substring(input.indexOf(".") + 1).length > maxDecimalPlacesAllowed
        ) {
            focusedCurrency?.conversion?.valueAsString = input.dropLast(1)
            return false
        }
        focusedCurrency?.conversion?.valueAsString = input
        return true
    }

    private fun validateDecimalSeparator(
        input: String,
        focusedCurrency: Currency?,
    ): Boolean {
        val decimalSeparatorCount = input.asSequence()
            .count { it == '.' }
        if (decimalSeparatorCount > 1) {
            focusedCurrency?.conversion?.valueAsString = input.dropLast(1)
            return false
        }
        focusedCurrency?.conversion?.valueAsString = input
        return true
    }
}

data class InputResult(
    val currencies: List<Currency> = emptyList(),
    val isInputValid: Boolean = true,
)