package com.nicoqueijo.android.selectcurrency.usecases

data class SelectCurrencyUseCases(
    val selectCurrencyUseCase: SelectCurrencyUseCase,
    val retrieveCurrenciesUseCase: RetrieveCurrenciesUseCase,
    val filterCurrenciesUseCase: FilterCurrenciesUseCase,
)