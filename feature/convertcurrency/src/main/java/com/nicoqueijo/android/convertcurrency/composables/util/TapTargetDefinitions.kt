package com.nicoqueijo.android.convertcurrency.composables.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.psoffritti.taptargetcompose.TapTargetDefinition
import com.psoffritti.taptargetcompose.TapTargetStyle
import com.psoffritti.taptargetcompose.TextDefinition

@Composable
fun addCurrencyTapTargetDefinition(): TapTargetDefinition {
    return TapTargetDefinition(
        precedence = 0,
        title = TextDefinition(
            text = "Tap button to add",
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        description = TextDefinition(
            text = "Tap button to add",
            textStyle = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        tapTargetStyle = TapTargetStyle(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
            backgroundAlpha = 1f,
        ),
    )
}

@Composable
fun copyConversionTapTargetDefinition(): TapTargetDefinition {
    return TapTargetDefinition(
        precedence = 1,
        title = TextDefinition(
            text = "Long-press conversion to copy",
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        description = TextDefinition(
            text = "Long-press conversion to copy",
            textStyle = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        tapTargetStyle = TapTargetStyle(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
            backgroundAlpha = 1f,
        ),
    )
}

@Composable
fun reorderCurrencyTapTargetDefinition(): TapTargetDefinition {
    return TapTargetDefinition(
        precedence = 2,
        title = TextDefinition(
            text = "Long-press & drag any part of the row currency to reorder",
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        description = TextDefinition(
            text = "Long-press & drag any part of the row currency to reorder",
            textStyle = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        tapTargetStyle = TapTargetStyle(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
            backgroundAlpha = 1f,
        ),
    )
}

@Composable
fun removeCurrencyTapTargetDefinition(): TapTargetDefinition {
    return TapTargetDefinition(
        precedence = 3,
        title = TextDefinition(
            text = "Swipe any part of the row to remove",
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        description = TextDefinition(
            text = "Swipe any part of the row to remove",
            textStyle = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        tapTargetStyle = TapTargetStyle(
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            tapTargetHighlightColor = MaterialTheme.colorScheme.onSecondaryContainer,
            backgroundAlpha = 1f,
        ),
    )
}