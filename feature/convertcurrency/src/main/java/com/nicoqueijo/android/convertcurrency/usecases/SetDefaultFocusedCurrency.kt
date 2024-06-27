package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.extensions.deepCopy

class SetDefaultFocusedCurrency {

    operator fun invoke(selectedCurrencies: List<Currency>): List<Currency> {
        val copy = selectedCurrencies.deepCopy()
        if (copy.count { it.isFocused } == 0) {
            copy.firstOrNull()?.apply {
                isFocused = true
            }
        }
        return copy
    }
}