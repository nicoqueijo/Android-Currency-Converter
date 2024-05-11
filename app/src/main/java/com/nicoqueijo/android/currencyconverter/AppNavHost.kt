package com.nicoqueijo.android.currencyconverter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyScreen
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.currencyconverter.splash.SplashScreen
import com.nicoqueijo.android.selectcurrency.SelectCurrencyScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Error : Screen("error")
    data object FeatureFlow : Screen("feature_flow")
    data object ConvertCurrency : Screen("convert_currency")
    data object SelectCurrency : Screen("select_currency")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSuccess = {
                    navController.navigate(Screen.FeatureFlow.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onFailure = {
                    navController.navigate(Screen.Error.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
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
                    onFabClick = {
                        navController.navigate(Screen.SelectCurrency.route)
                    }
                )
            }
            composable(Screen.SelectCurrency.route) {
                SelectCurrencyScreen(
                    onCurrencyClick = {
                        navController.navigate(Screen.ConvertCurrency.route) {
                            popUpTo(Screen.ConvertCurrency.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
