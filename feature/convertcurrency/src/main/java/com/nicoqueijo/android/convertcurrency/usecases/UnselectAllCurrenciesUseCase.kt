package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UnselectAllCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke() {
//        removeAll()
        reorderFirstTwo()
    }

    private suspend fun removeAll() {
        val currenciesToUnselect = repository.getSelectedCurrencies().first()
        currenciesToUnselect.forEach { currency ->
            currency.isSelected = false
            currency.position = Position.INVALID.value
        }
        repository.upsertCurrencies(currencies = currenciesToUnselect)
    }

    private suspend fun reorderFirstTwo() {
        val currenciesToReorder = repository.getSelectedCurrencies().first()
        currenciesToReorder[0].position = 1
        currenciesToReorder[1].position = 0
        repository.upsertCurrencies(currencies = currenciesToReorder)
    }


}