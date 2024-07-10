package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.Red
import com.nicoqueijo.android.ui.XXL
import com.nicoqueijo.android.ui.XXS

/**
 * Composable function for a delete row background in a swipe-to-dismiss layout.
 *
 * This function provides a background for a row that can be swiped to dismiss, displaying delete icons
 * based on the swipe direction.
 *
 * @param modifier The modifier to be applied to the row background.
 * @param dismissDirection The direction in which the row is being dismissed, used to control the visibility
 * of the delete icons.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteRow(
    modifier: Modifier = Modifier,
    dismissDirection: SwipeToDismissBoxValue,
) {
    Surface(
        modifier = modifier,
        color = Red,
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(height = XXL)
                .padding(horizontal = XXS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            DeleteIcon(hideIcon = dismissDirection == SwipeToDismissBoxValue.EndToStart)
            DeleteIcon(hideIcon = dismissDirection == SwipeToDismissBoxValue.StartToEnd)
        }
    }
}

/**
 * Composable function for a delete icon.
 *
 * This function displays a delete icon that can be hidden or shown based on the [hideIcon] parameter.
 *
 * @param modifier The modifier to be applied to the icon.
 * @param hideIcon A boolean flag to control the visibility of the icon. If true, the icon will be hidden.
 */
@Composable
fun DeleteIcon(
    modifier: Modifier = Modifier,
    hideIcon: Boolean
) {
    Icon(
        modifier = modifier
            .size(size = L)
            .alpha(
                alpha = if (hideIcon) 0f else 1f
            ),
        imageVector = Icons.Filled.Delete,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.primary,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DarkLightPreviews
@Composable
fun DeleteRowStartSwipePreview() {
    AndroidCurrencyConverterTheme {
        DeleteRow(dismissDirection = SwipeToDismissBoxValue.StartToEnd)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@DarkLightPreviews
@Composable
fun DeleteRowEndSwipePreview() {
    AndroidCurrencyConverterTheme {
        DeleteRow(dismissDirection = SwipeToDismissBoxValue.EndToStart)
    }
}
