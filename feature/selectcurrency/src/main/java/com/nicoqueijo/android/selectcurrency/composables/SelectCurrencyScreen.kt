@file:OptIn(ExperimentalMaterial3Api::class)

package com.nicoqueijo.android.selectcurrency.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nicoqueijo.android.selectcurrency.R
import com.nicoqueijo.android.selectcurrency.SelectCurrencyViewModel

@Composable
fun SelectCurrencyScreen(
    modifier: Modifier = Modifier,
    viewModel: SelectCurrencyViewModel? = hiltViewModel(),
    onCurrencyClick: (() -> Unit)? = null,
) {
    val uiState = viewModel?.uiState?.collectAsStateWithLifecycle()
    val keyboardController = LocalSoftwareKeyboardController.current
    var searchTerm by remember { mutableStateOf("") }
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            DockedSearchBar(
                                modifier = Modifier.fillMaxWidth(),
                                query = searchTerm,
                                onQueryChange = { queryChange ->
                                    searchTerm = queryChange
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
                                        modifier = Modifier.clickable { searchTerm = "" },
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
            LazyColumn(
                modifier = Modifier.padding(paddingValues = innerPadding),
            ) {
                uiState?.value?.filteredCurrencies?.forEach { currency ->
                    item {
                        SelectCurrencyRow(
                            state = currency,
                            onClick = {
                                onCurrencyClick?.invoke()
                                viewModel.handleCurrencySelection(selectedCurrency = currency)
                            },
                        )
                        HorizontalDivider()
                    }
                }
            }
        }
    }
}
