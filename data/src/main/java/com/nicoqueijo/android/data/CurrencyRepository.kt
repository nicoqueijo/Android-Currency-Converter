package com.nicoqueijo.android.data

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.core.di.IODispatcher
import com.nicoqueijo.android.network.ApiOperation
import com.nicoqueijo.android.network.KtorClient
import com.nicoqueijo.android.network.OpenExchangeRatesEndPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val ktorClient: KtorClient,
    private val currencyDao: CurrencyDao,
    private val dataStoreManager: DataStoreManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : Repository {

    override suspend fun getExchangeRates(): ApiOperation<OpenExchangeRatesEndPoint> {
        return withContext(context = dispatcher) {
            ktorClient.getExchangeRates()
        }
    }

    override suspend fun upsertCurrency(currency: Currency) {
        withContext(context = dispatcher) {
            currencyDao.upsertCurrency(currency = currency)
        }
    }

    override suspend fun upsertCurrencies(currencies: List<Currency>) {
        withContext(context = dispatcher) {
            currencyDao.upsertCurrencies(currencies = currencies)
        }
    }

    override suspend fun updateExchangeRates(currencies: List<Currency>) {
        withContext(context = dispatcher) {
            currencyDao.updateExchangeRates(currencies = currencies)
        }
    }

    override suspend fun getCurrency(currencyCode: String): Currency {
        return withContext(context = dispatcher) {
            currencyDao.getCurrency(currencyCode = currencyCode)
        }
    }

    override suspend fun getAllCurrencies(): List<Currency> {
        return withContext(context = dispatcher) {
            currencyDao.getAllCurrencies()
        }
    }

    override suspend fun getSelectedCurrencies(): Flow<List<Currency>> {
        return withContext(context = dispatcher) {
            currencyDao.getSelectedCurrencies()
        }
    }

    override suspend fun getSelectedCurrencyCount(): Int {
        return withContext(context = dispatcher) {
            currencyDao.getSelectedCurrencyCount()
        }
    }

    override suspend fun setFirstLaunch(value: Boolean) {
        withContext(context = dispatcher) {
            dataStoreManager.setFirstLaunch(value = value)
        }
    }

    override suspend fun isFirstLaunch(): Boolean {
        return withContext(context = dispatcher) {
            dataStoreManager.isFirstLaunch()
        }
    }

    override suspend fun setTimestampInSeconds(value: Long) {
        withContext(context = dispatcher) {
            dataStoreManager.setTimestampInSeconds(value = value)
        }
    }

    override suspend fun getTimestampInSeconds(): Long {
        return withContext(context = dispatcher) {
            dataStoreManager.getTimestampInSeconds()
        }
    }

    override suspend fun isDataEmpty(): Boolean {
        return withContext(context = dispatcher) {
            dataStoreManager.isDataEmpty()
        }
    }

    override suspend fun isDataStale(): Boolean {
        return withContext(context = dispatcher) {
            dataStoreManager.isDataStale()
        }
    }
}