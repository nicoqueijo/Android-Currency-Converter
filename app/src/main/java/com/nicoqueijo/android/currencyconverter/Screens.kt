package com.nicoqueijo.android.currencyconverter

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Error : Screen("error")
    data object FeatureFlow : Screen("feature_flow")
    data object ConvertCurrency : Screen("convert_currency")
    data object SelectCurrency : Screen("select_currency")
}
