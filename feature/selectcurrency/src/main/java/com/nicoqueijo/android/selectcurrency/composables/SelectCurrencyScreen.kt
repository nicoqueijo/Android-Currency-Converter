@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.selectcurrency.composables

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.core.Currency
import com.nicoqueijo.android.selectcurrency.R
import com.nicoqueijo.android.selectcurrency.SelectCurrencyUiState
import com.nicoqueijo.android.selectcurrency.SelectCurrencyViewModel
import com.nicoqueijo.android.ui.AndroidCurrencyConverterTheme
import com.nicoqueijo.android.ui.DarkLightPreviews

@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCurrencyViewModel? = hiltViewModel(),
    onCurrencyClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()
    SelectCurrency(
        modifier = modifier,
        state = uiState?.value,
        onCurrencyClick = { onCurrencyClick?.invoke() },
        onCurrencyClick1 = { currency ->
            viewModel?.handleCurrencySelection(selectedCurrency = currency)
        },
        onSearchTermChange = { searchTerm ->
            viewModel?.handleSearchTermChange(searchTerm = searchTerm)
        },
    )
}

@Composable
fun SelectCurrency(
    modifier: Modifier = Modifier,
    state: SelectCurrencyUiState?,
    onCurrencyClick: (() -> Unit)? = null,
    onCurrencyClick1: ((selectedCurrency: Currency) -> Unit)? = null,
    onSearchTermChange: ((searchTerm: String) -> Unit)? = null,
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
                                modifier = Modifier.fillMaxWidth(),
                                query = state?.searchTerm ?: "",
                                onQueryChange = { queryChange ->
                                    onSearchTermChange?.invoke(queryChange)
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
                                        contentDescription = null
                                    )
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            onSearchTermChange?.invoke("")
                                        },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = null,
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
                modifier = Modifier.padding(paddingValues = innerPadding),
            ) {
                LazyColumn {
                    state?.filteredCurrencies?.forEach { currency ->
                        item {
                            SelectCurrencyRow(
                                state = currency,
                                onClick = {
                                    onCurrencyClick?.invoke()
                                    onCurrencyClick1?.invoke(currency)
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

@DarkLightPreviews
@Composable
fun SelectCurrencyScreenPreview() {
    val state = SelectCurrencyUiState(
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
    val state = SelectCurrencyUiState()
    AndroidCurrencyConverterTheme {
        SelectCurrency(state = state)
    }
}
