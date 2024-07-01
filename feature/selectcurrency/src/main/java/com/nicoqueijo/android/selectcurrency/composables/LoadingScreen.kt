package com.nicoqueijo.android.selectcurrency.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.valentinilk.shimmer.shimmer

/**
 * Composable function that renders a loading screen. Creates a full-screen surface with a
 * shimmering effect to indicate ongoing loading. It displays several [LoadingItem] composables to
 * simulate content being loaded.
 *
 * @param modifier [Modifier] to be applied to the loading screen container (optional).
 */
@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            repeat(times = 20) {
                LoadingItem()
                HorizontalDivider()
            }
        }
    }
}

/**
 * Composable function that renders a single loading item. Creates a box with a specific height to
 * represent a placeholder for loading content.
 *
 * @param modifier [Modifier] to be applied to the loading item (optional).
 */
@Composable
fun LoadingItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .shimmer()
            .background(color = MaterialTheme.colorScheme.tertiary)
            .fillMaxWidth()
            .height(height = 64.dp)
    )
}

@DarkLightPreviews
@Composable
fun LoadingScreenPreview() {
    AndroidCurrencyConverterTheme {
        LoadingScreen()
    }
}
