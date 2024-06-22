package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class SetDefaultFocusedCurrency {

    operator fun invoke(
        focusedCurrency: Currency?,
        selectedCurrencies: List<Currency>
    ): Currency? {
        return focusedCurrency ?: selectedCurrencies.firstOrNull()?.apply {
            isFocused = true
        }
    }
}