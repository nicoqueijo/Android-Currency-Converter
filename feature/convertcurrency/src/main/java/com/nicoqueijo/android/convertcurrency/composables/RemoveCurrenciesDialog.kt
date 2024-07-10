package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.nicoqueijo.android.convertcurrency.R
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

/**
 * Composable function for a dialog to confirm the removal of currencies.
 *
 * This function displays an [AlertDialog] with a title and two buttons: confirm and dismiss.
 * It handles user interactions for confirming or dismissing the action to remove currencies.
 *
 * @param modifier The modifier to be applied to the dialog.
 * @param onConfirmClick Lambda function to be invoked when the confirm button is clicked.
 * @param onDismissClick Lambda function to be invoked when the dismiss button is clicked or the dialog
 * is dismissed.
 */
@Composable
fun RemoveCurrenciesDialog(
    modifier: Modifier = Modifier,
    onConfirmClick: (() -> Unit)? = null,
    onDismissClick: (() -> Unit)? = null,
) {
    AlertDialog(
        modifier = modifier,
        text = {
            Text(
                text = stringResource(id = R.string.remove_currencies_dialog_title),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmClick?.invoke()
                }
            ) {
                Text(
                    text = stringResource(id = R.string.remove_currencies_dialog_confirm_label),
                    fontSize = 16.sp,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissClick?.invoke()
                },
            ) {
                Text(
                    text = stringResource(id = R.string.remove_currencies_dialog_dismiss_label),
                    fontSize = 16.sp,
                )
            }
        },
        onDismissRequest = {
            onDismissClick?.invoke()
        },
    )
}

@DarkLightPreviews
@Composable
fun RemoveCurrenciesDialogPreview() {
    AndroidCurrencyConverterTheme {
        RemoveCurrenciesDialog()
    }
}