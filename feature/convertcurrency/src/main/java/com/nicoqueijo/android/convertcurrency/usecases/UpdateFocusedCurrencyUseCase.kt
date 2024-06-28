package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.extensions.deepCopy

class UpdateFocusedCurrencyUseCase {

    operator fun invoke(
        currencies: List<Currency>,
        currencyToFocus: Currency,
    ): List<Currency> {
        val currenciesCopy = currencies.deepCopy()
        currenciesCopy.apply {
            first { it.isFocused }.isFocused = false
            first { it.currencyCode == currencyToFocus.currencyCode }.isFocused = true
        }
        return currenciesCopy
    }
}