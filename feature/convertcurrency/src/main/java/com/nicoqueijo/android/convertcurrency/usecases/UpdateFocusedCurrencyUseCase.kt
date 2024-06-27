package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class UpdateFocusedCurrencyUseCase {

    operator fun invoke(
        selectedCurrencies: List<Currency>,
        currencyToFocus: Currency,
    ) {
        selectedCurrencies.apply {
            first { it.isFocused }.isFocused = false
            first { it.currencyCode == currencyToFocus.currencyCode }.isFocused = true
        }
    }
}