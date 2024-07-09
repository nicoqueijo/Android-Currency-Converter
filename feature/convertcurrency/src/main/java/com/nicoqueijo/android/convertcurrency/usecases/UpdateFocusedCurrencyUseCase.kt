package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

class UpdateFocusedCurrencyUseCase {

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