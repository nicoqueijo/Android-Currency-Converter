package com.nicoqueijo.android.selectcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.data.Repository

class RetrieveCurrenciesUseCase(
    private val repository: Repository,
) {

    suspend operator fun invoke(): List<Currency> {
        return repository.getAllCurrencies()
    }
}