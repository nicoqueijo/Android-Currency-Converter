package com.nicoqueijo.android.selectcurrency.di

import android.content.Context
import com.nicoqueijo.android.data.Repository
import com.nicoqueijo.android.selectcurrency.usecases.FilterCurrenciesUseCase
import com.nicoqueijo.android.selectcurrency.usecases.RetrieveCurrenciesUseCase
import com.nicoqueijo.android.selectcurrency.usecases.SelectCurrencyUseCase
import com.nicoqueijo.android.selectcurrency.usecases.SelectCurrencyUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object SelectCurrencyUseCasesModule {

    @Provides
    fun provideSelectCurrencyUseCases(
        @ApplicationContext context: Context,
        repository: Repository,
    ): SelectCurrencyUseCases {
        return SelectCurrencyUseCases(
            selectCurrencyUseCase = SelectCurrencyUseCase(repository = repository),
            retrieveCurrenciesUseCase = RetrieveCurrenciesUseCase(repository = repository),
            filterCurrenciesUseCase = FilterCurrenciesUseCase(
                context = context,
                retrieveCurrenciesUseCase = RetrieveCurrenciesUseCase(repository = repository)
            )
        )
    }
}