package com.nicoqueijo.android.selectcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

class RetrieveCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(): List<Currency> {
        return repository.getAllCurrencies()
    }
}