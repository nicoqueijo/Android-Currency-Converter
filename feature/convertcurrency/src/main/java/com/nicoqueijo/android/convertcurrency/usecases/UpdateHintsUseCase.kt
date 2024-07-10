package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.CurrencyConverter
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.extensions.roundToFourDecimalPlaces
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Hint
import java.math.BigDecimal

/**
 * Use case to update the conversion hints for a list of currencies based on the focused currency.
 *
 * This class processes a list of currencies, identifies the focused currency, and updates
 * the conversion hints for all currencies relative to the focused currency's exchange rate.
 */
class UpdateHintsUseCase {

    /**
     * Updates the conversion hints for the provided list of currencies.
     *
     * This method performs the following steps:
     * 1. Creates a deep copy of the provided list of currencies.
     * 2. Identifies the focused currency in the list.
     * 3. Sets the hint for the focused currency to "1".
     * 4. Iterates over the other currencies and calculates their conversion hints based on the focused currency's exchange rate.
     * 5. Returns the updated list of currencies with the new conversion hints.
     *
     * @param currencies The list of currencies to be processed and updated.
     * @return A new list of currencies with updated conversion hints.
     */
    operator fun invoke(
        currencies: List<Currency>,
    ): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        if (currenciesCopy.isEmpty()) { // No currencies to process
            return currenciesCopy
        }
        val focusedCurrency = currenciesCopy.single { currency ->
            currency.isFocused
        }
        focusedCurrency.conversion.hint = Hint(number = "1")
        currenciesCopy.filter { currency ->
            currency.currencyCode != focusedCurrency.currencyCode
        }.forEach { currency ->
            val fromRate = focusedCurrency.exchangeRate
            val toRate = currency.exchangeRate
            val conversionValue = CurrencyConverter.convertCurrency(
                amount = BigDecimal("1"),
                fromRate = fromRate,
                toRate = toRate
            ).roundToFourDecimalPlaces().toString()
            currency.conversion.hint = Hint(number = conversionValue)
        }
        return currenciesCopy
    }
}