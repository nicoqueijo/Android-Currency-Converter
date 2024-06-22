package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class UpdateFocusedCurrencyUseCase {

    operator fun invoke(
        selectedCurrencies: List<Currency>,
        currencyToFocus: Currency,
    ): Currency {
        selectedCurrencies.first { it.isFocused }.isFocused = false
        selectedCurrencies.first { it == currencyToFocus }.isFocused = true
        return currencyToFocus
    }
}