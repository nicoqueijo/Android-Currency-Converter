package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency

class UpdateSelectedCurrenciesUseCase {

    operator fun invoke(
        memoryCurrencies: List<Currency>,
        databaseCurrencies: List<Currency>,
    ): List<Currency> {
        val memoryCurrenciesCopy = memoryCurrencies.deepCopy()
        val databaseCurrenciesCopy = databaseCurrencies.deepCopy()
        if (databaseCurrenciesCopy.isEmpty()) {
            return emptyList()
        }
        return (memoryCurrenciesCopy + databaseCurrenciesCopy).distinctBy { currency ->
            currency.currencyCode
        }
    }
}
