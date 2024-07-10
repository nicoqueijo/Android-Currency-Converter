package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position

/**
 * Use case to process keyboard input for currency conversion.
 *
 * This class processes user keyboard inputs, updates the conversion value for the focused currency,
 * and validates the input according to various rules.
 */
class ProcessKeyboardInputUseCase {

    /**
     * Processes the keyboard input and updates the focused currency's conversion value.
     *
     * This method performs the following steps:
     * 1. Creates a deep copy of the provided list of currencies.
     * 2. Identifies the focused currency in the list.
     * 3. Updates the conversion value based on the keyboard input.
     * 4. Validates the input according to length, decimal places, and decimal separator rules.
     * 5. Returns the updated list of currencies and the validation result.
     *
     * @param keyboardInput The keyboard input to be processed.
     * @param currencies The list of currencies to be updated.
     * @return An [InputResult] containing the updated list of currencies and the validation result.
     */
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

    /**
     * Cleans the input string by ensuring it adheres to correct numeric format. This includes handling
     * cases such as leading zeros and standalone decimal points.
     *
     * @param input The input string to be cleaned.
     * @return The cleaned input string.
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

    /**
     * Validates the input string to ensure it is a valid numeric input for the focused currency.
     * The validation checks for length, decimal places, and decimal separators.
     *
     * @param input The input string to be validated.
     * @param focusedCurrency The currently focused currency whose input is being validated.
     * @return `true` if the input is valid, `false` otherwise.
     */
    private fun isInputValid(
        input: String,
        focusedCurrency: Currency?,
    ): Boolean {
        return validateLength(input, focusedCurrency) &&
                validateDecimalPlaces(input, focusedCurrency) &&
                validateDecimalSeparator(input = input, focusedCurrency)
    }

    /**
     * Validates the length of the input string to ensure it does not exceed the maximum allowed length.
     *
     * @param input The input string to be validated.
     * @param focusedCurrency The currently focused currency whose input is being validated.
     * @return `true` if the input length is valid, `false` otherwise.
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
     * Validates the number of decimal places in the input string to ensure it does not exceed the maximum allowed.
     *
     * @param input The input string to be validated.
     * @param focusedCurrency The currently focused currency whose input is being validated.
     * @return `true` if the number of decimal places is valid, `false` otherwise.
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
     * Validates the input string to ensure it contains at most one decimal separator.
     *
     * @param input The input string to be validated.
     * @param focusedCurrency The currently focused currency whose input is being validated.
     * @return `true` if the number of decimal separators is valid, `false` otherwise.
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

/**
 * Data class representing the result of processing keyboard input.
 *
 * @property currencies The list of currencies with updated conversion values.
 * @property isInputValid Indicates whether the processed input is valid.
 */
data class InputResult(
    val currencies: List<Currency> = emptyList(),
    val isInputValid: Boolean = true,
)