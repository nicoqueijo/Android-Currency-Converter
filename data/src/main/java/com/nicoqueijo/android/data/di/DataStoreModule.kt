package com.nicoqueijo.android.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.nicoqueijo.android.core.DefaultTimeProvider
import com.nicoqueijo.android.core.TimeProvider
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
     * @param dataStore The DataStore instance used to manage application preferences.
     * @param timeProvider The TimeProvider instance used to fetch the current time.
     * @return A singleton [DataStoreManager] instance.
     */
    @Singleton
    @Provides
    fun provideDataStoreManager(
        dataStore: DataStore<Preferences>,
        timeProvider: TimeProvider,
    ): DataStoreManager {
        return DataStoreManager(
            dataStore = dataStore,
            timeProvider = timeProvider,
        )
    }

    /**
     * Provides a singleton instance of [DataStore] for Preferences.
     *
     * @param context The application context used to initialize DataStore.
     * @return A singleton [DataStore] instance for Preferences.
     */
    @Singleton
    @Provides
    fun provideDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.myPreferencesDataStore
    }

    /**
     * Provides a singleton instance of [TimeProvider].
     *
     * @return A singleton [TimeProvider] instance.
     */
    @Singleton
    @Provides
    fun provideTimeProvider(): TimeProvider {
        return DefaultTimeProvider()
    }

    private val Context.myPreferencesDataStore: DataStore<Preferences>
            by preferencesDataStore(name = "preferences")
}
