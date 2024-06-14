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

    @Query("UPDATE Currency SET exchangeRate = :exchangeRate WHERE currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { currency ->
            updateExchangeRate(currency.currencyCode, currency.exchangeRate)
        }
    }

    @Query("SELECT * FROM Currency WHERE currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM Currency ORDER BY currencyCode ASC")
    fun getAllCurrencies(): Flow<MutableList<Currency>>

    @Query("SELECT * FROM Currency WHERE isSelected = 1 ORDER BY position ASC")
    fun getSelectedCurrencies(): Flow<MutableList<Currency>>
}
