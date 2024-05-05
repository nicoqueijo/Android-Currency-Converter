package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicoqueijo.android.currencyconverter.ui.theme.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.network.ApiEndPoint
import com.nicoqueijo.android.network.ExchangeRateService
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test retrofit call
        // The retrofit stuff resides in the network module so I need to import it as a dependency
        // on the app module's build.gradle

        runBlocking {
            val retrofit = Retrofit.Builder()
                .baseUrl(ExchangeRateService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(ExchangeRateService::class.java)
                .getExchangeRates(BuildConfig.API_KEY_DEBUG)
            println(
                retrofit.body()?.timestamp
            )
            println(
                retrofit.body()?.exchangeRates?.USD_AED
            )
            println(
                retrofit.body()?.exchangeRates?.USD_ZWL
            )
        }


        enableEdgeToEdge()
        setContent {
            AndroidCurrencyConverterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidCurrencyConverterTheme {
        Greeting("Android")
    }
}
