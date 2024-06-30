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
        val focusedCurrency = currenciesCopy.single { it.isFocused }
        var existingText = focusedCurrency.conversion.valueAsString
        var isInputValid = true
        when (keyboardInput) {
            is KeyboardInput.Number -> {
                val digitPressed = keyboardInput.digit.value
                var input = existingText + digitPressed
                input = cleanInput(input)
                isInputValid = isInputValid(
                    input,
                    focusedCurrency
                )
            }

            KeyboardInput.DecimalSeparator -> {
                var input = "$existingText."
                input = cleanInput(input)
                isInputValid = isInputValid(
                    input,
                    focusedCurrency
                )
            }

            KeyboardInput.Backspace -> {
                existingText = existingText.dropLast(1)
                focusedCurrency.conversion.valueAsString = existingText
            }
        }
        focusedCurrency.isInputValid = isInputValid

        return InputResult(
            currencies = currenciesCopy,
            isInputValid = isInputValid,
        )
    }

    /**
     * If input has a leading decimal separator it prefixes it with a zero.
     * If input has a leading 0 it removes it.
     * Examples:   "." -> "0."
     *            "00" -> "0"
     *            "07" -> "0"
     */
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

    /**
     * Assures the whole part of the input is not above 20 digits long.
     */
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

    /**
     * Assures the decimal part of the input is at most 4 digits.
     */
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

    /**
     * Assures the input contains at most 1 decimal separator.
     */
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