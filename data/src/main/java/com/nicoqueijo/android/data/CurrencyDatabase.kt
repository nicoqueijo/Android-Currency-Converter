package com.nicoqueijo.android.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.nicoqueijo.android.core.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        private const val DB_NAME = "currency.db"

        @Volatile
        private var instance: CurrencyDatabase? = null

        fun getInstance(context: Context): CurrencyDatabase {
            return instance ?: synchronized(this) {
                instance ?: databaseBuilder(
                    context = context,
                    klass = CurrencyDatabase::class.java,
                    name = DB_NAME
                ).fallbackToDestructiveMigration().build().also { currencyDatabase ->
                    instance = currencyDatabase
                }
            }
        }
    }
}