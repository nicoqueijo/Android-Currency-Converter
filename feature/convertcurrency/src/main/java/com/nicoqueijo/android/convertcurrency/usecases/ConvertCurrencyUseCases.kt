package com.nicoqueijo.android.convertcurrency.usecases

data class ConvertCurrencyUseCases(
    val retrieveSelectedCurrenciesUseCase: RetrieveSelectedCurrenciesUseCase,
    val removeAllCurrenciesUseCase: RemoveAllCurrenciesUseCase,
)