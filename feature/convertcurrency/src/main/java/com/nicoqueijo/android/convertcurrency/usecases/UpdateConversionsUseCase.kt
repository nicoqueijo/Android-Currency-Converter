package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.CurrencyConverter
import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import java.math.BigDecimal

/**
 * Use case to update the conversion values for a list of currencies based on the focused currency.
 *
 * This class processes a list of currencies, identifies the focused currency, and updates
 * the conversion values for the other currencies relative to the focused currency's exchange rate.
 */
class UpdateConversionsUseCase {

    /**
     * Updates the conversion values for the provided list of currencies.
     *
     * This method performs the following steps:
     * 1. Creates a deep copy of the provided list of currencies.
     * 2. Identifies the focused currency in the list.
     * 3. Iterates over the other currencies and calculates their conversion values based on the focused currency's exchange rate.
     * 4. Returns the updated list of currencies with the new conversion values.
     *
     * @param currencies The list of currencies to be processed and updated.
     * @return A new list of currencies with updated conversion values.
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
        currenciesCopy.filter { currency ->
            currency.currencyCode != focusedCurrency.currencyCode
        }.forEach { currency ->
            val fromRate = focusedCurrency.exchangeRate
            val toRate = currency.exchangeRate
            if (focusedCurrency.conversion.valueAsString.isNotEmpty()) {
                val conversionValue = CurrencyConverter.convertCurrency(
                    amount = BigDecimal(focusedCurrency.conversion.valueAsString),
                    fromRate = fromRate,
                    toRate = toRate
                )
                currency.conversion.value = conversionValue
            } else {
                currency.conversion.valueAsString = ""
            }
        }
        return currenciesCopy
    }
}