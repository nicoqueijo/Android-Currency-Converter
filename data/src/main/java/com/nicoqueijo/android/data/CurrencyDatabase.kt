package com.nicoqueijo.android.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicoqueijo.android.core.model.Currency

/**
 * Abstract class representing the database for storing [Currency] data.
 *
 * This class defines the database configuration and serves as the main access point
 * for the underlying connection to the app's persisted relational data.
 */
@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    /**
     * Provides access to the [CurrencyDao] for performing database operations on [Currency] entities.
     *
     * @return The [CurrencyDao] instance.
     */
    abstract fun currencyDao(): CurrencyDao
}