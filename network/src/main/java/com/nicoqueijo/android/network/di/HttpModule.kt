package com.nicoqueijo.android.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.android.AndroidClientEngine
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to the HTTP client configuration.
 */
@Module
@InstallIn(SingletonComponent::class)
object HttpModule {

    /**
     * Provides a singleton instance of [HttpClient] configured for Android with logging and JSON serialization.
     *
     * @param httpEngine The [HttpClientEngineFactory] used to create the HTTP client engine.
     * @return A singleton [HttpClient] instance.
     */
    @Singleton
    @Provides
    fun provideHttpClient(
        httpEngine: HttpClientEngine
    ): HttpClient {
        return HttpClient(httpEngine) {
            defaultRequest { url(BASE_URL) }
            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    /**
     * Provides the [HttpClientEngineFactory] for Android.
     *
     * @return The [HttpClientEngine] instance for Android.
     */
    @Singleton
    @Provides
    fun provideHttpEngine(): HttpClientEngine {
        return AndroidClientEngine(
            config = AndroidEngineConfig()
        )
    }

    private const val BASE_URL = "https://openexchangerates.org/api/"
}