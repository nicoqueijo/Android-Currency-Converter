package com.nicoqueijo.android.selectcurrency.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.selectcurrency.R
import com.nicoqueijo.android.selectcurrency.SelectCurrencyViewModel
import com.nicoqueijo.android.selectcurrency.UiEvent
import com.nicoqueijo.android.selectcurrency.UiState
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews
import com.nicoqueijo.android.ui.L
import com.nicoqueijo.android.ui.S
import com.nicoqueijo.android.ui.XL

@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCurrencyViewModel? = hiltViewModel(),
    onCurrencyClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()?.value
    SelectCurrency(
        modifier = modifier,
        state = uiState,
        onCurrencyClick = {
            onCurrencyClick?.invoke()
        },
        onEvent = { event ->
            viewModel?.onEvent(event = event)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectCurrency(
    modifier: Modifier = Modifier,
    state: UiState?,
    onCurrencyClick: (() -> Unit)? = null,
    onEvent: ((UiEvent) -> Unit)? = null,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            DockedSearchBar(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = S),
                                query = state?.searchTerm ?: "",
                                onQueryChange = { queryChange ->
                                    onEvent?.invoke(
                                        UiEvent.SearchTermChange(searchTerm = queryChange)
                                    )
                                },
                                onSearch = {
                                    keyboardController?.hide()
                                },
                                active = false,
                                onActiveChange = { },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.search_hint))
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            onEvent?.invoke(
                                                UiEvent.SearchTermChange(searchTerm = "")
                                            )
                                        },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                    )
                                }
                            ) { }
                        },
                    )
                    HorizontalDivider()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(paddingValues = innerPadding),
            ) {
                if (state?.isSearchResultEmpty == true) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                horizontal = L,
                                vertical = XL,
                            ),
                        text = stringResource(
                            id = R.string.no_results_label,
                            state.searchTerm
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        state?.filteredCurrencies?.forEach { currency ->
                            item {
                                SelectCurrencyRow(
                                    modifier = Modifier.animateItem(),
                                    state = currency,
                                    onClick = {
                                        onEvent?.invoke(UiEvent.SelectCurrency(currency = currency))
                                        onCurrencyClick?.invoke()
                                    },
                                )
                                HorizontalDivider()
                            }
                        }
                    }
                }
            }
        }
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    val state = UiState(
        filteredCurrencies = listOf(
            Currency(
                currencyCode = "USD_ARS",
                exchangeRate = 905.75429,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_CLP",
                exchangeRate = 935.45,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_COP",
                exchangeRate = 4121.178068,
            ),
            Currency(
                currencyCode = "USD_CUP",
                exchangeRate = 25.75,
            ),
            Currency(
                currencyCode = "USD_DOP",
                exchangeRate = 59.183449,
            ),
            Currency(
                currencyCode = "USD_MXN",
                exchangeRate = 18.4103,
                isSelected = true,
            ),
            Currency(
                currencyCode = "USD_UYU",
                exchangeRate = 39.312329,
            ),
        ),
        searchTerm = "peso"
    )
    AndroidCurrencyConverterTheme {
        SelectCurrency(state = state)
    }
}

@DarkLightPreviews
@Composable
fun SelectCurrencyEmptyScreenPreview() {
    val state = UiState(
        searchTerm = "german deutsche",
        isSearchResultEmpty = true,
    )
    AndroidCurrencyConverterTheme {
        SelectCurrency(state = state)
    }
}
