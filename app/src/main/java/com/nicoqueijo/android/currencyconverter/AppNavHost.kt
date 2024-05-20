package com.nicoqueijo.android.currencyconverter

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nicoqueijo.android.convertcurrency.composables.ConvertCurrencyScreen
import com.nicoqueijo.android.convertcurrency.composables.ConvertCurrencyScreenState
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.currencyconverter.splash.SplashScreen
import com.nicoqueijo.android.selectcurrency.SelectCurrencyScreen
import kotlinx.serialization.Serializable

/**
 * Sealed class representing the screens within the application.
 */
@Serializable
sealed class Screen(val route: String) {

    @Serializable
    data object Splash : Screen(route = "splash")

    @Serializable
    data object Error : Screen(route = "error")

    @Serializable
    data object FeatureFlow : Screen(route = "feature_flow")

    @Serializable
    data object ConvertCurrency : Screen(route = "convert_currency")

    @Serializable
    data object SelectCurrency : Screen(route = "select_currency")
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
                    state = ConvertCurrencyScreenState(
                        onFabClick = {
                            navController.navigate(Screen.SelectCurrency.route)
                        }
                    )
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
