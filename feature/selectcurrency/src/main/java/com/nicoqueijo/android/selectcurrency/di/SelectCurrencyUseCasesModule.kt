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

/**
 * Module to provide use case instances for currency selection and retrieval.
 *
 * This module defines a provider for all use cases required for currency selection and retrieval operations,
 * ensuring they are available for dependency injection in the ViewModel component.
 */
@Module
@InstallIn(ViewModelComponent::class)
object SelectCurrencyUseCasesModule {

    /**
     * Provides an instance of [SelectCurrencyUseCases] containing all the use cases for currency selection and retrieval.
     *
     * This method creates and returns an instance of [SelectCurrencyUseCases] by initializing all the
     * required use cases with their respective dependencies.
     *
     * @param context The application context used for accessing resources.
     * @param repository The repository interface used for accessing and manipulating currency data.
     * @return An instance of [SelectCurrencyUseCases] with all the necessary use cases initialized.
     */
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
