package com.nicoqueijo.android.convertcurrency.di

import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.convertcurrency.usecases.ProcessKeyboardInputUseCase
import com.nicoqueijo.android.convertcurrency.usecases.RemoveSelectedCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.RetrieveSelectedCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.SetDefaultFocusedCurrency
import com.nicoqueijo.android.convertcurrency.usecases.UpdateFocusedCurrencyUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UpdateSelectedCurrenciesUseCase
import com.nicoqueijo.android.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ConvertCurrencyUseCasesModule {

    @Provides
    fun provideConvertCurrencyUseCases(
        repository: Repository,
    ): ConvertCurrencyUseCases {
        return ConvertCurrencyUseCases(
            retrieveSelectedCurrenciesUseCase = RetrieveSelectedCurrenciesUseCase(
                repository = repository
            ),
            removeSelectedCurrenciesUseCase = RemoveSelectedCurrenciesUseCase(
                repository = repository
            ),
            setDefaultFocusedCurrency = SetDefaultFocusedCurrency(),
            updateFocusedCurrencyUseCase = UpdateFocusedCurrencyUseCase(),
            updateSelectedCurrenciesUseCase = UpdateSelectedCurrenciesUseCase(),
            processKeyboardInputUseCase = ProcessKeyboardInputUseCase(),
        )
    }
}