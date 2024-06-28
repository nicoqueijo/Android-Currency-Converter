package com.nicoqueijo.android.convertcurrency.usecases

data class ConvertCurrencyUseCases(
    val retrieveSelectedCurrenciesUseCase: RetrieveSelectedCurrenciesUseCase,
    val unselectAllCurrenciesUseCase: UnselectAllCurrenciesUseCase,
    val setDefaultFocusedCurrency: SetDefaultFocusedCurrency,
    val updateFocusedCurrencyUseCase: UpdateFocusedCurrencyUseCase,
    val updateSelectedCurrenciesUseCase: UpdateSelectedCurrenciesUseCase,
    val processKeyboardInputUseCase: ProcessKeyboardInputUseCase,
)