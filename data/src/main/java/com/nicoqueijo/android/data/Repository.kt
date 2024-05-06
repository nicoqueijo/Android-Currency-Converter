package com.nicoqueijo.android.data

import com.nicoqueijo.android.network.KtorClient
import javax.inject.Inject

class Repository @Inject constructor(
    private val ktorClient: KtorClient,
    private val currencyDao: CurrencyDao
){

    suspend fun getCurrencies() = ktorClient.getExchangeRates().exchangeRates?.currencies
}