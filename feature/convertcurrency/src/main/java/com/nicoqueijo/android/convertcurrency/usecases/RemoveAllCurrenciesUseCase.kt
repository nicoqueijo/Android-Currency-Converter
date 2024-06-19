package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.Position
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

class RemoveAllCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke() {
        val currenciesToRemove = repository.getSelectedCurrencies()
        currenciesToRemove.forEach { currency ->
            currency.isSelected = false
            currency.position = Position.INVALID.value
        }
        repository.upsertCurrencies(currencies = currenciesToRemove)
    }
}