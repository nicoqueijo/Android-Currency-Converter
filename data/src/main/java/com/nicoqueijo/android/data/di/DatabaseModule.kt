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

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCurrencyDatabase(@ApplicationContext context: Context): CurrencyDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CurrencyDatabase::class.java,
            name = "currency.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideCurrencyDao(database: CurrencyDatabase): CurrencyDao {
        return database.currencyDao()
    }
}