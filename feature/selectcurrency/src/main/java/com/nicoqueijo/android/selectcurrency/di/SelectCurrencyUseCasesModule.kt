package com.nicoqueijo.android.selectcurrency.di

import com.nicoqueijo.android.data.Repository
import com.nicoqueijo.android.selectcurrency.usecases.RetrieveCurrenciesUseCase
import com.nicoqueijo.android.selectcurrency.usecases.SelectCurrencyUseCase
import com.nicoqueijo.android.selectcurrency.usecases.SelectCurrencyUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object SelectCurrencyUseCasesModule {

    @Provides
    fun provideSelectCurrencyUseCases(
        repository: Repository,
    ): SelectCurrencyUseCases {
        return SelectCurrencyUseCases(
            selectCurrencyUseCase = SelectCurrencyUseCase(repository = repository),
            retrieveCurrenciesUseCase = RetrieveCurrenciesUseCase(repository = repository),
        )
    }

}