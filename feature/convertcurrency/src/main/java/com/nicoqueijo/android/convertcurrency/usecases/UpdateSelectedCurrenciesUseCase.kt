package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency

class UpdateSelectedCurrenciesUseCase {

    operator fun invoke(
        selectedCurrenciesFromMemory: List<Currency>,
        selectedCurrenciesFromDatabase: List<Currency>,
    ): List<Currency> {
        return (selectedCurrenciesFromMemory + selectedCurrenciesFromDatabase).distinctBy { currency ->
            currency.currencyCode
        }
    }
}