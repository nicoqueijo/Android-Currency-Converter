@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyScreen
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.currencyconverter.splash.SplashScreen
import com.nicoqueijo.android.data.CurrencyRepository
import com.nicoqueijo.android.selectcurrency.SelectCurrencyScreen
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
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
                result = it.exchangeRates!!.currencies[60].toString()
            }.onFailure {
                result = it.message.toString()
            }
        }

        setContent {
            val navController = rememberNavController()
            AndroidCurrencyConverterTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Currency Converter") },
                            actions = {
                                IconButton(
                                    onClick = {
                                        navController.navigate("splash_screen") {
                                            popUpTo("splash_screen") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Refresh,
                                        contentDescription = null
                                    )
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("splash_screen") {
                            SplashScreen(
                                onSuccess = {
                                    navController.navigate("features_flow") {
                                        popUpTo("splash_screen") {
                                            inclusive = true
                                        }
                                    }
                                },
                                onFailure = {
                                    navController.navigate("error_screen") {
                                        popUpTo("splash_screen") {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable("error_screen") {
                            ErrorScreen()
                        }
                        navigation(
                            startDestination = "convert_currency_screen",
                            route = "features_flow"
                        ) {
                            composable("convert_currency_screen") {
                                ConvertCurrencyScreen(
                                    currency = result,
                                    onFabClick = {
                                        navController.navigate("select_currency_screen")
                                    }
                                )
                            }
                            composable("select_currency_screen") {
                                SelectCurrencyScreen(
                                    onCurrencyClick = {
                                        navController.navigate("convert_currency_screen") {
                                            popUpTo("convert_currency_screen") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
@DarkLightPreviews
fun Greeting() {
    AndroidCurrencyConverterTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Currency Converter") },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            SplashScreen(modifier = Modifier.padding(innerPadding))
        }
    }
}
