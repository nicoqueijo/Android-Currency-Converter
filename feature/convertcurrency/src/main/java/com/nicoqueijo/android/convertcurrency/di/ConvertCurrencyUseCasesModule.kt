package com.nicoqueijo.android.convertcurrency.di

import com.nicoqueijo.android.convertcurrency.usecases.ConvertCurrencyUseCases
import com.nicoqueijo.android.convertcurrency.usecases.ProcessKeyboardInputUseCase
import com.nicoqueijo.android.convertcurrency.usecases.ReorderCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.RestoreCurrencyUseCase
import com.nicoqueijo.android.convertcurrency.usecases.RetrieveIsFirstLaunchUseCase
import com.nicoqueijo.android.convertcurrency.usecases.RetrieveSelectedCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.SetDefaultCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.SetDefaultFocusedCurrencyUseCase
import com.nicoqueijo.android.convertcurrency.usecases.ToggleOffIsFirstLaunchUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UnselectAllCurrenciesUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UnselectCurrencyUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UpdateConversionsUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UpdateFocusedCurrencyUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UpdateHintsUseCase
import com.nicoqueijo.android.convertcurrency.usecases.UpdateSelectedCurrenciesUseCase
import com.nicoqueijo.android.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Hilt module to provide use case instances for currency conversion.
 *
 * This module defines a provider for all use cases required for currency conversion operations,
 * ensuring they are available for dependency injection in the ViewModel component.
 */
@Module
@InstallIn(ViewModelComponent::class)
object ConvertCurrencyUseCasesModule {

    /**
     * Provides an instance of [ConvertCurrencyUseCases] containing all the use cases for currency conversion.
     *
     * This method creates and returns an instance of [ConvertCurrencyUseCases] by initializing all the
     * required use cases with their respective dependencies.
     *
     * @param repository The repository interface used for accessing and manipulating currency data.
     * @return An instance of [ConvertCurrencyUseCases] with all the necessary use cases initialized.
     */
    @Provides
    fun provideConvertCurrencyUseCases(
        repository: Repository,
    ): ConvertCurrencyUseCases {
        return ConvertCurrencyUseCases(
            setDefaultCurrenciesUseCase = SetDefaultCurrenciesUseCase(
                repository = repository
            ),
            retrieveSelectedCurrenciesUseCase = RetrieveSelectedCurrenciesUseCase(
                repository = repository,
            ),
            unselectAllCurrenciesUseCase = UnselectAllCurrenciesUseCase(
                repository = repository,
            ),
            unselectCurrencyUseCase = UnselectCurrencyUseCase(
                repository = repository,
            ),
            restoreCurrencyUseCase = RestoreCurrencyUseCase(
                repository = repository,
            ),
            setDefaultFocusedCurrencyUseCase = SetDefaultFocusedCurrencyUseCase(),
            updateFocusedCurrencyUseCase = UpdateFocusedCurrencyUseCase(),
            updateSelectedCurrenciesUseCase = UpdateSelectedCurrenciesUseCase(),
            processKeyboardInputUseCase = ProcessKeyboardInputUseCase(),
            updateHintsUseCase = UpdateHintsUseCase(),
            updateConversionsUseCase = UpdateConversionsUseCase(),
            reorderCurrenciesUseCase = ReorderCurrenciesUseCase(
                repository = repository,
            ),
            toggleOffIsFirstLaunchUseCase = ToggleOffIsFirstLaunchUseCase(
                repository = repository
            ),
            retrieveIsFirstLaunchUseCase = RetrieveIsFirstLaunchUseCase(
                repository = repository
            ),
        )
    }
}