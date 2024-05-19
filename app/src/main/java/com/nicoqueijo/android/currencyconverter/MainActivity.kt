@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.navigation.compose.rememberNavController
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                                popUpTo(Screen.Splash.route) { inclusive = true }
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
                    AppNavHost(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
