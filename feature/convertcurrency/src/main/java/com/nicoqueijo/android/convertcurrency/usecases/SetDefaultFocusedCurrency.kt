package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class SetDefaultFocusedCurrency {

    operator fun invoke(selectedCurrencies: List<Currency>) {
        if (selectedCurrencies.count { it.isFocused } == 0) {
            selectedCurrencies.firstOrNull()?.apply {
                isFocused = true
            }
        }
    }
}