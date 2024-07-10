package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.model.Position
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first

/**
 * Use case to unselect a specific currency in the repository.
 *
 * This class interacts with the [Repository] to fetch all selected currencies,
 * unselects the specified currency by setting its `isSelected` flag to false and
 * resetting its position to an invalid state, and then updates the repository
 * with these changes. It also adjusts the positions of other currencies as needed.
 *
 * @property repository The repository interface for data operations.
 */
class UnselectCurrencyUseCase(
    private val repository: Repository,
) {

    /**
     * Unselects the specified currency, sets its `isSelected` flag to false,
     * resets its position to an invalid state, and adjusts the positions of other
     * currencies accordingly in the repository.
     *
     * This method performs the following steps:
     * 1. Retrieves the list of currently selected currencies from the repository.
     * 2. Finds the currency to unselect by matching the currency code.
     * 3. Adjusts the positions of other currencies that follow the unselected currency.
     * 4. Updates the specified currency's `isSelected` flag and position.
     * 5. Updates the repository with the modified list of currencies.
     *
     * @param currency The currency to be unselected.
     */
    suspend operator fun invoke(currency: Currency) {
        val selectedCurrencies = repository.getSelectedCurrencies().first()
        val currencyToUnselect = selectedCurrencies.first { selectedCurrency ->
            selectedCurrency.currencyCode == currency.currencyCode
        }
        for (i in selectedCurrencies.count() - 1 downTo currencyToUnselect.position + 1) {
            selectedCurrencies[i].position = --selectedCurrencies[i].position
        }
        currencyToUnselect.apply {
            isSelected = false
            position = Position.INVALID.value
        }
        repository.upsertCurrencies(currencies = selectedCurrencies)
    }
}