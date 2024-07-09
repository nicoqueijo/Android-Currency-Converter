package com.nicoqueijo.android.data.di

import com.nicoqueijo.android.network.KtorClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to network operations.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Provides a singleton instance of [KtorClient].
     *
     * @return A singleton [KtorClient] instance.
     */
    @Singleton
    @Provides
    fun provideKtorClient(): KtorClient {
        return KtorClient()
    }
}
