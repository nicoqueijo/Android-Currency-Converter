package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.extensions.deepCopy
import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository

/**
 * Use case for reordering a list of currencies and updating their positions in the repository.
 *
 * @property repository The repository that handles data operations for currencies.
 */
class ReorderCurrenciesUseCase(
    private val repository: Repository,
) {

    /**
     * Reorders the given list of currencies by updating their positions and then saving the updated
     * list to the repository. Each currency's position is set to its index in the list.
     *
     * @param currencies The list of currencies to be reordered.
     */
    suspend operator fun invoke(
        currencies: List<Currency>,
    ) {
        val currenciesCopy = currencies.deepCopy().toMutableList()
        currenciesCopy.forEachIndexed { index, currency ->
            currency.position = index
        }
        repository.upsertCurrencies(currencies = currenciesCopy)
    }
}
