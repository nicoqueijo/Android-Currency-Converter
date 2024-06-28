package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.Position
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UnselectAllCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke() {
        val currenciesToUnselect = repository.getSelectedCurrencies().first()
        currenciesToUnselect.forEach { currency ->
            currency.isSelected = false
            currency.position = Position.INVALID.value
        }
        repository.upsertCurrencies(currencies = currenciesToUnselect)
    }
}