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
import androidx.navigation.compose.rememberNavController
import com.nicoqueijo.android.currencyconverter.splash.SplashScreen
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
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
