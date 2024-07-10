package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

/**
 * Use case to update the focused currency in a list of currencies.
 *
 * This class processes a list of currencies, unfocuses the currently focused currency,
 * and sets the specified currency as the new focused currency.
 */
class UpdateFocusedCurrencyUseCase {

    /**
     * Updates the focused currency in the provided list of currencies.
     *
     * This method performs the following steps:
     * 1. Creates a deep copy of the provided list of currencies.
     * 2. Unfocuses the currently focused currency.
     * 3. Sets the specified currency as the new focused currency.
     * 4. Returns the updated list of currencies.
     *
     * @param currencies The list of currencies to be processed and updated.
     * @param currencyToFocus The currency to be set as the new focused currency.
     * @return A new list of currencies with the updated focused currency.
     */
    operator fun invoke(
        currencies: List<Currency>,
        currencyToFocus: Currency,
    ): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        currenciesCopy.apply {
            first { currency ->
                currency.isFocused
            }.isFocused = false
            first { currency ->
                currency.currencyCode == currencyToFocus.currencyCode
            }.isFocused = true
        }
        return currenciesCopy
    }
}