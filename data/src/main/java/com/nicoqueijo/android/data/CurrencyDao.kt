package com.nicoqueijo.android.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nicoqueijo.android.core.model.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object (DAO) for accessing and manipulating [Currency] data in the database.
 */
@Dao
interface CurrencyDao {

    /**
     * Inserts or updates a single [Currency] in the database.
     *
     * @param currency The currency to be upserted.
     */
    @Upsert
    suspend fun upsertCurrency(currency: Currency)

    /**
     * Inserts or updates a list of [Currency] objects in the database.
     *
     * @param currencies The list of currencies to be upserted.
     */
    @Upsert
    suspend fun upsertCurrencies(currencies: List<Currency>)

    /**
     * Updates the exchange rate of a specific currency identified by its code.
     *
     * @param currencyCode The code of the currency to be updated.
     * @param exchangeRate The new exchange rate of the currency.
     */
    @Query("UPDATE Currency SET exchangeRate = :exchangeRate WHERE currencyCode = :currencyCode")
    suspend fun updateExchangeRate(currencyCode: String, exchangeRate: Double)

    /**
     * Updates the exchange rates of a list of currencies in a transaction.
     *
     * @param currencies The list of currencies with updated exchange rates.
     */
    @Transaction
    suspend fun updateExchangeRates(currencies: List<Currency>) {
        currencies.forEach { currency ->
            updateExchangeRate(
                currencyCode = currency.currencyCode,
                exchangeRate = currency.exchangeRate
            )
        }
    }

    /**
     * Retrieves a [Currency] object by its code.
     *
     * @param currencyCode The code of the currency to be retrieved.
     * @return The currency with the specified code.
     */
    @Query("SELECT * FROM Currency WHERE currencyCode = :currencyCode")
    suspend fun getCurrency(currencyCode: String): Currency

    /**
     * Retrieves all [Currency] objects ordered by their code in ascending order.
     *
     * @return A list of all currencies.
     */
    @Query("SELECT * FROM Currency ORDER BY currencyCode ASC")
    suspend fun getAllCurrencies(): List<Currency>

    /**
     * Retrieves a flow of selected [Currency] objects ordered by their position in ascending order.
     *
     * @return A flow of selected currencies.
     */
    @Query("SELECT * FROM Currency WHERE isSelected = 1 ORDER BY position ASC")
    fun getSelectedCurrencies(): Flow<List<Currency>>

    /**
     * Retrieves the count of selected [Currency] objects.
     *
     * @return The count of selected currencies.
     */
    @Query("SELECT COUNT(*) FROM Currency WHERE isSelected = 1")
    suspend fun getSelectedCurrencyCount(): Int
}
