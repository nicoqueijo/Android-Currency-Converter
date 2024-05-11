package com.nicoqueijo.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.nicoqueijo.android.core.Currency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCurrency(currency: Currency)

    @Transaction
    suspend fun upsertCurrencies(currencies: List<Currency>) {
        currencies.forEach { currency ->
            upsertCurrency(currency = currency)
        }
    }

    @Query("UPDATE table_currency SET column_exchangeRate = :exchangeRate WHERE column_currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { currency ->
            updateExchangeRate(currency.currencyCode, currency.exchangeRate)
        }
    }

    @Query("SELECT * FROM table_currency WHERE column_currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM table_currency ORDER BY column_currencyCode ASC")
    fun getAllCurrencies(): Flow<MutableList<Currency>>

    @Query("SELECT * FROM table_currency WHERE column_isSelected = 1 ORDER BY column_order ASC")
    fun getSelectedCurrencies(): Flow<MutableList<Currency>>
}
