package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository

class ReorderCurrenciesUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(
        currencies: List<Currency>,
    ) {
        val currenciesCopy = currencies.deepCopy().toMutableList()
        currenciesCopy.forEachIndexed { index, currency ->
            currency.position = index
        }
        repository.upsertCurrencies(currencies = currenciesCopy)
    }
}