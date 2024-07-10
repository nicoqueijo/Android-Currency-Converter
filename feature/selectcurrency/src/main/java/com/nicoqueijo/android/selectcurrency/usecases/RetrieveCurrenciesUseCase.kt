package com.nicoqueijo.android.selectcurrency.usecases

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.data.Repository
import javax.inject.Inject

/**
 * Use case to retrieve all currencies from the repository.
 *
 * @property repository The repository interface for accessing currency data.
 */
class RetrieveCurrenciesUseCase @Inject constructor(
    private val repository: Repository,
) {

    /**
     * Retrieves all currencies from the repository.
     *
     * @return A list of all currencies.
     */
    suspend operator fun invoke(): List<Currency> {
        return repository.getAllCurrencies()
    }
}