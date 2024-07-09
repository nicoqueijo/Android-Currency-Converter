package com.nicoqueijo.android.data.di

import com.nicoqueijo.android.core.di.IODispatcher
import com.nicoqueijo.android.data.CurrencyDao
import com.nicoqueijo.android.data.CurrencyRepository
import com.nicoqueijo.android.data.DataStoreManager
import com.nicoqueijo.android.data.Repository
import com.nicoqueijo.android.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to repositories.
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides a singleton instance of [Repository].
     *
     * @param ktorClient The client for making network requests.
     * @param currencyDao The DAO for accessing and manipulating currency data.
     * @param dataStoreManager The manager for handling preferences using DataStore.
     * @param dispatcher The coroutine dispatcher for executing background tasks.
     * @return A singleton [Repository] instance.
     */
    @Singleton
    @Provides
    fun provideRepository(
        ktorClient: KtorClient,
        currencyDao: CurrencyDao,
        dataStoreManager: DataStoreManager,
        @IODispatcher dispatcher: CoroutineDispatcher,
    ): Repository {
        return CurrencyRepository(
            ktorClient = ktorClient,
            currencyDao = currencyDao,
            dataStoreManager = dataStoreManager,
            dispatcher = dispatcher
        )
    }
}