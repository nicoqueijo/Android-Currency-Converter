package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

/**
 * Use case to ensure there is always a focused currency in a list of currencies. If none of the currencies
 * in the provided list are marked as focused, it sets the first currency in the list as focused.
 */
class SetDefaultFocusedCurrencyUseCase {

    /**
     * Sets a default focused currency among the provided list of currencies if none are focused.
     *
     * @param currencies The list of currencies to check and potentially modify.
     * @return A new list of currencies where at least one currency is focused.
     */
    operator fun invoke(currencies: List<Currency>): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        val hasNoFocusedCurrency = currenciesCopy.none { currency -> currency.isFocused }
        if (hasNoFocusedCurrency) {
            currenciesCopy.firstOrNull()?.apply {
                isFocused = true
            }
        }
        return currenciesCopy
    }
}