package com.nicoqueijo.android.data

import com.nicoqueijo.android.core.di.IODispatcher
import com.nicoqueijo.android.network.KtorClient
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class Repository @Inject constructor(
    private val ktorClient: KtorClient,
    private val currencyDao: CurrencyDao,
    private val dataStoreManager: DataStoreManager,
    @IODispatcher private val dispatcher: CoroutineDispatcher
){
    suspend fun getCurrencies() = ktorClient.getExchangeRates().exchangeRates?.currencies
}