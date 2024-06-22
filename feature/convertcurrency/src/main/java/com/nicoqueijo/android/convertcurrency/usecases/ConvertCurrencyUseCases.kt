package com.nicoqueijo.android.convertcurrency.usecases

data class ConvertCurrencyUseCases(
    val retrieveSelectedCurrenciesUseCase: RetrieveSelectedCurrenciesUseCase,
    val removeSelectedCurrenciesUseCase: RemoveSelectedCurrenciesUseCase,
    val setDefaultFocusedCurrency: SetDefaultFocusedCurrency,
    val updateFocusedCurrencyUseCase: UpdateFocusedCurrencyUseCase,
    val updateSelectedCurrenciesUseCase: UpdateSelectedCurrenciesUseCase,
)