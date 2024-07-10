package com.nicoqueijo.android.selectcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

/**
 * Use case to select a currency.
 *
 * @property repository The repository interface for accessing and modifying currency data.
 */
class SelectCurrencyUseCase @Inject constructor(
    private val repository: Repository,
) {

    /**
     * Marks a currency as selected and updates its position.
     *
     * This method performs the following steps:
     * 1. Retrieves the current count of selected currencies from the repository.
     * 2. Updates the selected currency's `isSelected` flag to true and sets its position.
     * 3. Persists the updated currency in the repository.
     *
     * @param selectedCurrency The currency to be marked as selected.
     */
    suspend operator fun invoke(selectedCurrency: Currency) {
        val lastPosition = repository.getSelectedCurrencyCount()
        selectedCurrency.apply {
            isSelected = true
            position = lastPosition
        }
        repository.upsertCurrency(selectedCurrency)
    }
}