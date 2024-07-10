package com.nicoqueijo.android.convertcurrency.usecases

/**
 * Data class that aggregates all use cases related to currency conversion operations.
 *
 * This class provides a centralized way to access various use cases required for handling
 * different aspects of currency conversion within the application.
 */
data class ConvertCurrencyUseCases(
    val setDefaultCurrenciesUseCase: SetDefaultCurrenciesUseCase,
    val retrieveSelectedCurrenciesUseCase: RetrieveSelectedCurrenciesUseCase,
    val unselectAllCurrenciesUseCase: UnselectAllCurrenciesUseCase,
    val unselectCurrencyUseCase: UnselectCurrencyUseCase,
    val restoreCurrencyUseCase: RestoreCurrencyUseCase,
    val setDefaultFocusedCurrencyUseCase: SetDefaultFocusedCurrencyUseCase,
    val updateFocusedCurrencyUseCase: UpdateFocusedCurrencyUseCase,
    val updateSelectedCurrenciesUseCase: UpdateSelectedCurrenciesUseCase,
    val processKeyboardInputUseCase: ProcessKeyboardInputUseCase,
    val updateHintsUseCase: UpdateHintsUseCase,
    val updateConversionsUseCase: UpdateConversionsUseCase,
    val reorderCurrenciesUseCase: ReorderCurrenciesUseCase,
    val retrieveIsFirstLaunchUseCase: RetrieveIsFirstLaunchUseCase,
    val toggleOffIsFirstLaunchUseCase: ToggleOffIsFirstLaunchUseCase,
)