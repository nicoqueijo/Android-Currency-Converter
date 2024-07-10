package com.nicoqueijo.android.selectcurrency.usecases

/**
 * Data class that aggregates use cases related to selecting and retrieving currencies.
 *
 * This class provides a centralized way to access various use cases required for handling
 * currency selection and retrieval operations within the application.
 */
data class SelectCurrencyUseCases(
    val selectCurrencyUseCase: SelectCurrencyUseCase,
    val retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase,
    val filterCurrenciesUseCase: FilterCurrenciesUseCase,
)