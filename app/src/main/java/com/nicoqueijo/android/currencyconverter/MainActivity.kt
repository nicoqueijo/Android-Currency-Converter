package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.currencyconverter.ui.theme.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.data.Repository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var currencyRepository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var currencies: List<Currency>

        runBlocking {

        }

        enableEdgeToEdge()
        setContent {
            AndroidCurrencyConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    /*Greeting(
                        currencies = currencies,
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
    }
}

@Composable
fun Greeting(currencies: List<Currency>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        items(currencies) {
            Text(text = "${it.currencyCode} - ${it.exchangeRate}")
        }
    }
}