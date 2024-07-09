package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
        title = stringResource(R.string.tap_target_add_title),
        description = stringResource(R.string.tap_target_add_description),
    )
}

@Composable
fun copyConversionTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 1,
        title = stringResource(R.string.tap_target_copy_title),
        description = stringResource(R.string.tap_target_copy_description),
    )
}

@Composable
fun reorderCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 2,
        title = stringResource(R.string.tap_target_reorder_title),
        description = stringResource(R.string.tap_target_reorder_description),
    )
}

@Composable
fun removeCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 3,
        title = stringResource(R.string.tap_target_remove_title),
        description = stringResource(R.string.tap_target_remove_description),
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