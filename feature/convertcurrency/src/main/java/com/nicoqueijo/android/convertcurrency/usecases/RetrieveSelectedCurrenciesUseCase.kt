package com.nicoqueijo.android.convertcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for retrieving the list of selected currencies from the repository.
 *
 * @property repository The repository that handles data operations for currencies.
 */
class RetrieveSelectedCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    /**
     * Retrieves a flow of the list of selected currencies.
     *
     * @return A [Flow] emitting the list of selected currencies.
     */
    suspend operator fun invoke(): Flow<List<Currency>> {
        return repository.getSelectedCurrencies()
    }
}
