@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidCurrencyConverterTheme {
                val navController = rememberNavController()
                val currentDestination = navController.currentBackStackEntryAsState()

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "Currency Converter") },
                            actions = {

                                when (currentDestination.value?.destination?.route) {
                                    Screen.Error.route -> {
                                        IconButton(
                                            onClick = {
                                                navController.navigate(Screen.Splash.route) {
                                                    popUpTo(Screen.Error.route) { inclusive = true }
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Refresh,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    Screen.ConvertCurrency.route -> {
                                        IconButton(
                                            onClick = {
                                                // TODO: Remove all selected currencies
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.MoreVert,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                    Screen.SelectCurrency.route -> {
                                        IconButton(
                                            onClick = {
                                                // TODO: Summon search bar to filter currencies
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Search,
                                                contentDescription = null
                                            )
                                        }
                                    }
                                }

                            }
                        )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
