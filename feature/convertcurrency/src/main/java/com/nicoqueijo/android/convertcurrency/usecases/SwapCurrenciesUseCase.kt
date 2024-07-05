package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository

class SwapCurrenciesUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(
        currencies: List<Currency>,
        currencyFromCode: String,
        currencyToCode: String,
    ) {
        val currenciesCopy = currencies.deepCopy().toMutableList()
        val currencyFrom = currenciesCopy.single { it.currencyCode == currencyFromCode }
        val currencyTo = currenciesCopy.single { it.currencyCode == currencyToCode }
        currenciesCopy.apply {
            this[currencyFrom.position].position = this[currencyTo.position].position.also {
                this[currencyTo.position].position = this[currencyFrom.position].position
            }

            this[currencyFrom.position] = this[currencyTo.position].also {
                this[currencyTo.position] = this[currencyFrom.position]
            }
        }
        repository.upsertCurrencies(currencies = currenciesCopy)
    }
}