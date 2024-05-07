package com.nicoqueijo.android.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * Dagger module for providing coroutine dispatchers.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    /**
     * Provides the default coroutine dispatcher using Dispatchers.Default. The default dispatcher
     * is typically used for coroutines that don't have specific requirements and can be run on any
     * thread from the pool of threads used by Dispatchers.Default.
     *
     * @return A CoroutineDispatcher instance representing the default dispatcher.
     */
    @Provides
    @DefaultDispatcher
    fun provideDefaultCoroutineDispatcher() = Dispatchers.Default

    /**
     * Provides the I/O coroutine dispatcher using Dispatchers.IO. The I/O dispatcher is used for
     * coroutines that perform I/O bound operations, such as network requests or file system
     * access. It ensures these tasks are executed on threads optimized for I/O operations,
     * improving application performance.
     *
     * @return A CoroutineDispatcher instance representing the I/O dispatcher.
     */
    @Provides
    @IODispatcher
    fun provideIOCoroutineDispatcher() = Dispatchers.IO
}

/**
 * Qualifier annotation used to identify the default coroutine dispatcher.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

/**
 * Qualifier annotation used to identify the I/O coroutine dispatcher.
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher