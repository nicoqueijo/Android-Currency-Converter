package com.nicoqueijo.android.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicoqueijo.android.core.model.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao
}