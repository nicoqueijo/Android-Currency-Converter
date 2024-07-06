package com.nicoqueijo.android.convertcurrency.usecases

data class ConvertCurrencyUseCases(
    val retrieveSelectedCurrenciesUseCase: RetrieveSelectedCurrenciesUseCase,
    val unselectAllCurrenciesUseCase: UnselectAllCurrenciesUseCase,
    val unselectCurrencyUseCase: UnselectCurrencyUseCase,
    val restoreCurrencyUseCase: RestoreCurrencyUseCase,
    val setDefaultFocusedCurrency: SetDefaultFocusedCurrency,
    val updateFocusedCurrencyUseCase: UpdateFocusedCurrencyUseCase,
    val updateSelectedCurrenciesUseCase: UpdateSelectedCurrenciesUseCase,
    val processKeyboardInputUseCase: ProcessKeyboardInputUseCase,
    val updateHintsUseCase: UpdateHintsUseCase,
    val updateConversionsUseCase: UpdateConversionsUseCase,
    val reorderCurrenciesUseCase: ReorderCurrenciesUseCase,
)