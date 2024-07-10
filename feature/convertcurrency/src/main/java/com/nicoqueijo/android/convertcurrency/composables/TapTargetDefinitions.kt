package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.nicoqueijo.android.convertcurrency.R
import com.psoffritti.taptargetcompose.TapTargetDefinition
import com.psoffritti.taptargetcompose.TapTargetStyle
import com.psoffritti.taptargetcompose.TextDefinition

/**
 * Composable function to define a tap target for adding a currency.
 *
 * This function builds and returns a [TapTargetDefinition] for the "add currency" action,
 * including the title and description.
 *
 * @return The tap target definition for adding a currency.
 */
@Composable
fun addCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 0,
        title = stringResource(R.string.tap_target_add_title),
        description = stringResource(R.string.tap_target_add_description),
    )
}

/**
 * Composable function to define a tap target for copying a conversion value.
 *
 * This function builds and returns a [TapTargetDefinition] for the "copy conversion" action,
 * including the title and description.
 *
 * @return The tap target definition for copying a conversion value.
 */
@Composable
fun copyConversionTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 1,
        title = stringResource(R.string.tap_target_copy_title),
        description = stringResource(R.string.tap_target_copy_description),
    )
}

/**
 * Composable function to define a tap target for reordering currencies.
 *
 * This function builds and returns a [TapTargetDefinition] for the "reorder currency" action,
 * including the title and description.
 *
 * @return The tap target definition for reordering currencies.
 */
@Composable
fun reorderCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 2,
        title = stringResource(R.string.tap_target_reorder_title),
        description = stringResource(R.string.tap_target_reorder_description),
    )
}

/**
 * Composable function to define a tap target for removing a currency.
 *
 * This function builds and returns a [TapTargetDefinition] for the "remove currency" action,
 * including the title and description.
 *
 * @return The tap target definition for removing a currency.
 */
@Composable
fun removeCurrencyTapTargetDefinition(): TapTargetDefinition {
    return buildTapTargetDefinition(
        precedence = 3,
        title = stringResource(R.string.tap_target_remove_title),
        description = stringResource(R.string.tap_target_remove_description),
    )
}

/**
 * Composable function to build a tap target definition.
 *
 * This function constructs a [TapTargetDefinition] with the specified precedence, title, and description.
 * It is used by the other tap target definition functions to create consistent definitions.
 *
 * @param precedence The precedence of the tap target.
 * @param title The title text for the tap target.
 * @param description The description text for the tap target.
 * @return The constructed tap target definition.
 */
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