package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.convertcurrency.util.KeyboardInput
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.CurrencyConverter
import com.nicoqueijo.android.core.Position
import java.math.BigDecimal
import javax.inject.Inject

class ProcessKeyboardInputUseCase @Inject constructor(/*val context: Context*/) {

    operator fun invoke(
        keyboardInput: KeyboardInput,
        selectedCurrencies: List<Currency>,
    ): List<Currency> {
        val focusedCurrency = selectedCurrencies.single { it.isFocused }
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

        if (isInputValid) {
            runConversions(
                focusedCurrency = focusedCurrency,
                selectedCurrencies = selectedCurrencies,
            )
        } else {
            vibrateAndShake()
        }
        return selectedCurrencies.map { it.deepCopy() }

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
        val isLengthValid = validateLength(input = input, focusedCurrency = focusedCurrency)
        val isDecimalValid = validateDecimalPlaces(input = input, focusedCurrency = focusedCurrency)
        val isDecimalSeparatorValid =
            validateDecimalSeparator(input = input, focusedCurrency = focusedCurrency)
        return isLengthValid && isDecimalValid && isDecimalSeparatorValid
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

    private fun runConversions(
        focusedCurrency: Currency?,
        selectedCurrencies: List<Currency>,
    ) {

        selectedCurrencies.single { currency ->
            currency.currencyCode == focusedCurrency?.currencyCode
        }.conversion.valueAsString = focusedCurrency?.conversion?.valueAsString ?: ""


        selectedCurrencies.filter { currency ->
            currency.currencyCode != focusedCurrency?.currencyCode
        }.forEach {
            val fromRate = focusedCurrency!!.exchangeRate
            val toRate = it.exchangeRate
            if (focusedCurrency.conversion.valueAsString.isNotEmpty()) {
                val conversionValue = CurrencyConverter.convertCurrency(
                    amount = BigDecimal(focusedCurrency.conversion.valueAsString),
                    fromRate = fromRate,
                    toRate = toRate
                )
                it.conversion.value = conversionValue
            } else {
                it.conversion.valueAsString = ""
            }
        }
    }

    private fun vibrateAndShake() {
        /**
         * Animation has to be done in compose so maybe I should do the vibration there as well with
         * the haptic feedback.
         */
    }
}
