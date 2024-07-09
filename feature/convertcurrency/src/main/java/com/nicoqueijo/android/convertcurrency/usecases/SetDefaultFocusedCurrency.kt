package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

class SetDefaultFocusedCurrency {

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