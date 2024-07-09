package com.nicoqueijo.android.data.di

import android.content.Context
import androidx.room.Room
import com.nicoqueijo.android.data.CurrencyDao
import com.nicoqueijo.android.data.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies related to the database.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    /**
     * Provides a singleton instance of [CurrencyDatabase].
     *
     * @param context The application context used to create the database.
     * @return A singleton [CurrencyDatabase] instance.
     */
    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CurrencyDatabase::class.java,
            name = "currency.db"
        ).fallbackToDestructiveMigration().build()
    }

    /**
     * Provides a singleton instance of [CurrencyDao].
     *
     * @param database The [CurrencyDatabase] instance used to obtain the DAO.
     * @return A singleton [CurrencyDao] instance.
     */
    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}