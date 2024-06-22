package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class SetDefaultFocusedCurrency {

    operator fun invoke(
        focusedCurrency: Currency?,
        selectedCurrencies: List<Currency>
    ): Currency? {
        /*if (focusedCurrency == null && selectedCurrencies.isNotEmpty()) {
            val currencyToFocus = selectedCurrencies.first()
                .also { firstCurrency ->
                    firstCurrency.isFocused = true
                }
            return currencyToFocus
        }
        return null*/


        if (focusedCurrency != null || selectedCurrencies.isEmpty()) return null
        return selectedCurrencies.first().apply { isFocused = true }

    }

}