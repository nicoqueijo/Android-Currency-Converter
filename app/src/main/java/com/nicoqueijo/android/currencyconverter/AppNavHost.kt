package com.nicoqueijo.android.currencyconverter

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.nicoqueijo.android.convertcurrency.composables.ConvertCurrencyScreen
import com.nicoqueijo.android.currencyconverter.error.ErrorScreen
import com.nicoqueijo.android.currencyconverter.splash.SplashScreen
import com.nicoqueijo.android.selectcurrency.composables.SelectCurrencyScreen
import kotlinx.serialization.Serializable

/**
 * A composable function that sets up the navigation host for the app.
 *
 * @param modifier A [Modifier] to be applied to the [NavHost] layout.
 * @param navController The [NavHostController] that manages app navigation.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Splash.route,
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onSuccess = {
                    navController.navigate(route = Screen.FeatureFlow.route) {
                        popUpTo(route = Screen.Splash.route) { inclusive = true }
                    }
                },
                onFailure = {
                    navController.navigate(route = Screen.Error.route) {
                        popUpTo(route = Screen.Splash.route) { inclusive = true }
                    }
                },
            )
        }
        composable(Screen.Error.route) {
            ErrorScreen(
                onRefreshClick = {
                    navController.navigate(route = Screen.Splash.route) {
                        popUpTo(route = Screen.Error.route) { inclusive = true }
                    }
                },
            )
        }
        navigation(
            startDestination = Screen.ConvertCurrency.route,
            route = Screen.FeatureFlow.route,
        ) {
            composable(route = Screen.ConvertCurrency.route) {
                ConvertCurrencyScreen(
                    onFabClick = {
                        navController.navigate(route = Screen.SelectCurrency.route)
                    },
                )
            }
            composable(
                route = Screen.SelectCurrency.route,
                enterTransition = {
                    slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
                },
                exitTransition = {
                    slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
                },
            ) {
                SelectCurrencyScreen(
                    onCurrencyClick = {
                        navController.navigateUp()
                    },
                )
            }
        }
    }
}

/**
 * A sealed class representing the different screens/routes in the app.
 *
 * @property route The route associated with the screen.
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