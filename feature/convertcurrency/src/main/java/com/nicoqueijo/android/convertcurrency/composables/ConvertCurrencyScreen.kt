package com.nicoqueijo.android.convertcurrency.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.convertcurrency.ConvertCurrencyViewModel
import com.nicoqueijo.android.convertcurrency.Digit

// TODO: Style this nicely
@Composable
fun ConvertCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: ConvertCurrencyViewModel? = hiltViewModel(),
    onFabClick: (() -> Unit)? = null,
    onDigitButtonClick: ((Digit) -> Unit)? = null,
    onDecimalPointButtonClick: (() -> Unit)? = null,
    onBackspaceButtonClick: (() -> Unit)? = null,
) {

    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                when (uiState?.value?.selectedCurrencies?.isEmpty()) {
                    true -> {
                        EmptyListIndicator()
                    }

                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            uiState?.value?.selectedCurrencies?.forEach { currency ->
                                item {
                                    ConvertCurrencyRow(state = currency)
                                }
                            }
                        }
                    }
                }
                FloatingActionButton(
                    onClick = { onFabClick?.invoke() },
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
            Box {
                NumberPad(
                    state = NumberPadState(
                        onDigitButtonClick = null, // TODO: Pass the correct implementation
                        onDecimalPointButtonClick = null, // TODO: Pass the correct implementation
                        onBackspaceButtonClick = null, // TODO: Pass the correct implementation
                    )
                )
            }
        }
    }
}
