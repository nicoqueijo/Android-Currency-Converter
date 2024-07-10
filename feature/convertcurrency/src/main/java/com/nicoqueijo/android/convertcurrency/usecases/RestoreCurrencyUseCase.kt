package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.first

/**
 * Use case for restoring a currency to its position and updating the repository.
 *
 * @property repository The repository that handles data operations for currencies.
 */
class RestoreCurrencyUseCase(
    private val repository: Repository,
) {

    /**
     * Restores a given currency to its position in the list of selected currencies.
     * This involves shifting the positions of other currencies to make space for the restored currency.
     *
     * @param currency The currency to be restored.
     */
    suspend operator fun invoke(currency: Currency) {
        val selectedCurrencies = repository.getSelectedCurrencies().first()
        val currencyToRestore = currency.deepCopy()
        for (i in currencyToRestore.position until selectedCurrencies.size) {
            selectedCurrencies[i].position = ++selectedCurrencies[i].position
        }
        repository.upsertCurrencies(
            currencies = (selectedCurrencies + currencyToRestore)
        )
    }
}
