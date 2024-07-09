package com.nicoqueijo.android.convertcurrency.composables.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nicoqueijo.android.convertcurrency.R
import com.psoffritti.taptargetcompose.TapTargetDefinition
import com.psoffritti.taptargetcompose.TapTargetStyle
import com.psoffritti.taptargetcompose.TextDefinition

@Composable
fun addCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 0,
        title = stringResource(R.string.tap_button_to_add),
        description = "Tap this button to add more currencies to your list.",
    )
}

@Composable
fun copyConversionTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 1,
        title = "Long-press to copy",
        description = "Long-press the conversion value to copy it to your clipboard.",
    )
}

@Composable
fun reorderCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 2,
        title = "Long-press & drag to reorder",
        description = "Long-press and drag the currency to change its order in your list.",
    )
}

@Composable
fun removeCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 3,
        title = "Swipe to remove",
        description = "Swipe the currency to remove it from your list.",
    )
}

@Composable
fun buildTapTargetDefinition(
    precedence: Int,
    title: String,
    description: String,
): TapTargetDefinition {
    return TapTargetDefinition(
        precedence = precedence,
        title = TextDefinition(
            text = title,
            textStyle = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        ),
        description = TextDefinition(
            text = description,
            textStyle = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        ),
        tapTargetStyle = TapTargetStyle(
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            tapTargetHighlightColor = MaterialTheme.colorScheme.inverseOnSurface,
            backgroundAlpha = 1f,
        ),
    )
}