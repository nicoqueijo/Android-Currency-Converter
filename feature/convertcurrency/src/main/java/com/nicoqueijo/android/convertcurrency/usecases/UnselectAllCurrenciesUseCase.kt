package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Use case to unselect all currently selected currencies in the repository.
 *
 * This class interacts with the [Repository] to fetch all selected currencies,
 * unselect them by setting their `isSelected` flag to false and resetting their
 * positions to an invalid state, and then updates the repository with these changes.
 *
 * @property repository The repository interface for data operations.
 */
class UnselectAllCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    /**
     * Unselects all currently selected currencies, setting their `isSelected` flag to false
     * and resetting their positions to an invalid state in the repository.
     *
     * This method performs the following steps:
     * 1. Retrieves the list of currently selected currencies from the repository.
     * 2. Iterates over each currency and updates its `isSelected` flag and position.
     * 3. Updates the repository with the modified list of currencies.
     */
    suspend operator fun invoke() {
        val currenciesToUnselect = repository.getSelectedCurrencies().first()
        currenciesToUnselect.forEach { currency ->
            currency.isSelected = false
            currency.position = Position.INVALID.value
        }
        repository.upsertCurrencies(currencies = currenciesToUnselect)
    }
}