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
                                if (navController.currentDestination?.route == Screen.Error.route) {
                                    // Not working. Maybe I have to init with remember()?
                                    IconButton(
                                        onClick = {
                                            navController.navigate(Screen.Splash.route) {
                                                popUpTo(Screen.Splash.route) {
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
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Splash.route) {
                            SplashScreen(
                                onSuccess = {
                                    navController.navigate(Screen.FeatureFlow.route) {
                                        popUpTo(Screen.Splash.route) {
                                            inclusive = true
                                        }
                                    }
                                },
                                onFailure = {
                                    navController.navigate(Screen.Error.route) {
                                        popUpTo(Screen.Splash.route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable(Screen.Error.route) {
                            ErrorScreen()
                        }
                        navigation(
                            startDestination = Screen.ConvertCurrency.route,
                            route = Screen.FeatureFlow.route
                        ) {
                            composable(Screen.ConvertCurrency.route) {
                                ConvertCurrencyScreen(
                                    currency = result,
                                    onFabClick = {
                                        navController.navigate(Screen.SelectCurrency.route)
                                    }
                                )
                            }
                            composable(Screen.SelectCurrency.route) {
                                SelectCurrencyScreen(
                                    onCurrencyClick = {
                                        navController.navigate(Screen.ConvertCurrency.route) {
                                            popUpTo(Screen.ConvertCurrency.route) {
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
