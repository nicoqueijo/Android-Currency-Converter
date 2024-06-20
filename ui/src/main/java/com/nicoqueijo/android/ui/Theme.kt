package com.nicoqueijo.android.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    surface = Black,
    primary = White,
    secondary = Black,
    tertiary = DarkGray,
    /*onPrimary = ,
    onSecondary = ,*/
    onTertiary = AlmostBlack,
)

private val LightColorScheme = lightColorScheme(
    surface = White,
    primary = Black,
    secondary = White,
    tertiary = LightGray,
    /*onPrimary = ,
    onSecondary = ,*/
    onTertiary = AlmostWhite,
)

@Composable
fun AndroidCurrencyConverterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}