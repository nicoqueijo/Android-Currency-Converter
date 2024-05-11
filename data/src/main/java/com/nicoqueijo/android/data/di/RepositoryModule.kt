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

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

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