package com.nicoqueijo.android.data

import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.core.di.IODispatcher
import com.nicoqueijo.android.network.KtorClient
import com.nicoqueijo.android.network.OpenExchangeRatesEndPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val currencyDao: CurrencyDao,
    private val dataStoreManager: DataStoreManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : Repository {
    override suspend fun getExchangeRates(): OpenExchangeRatesEndPoint {
        TODO("Not yet implemented")
    }

    override suspend fun upsertCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }

    override suspend fun upsertCurrencies(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateExchangeRates(currencies: List<Currency>) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrency(currencyCode: String): Currency {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCurrencies(): Flow<MutableList<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun getSelectedCurrencies(): Flow<MutableList<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun setFirstLaunch(value: Boolean) {
        TODO("Not yet implemented")
    }

    override suspend fun setTimestampInSeconds(value: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getFirstLaunch(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getTimestampInSeconds(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun isDataEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun isDataStale(): Boolean {
        TODO("Not yet implemented")
    }
}