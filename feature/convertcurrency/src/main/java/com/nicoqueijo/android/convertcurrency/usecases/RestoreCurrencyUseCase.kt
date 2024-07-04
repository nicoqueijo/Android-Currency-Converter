package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first

class RestoreCurrencyUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(currency: Currency) {
        val selectedCurrencies = repository.getSelectedCurrencies().first()
        val currencyToRestore = currency.deepCopy()
        for (i in currencyToRestore.position until selectedCurrencies.size) {
            selectedCurrencies[i].position = ++selectedCurrencies[i].position
        }
        repository.upsertCurrencies(
            currencies = (selectedCurrencies + currencyToRestore)
        )
    }
}