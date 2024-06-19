package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

class RetrieveSelectedCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(): List<Currency> {
        return repository.getSelectedCurrencies()
    }
}