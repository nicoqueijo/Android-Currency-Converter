package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.extensions.deepCopy

class UpdateSelectedCurrenciesUseCase {

    operator fun invoke(
        selectedCurrenciesFromMemory: List<Currency>,
        selectedCurrenciesFromDatabase: List<Currency>,
    ): List<Currency> {
        val memoryCurrenciesCopy = selectedCurrenciesFromMemory.deepCopy()
        val databaseCurrenciesCopy = selectedCurrenciesFromDatabase.deepCopy()
        if (databaseCurrenciesCopy.isEmpty()) {
            return emptyList()
        }
        return (memoryCurrenciesCopy + databaseCurrenciesCopy).distinctBy { currency ->
            currency.currencyCode
        }
    }
}
