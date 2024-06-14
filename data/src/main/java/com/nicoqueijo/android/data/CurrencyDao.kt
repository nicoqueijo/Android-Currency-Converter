package com.nicoqueijo.android.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nicoqueijo.android.core.Currency

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrency(currency: Currency)

    @Upsert
    suspend fun upsertCurrencies(currencies: List<Currency>)

    @Query("UPDATE Currency SET exchangeRate = :exchangeRate WHERE currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { currency ->
            updateExchangeRate(
                currencyCode = currency.currencyCode,
                exchangeRate = currency.exchangeRate
            )
        }
    }

    @Query("SELECT * FROM Currency WHERE currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    @Query("SELECT * FROM Currency ORDER BY currencyCode ASC")
    suspend fun getAllCurrencies(): MutableList<Currency>

    @Query("SELECT * FROM Currency WHERE isSelected = 1 ORDER BY position ASC")
    suspend fun getSelectedCurrencies(): MutableList<Currency>

    @Query("SELECT COUNT(*) FROM Currency WHERE isSelected = 1")
    suspend fun getSelectedCurrencyCount(): Int
}
