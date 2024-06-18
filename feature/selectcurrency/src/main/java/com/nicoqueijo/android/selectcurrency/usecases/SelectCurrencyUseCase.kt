package com.nicoqueijo.android.selectcurrency.usecases

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

class SelectCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    suspend operator fun invoke(selectedCurrency: Currency) {
        val lastPosition = repository.getSelectedCurrencyCount()
        selectedCurrency.apply {
            isSelected = true
            position = lastPosition
        }
        repository.upsertCurrency(selectedCurrency)
    }
}