package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.data.CurrencyRepository
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currencyRepository: CurrencyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var result = "Uninitialized"

        runBlocking {
            currencyRepository.getExchangeRates().onSuccess {
                result = it.exchangeRates!!.currencies.toString()
            }.onFailure {
                result = it.message.toString()
            }
        }

        setContent {
            AndroidCurrencyConverterTheme {
                ErrorScreen()
            }
        }
    }
}

@Composable
fun Greeting(message: String, modifier: Modifier = Modifier) {
    Text(text = message)
}