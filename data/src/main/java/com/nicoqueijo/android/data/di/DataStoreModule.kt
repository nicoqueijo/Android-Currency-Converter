package com.nicoqueijo.android.data.di

import android.content.Context
import com.nicoqueijo.android.data.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to DataStore.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    /**
     * Provides a singleton instance of [DataStoreManager].
     *
     * @param context The application context used to initialize DataStoreManager.
     * @return A singleton [DataStoreManager] instance.
     */
    @Singleton
    @Provides
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context = context)
    }
}