package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.extensions.deepCopy

class SetDefaultFocusedCurrency {

    operator fun invoke(currencies: List<Currency>): List<Currency> {
        val currencyCopy = currencies.deepCopy()
        if (currencyCopy.count { it.isFocused } == 0) {
            currencyCopy.firstOrNull()?.apply {
                isFocused = true
            }
        }
        return currencyCopy
    }
}