package com.nicoqueijo.android.data

import com.nicoqueijo.android.core.model.Currency
import com.nicoqueijo.android.network.KtorClient
import com.nicoqueijo.android.network.model.ApiOperation
import com.nicoqueijo.android.network.model.OpenExchangeRatesEndPoint
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyRepositoryTest {

    private lateinit var ktorClient: KtorClient
    private lateinit var currencyDao: CurrencyDao
    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var subject: CurrencyRepository

    @BeforeEach
    fun setUp() {
        ktorClient = mockk()
        dataStoreManager = mockk()
        currencyDao = mockk()
        subject = CurrencyRepository(
            ktorClient = ktorClient,
            currencyDao = currencyDao,
            dispatcher = UnconfinedTestDispatcher(),
            dataStoreManager = dataStoreManager,
        )
    }

    @Nested
    inner class GetExchangeRates {

        @Test
        fun `given API call is successful, when getExchangeRates is called, then return success`() =
            runTest {
                val data = OpenExchangeRatesEndPoint()
                coEvery {
                    ktorClient.getExchangeRates()
                } returns ApiOperation.Success(
                    data = data
                )

                val actual = subject.getExchangeRates()
                val expected = ApiOperation.Success(
                    data = data
                )

                actual.shouldBe(expected)
            }

        @Test
        fun `given API call fails, when getExchangeRates is called, then return failure`() =
            runTest {
                val exception = Exception()
                coEvery {
                    ktorClient.getExchangeRates()
                } returns ApiOperation.Failure(
                    exception = exception
                )

                val actual = subject.getExchangeRates()
                val expected = ApiOperation.Failure<OpenExchangeRatesEndPoint>(
                    exception = exception
                )

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class UpsertCurrency {

        @Test
        fun `given a currency, when upsertCurrency is called, then dao upsertCurrency is invoked`() =
            runTest {
                val currency = Currency(
                    currencyCode = "USD",
                    exchangeRate = 1.0
                )
                coEvery {
                    currencyDao.upsertCurrency(currency = currency)
                }.just(Runs)

                subject.upsertCurrency(currency = currency)

                coVerify(exactly = 1) {
                    currencyDao.upsertCurrency(currency = currency)
                }
            }
    }

    @Nested
    inner class UpsertCurrencies {

        @Test
        fun `given a list of currencies, when upsertCurrencies is called, then dao upsertCurrencies is invoked`() =
            runTest {
                val currencies = listOf(
                    Currency(
                        currencyCode = "USD",
                        exchangeRate = 1.0
                    ),
                    Currency(
                        currencyCode = "CAD",
                        exchangeRate = 1.3811
                    )
                )
                coEvery {
                    currencyDao.upsertCurrencies(
                        currencies = currencies
                    )
                }.just(Runs)

                subject.upsertCurrencies(currencies = currencies)

                coVerify(exactly = 1) {
                    currencyDao.upsertCurrencies(currencies = currencies)
                }
            }
    }

    @Nested
    inner class UpdateExchangeRates {

        @Test
        fun `given a list of currencies, when updateExchangeRates is called, then dao updateExchangeRates is invoked`() =
            runTest {
                val currencies = listOf(
                    Currency(
                        currencyCode = "USD",
                        exchangeRate = 1.0
                    ),
                    Currency(
                        currencyCode = "EUR",
                        exchangeRate = 0.9032
                    )
                )
                coEvery {
                    currencyDao.updateExchangeRates(
                        currencies = currencies
                    )
                }.just(Runs)

                subject.updateExchangeRates(currencies = currencies)

                coVerify(exactly = 1) {
                    currencyDao.updateExchangeRates(currencies = currencies)
                }
            }
    }

    @Nested
    inner class GetCurrency {

        @Test
        fun `given a currency code, when getCurrency is called, then dao getCurrency is invoked and correct currency is returned`() =
            runTest {
                val currencyCode = "USD"
                val expected = Currency(
                    currencyCode = "USD",
                    exchangeRate = 1.0
                )
                coEvery {
                    currencyDao.getCurrency(currencyCode = currencyCode)
                } returns expected

                val actual = subject.getCurrency(currencyCode = currencyCode)

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class GetAllCurrencies {

        @Test
        fun `given dao returns list of currencies, when getAllCurrencies is called, then correct list is returned`() =
            runTest {
                val expected = listOf(
                    Currency(
                        currencyCode = "USD",
                        exchangeRate = 1.0
                    ),
                    Currency(
                        currencyCode = "EUR",
                        exchangeRate = 0.9
                    )
                )
                coEvery {
                    currencyDao.getAllCurrencies()
                } returns expected

                val actual = subject.getAllCurrencies()

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class GetSelectedCurrencies {

        @Test
        fun `given dao returns flow of currencies, when getSelectedCurrencies is called, then correct flow is returned`() =
            runTest {
                val expected = flowOf(
                    listOf(
                        Currency(
                            currencyCode = "USD",
                            exchangeRate = 1.0
                        ),
                        Currency(
                            currencyCode = "EUR", exchangeRate = 0.9
                        )
                    )
                )
                coEvery {
                    currencyDao.getSelectedCurrencies()
                } returns expected

                val actual = subject.getSelectedCurrencies()

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class GetSelectedCurrencyCount {

        @Test
        fun `given dao returns currency count, when getSelectedCurrencyCount is called, then correct count is returned`() =
            runTest {
                val expected = 5
                coEvery {
                    currencyDao.getSelectedCurrencyCount()
                } returns expected

                val actual = subject.getSelectedCurrencyCount()

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class SetFirstLaunch {

        @ParameterizedTest
        @ValueSource(booleans = [true, false])
        fun `given boolean value, when setFirstLaunch is called, then correct value is passed to dataStoreManager`(
            value: Boolean
        ) = runTest {
            coEvery {
                dataStoreManager.setFirstLaunch(
                    value = value
                )
            }.just(Runs)

            subject.setFirstLaunch(value = value)

            coVerify(exactly = 1) {
                dataStoreManager.setFirstLaunch(
                    value = value
                )
            }
        }
    }

    @Nested
    inner class IsFirstLaunch {

        @ParameterizedTest
        @ValueSource(booleans = [true, false])
        fun `given boolean return value, when isFirstLaunch is called, then correct value is returned`(
            expected: Boolean
        ) = runTest {
            coEvery {
                dataStoreManager.isFirstLaunch()
            }.returns(expected)

            val actual = subject.isFirstLaunch()

            actual.shouldBe(expected)
        }
    }

    @Nested
    inner class SetTimestampInSeconds {

        @Test
        fun `given long value, when setTimestampInSeconds is called, then correct value is passed to dataStoreManager`() =
            runTest {
                val value = 123_456_789L
                coEvery {
                    dataStoreManager.setTimestampInSeconds(
                        value = value
                    )
                }.just(Runs)

                subject.setTimestampInSeconds(value = value)

                coVerify(exactly = 1) {
                    dataStoreManager.setTimestampInSeconds(
                        value = value
                    )
                }
            }
    }

    @Nested
    inner class GetTimestampInSeconds {

        @Test
        fun `given a timestamp, when getTimestampInSeconds is called, then correct value is returned`() =
            runTest {
                val expected = 123_456_789L
                coEvery {
                    dataStoreManager.getTimestampInSeconds()
                }.returns(expected)

                val actual = subject.getTimestampInSeconds()

                actual.shouldBe(expected)
            }
    }

    @Nested
    inner class IsDataEmpty {

        @ParameterizedTest
        @ValueSource(booleans = [true, false])
        fun `given a boolean value, when isDataEmpty is called, then correct value is returned`(
            expected: Boolean
        ) = runTest {
            coEvery {
                dataStoreManager.isDataEmpty()
            }.returns(expected)

            val actual = subject.isDataEmpty()

            actual.shouldBe(expected)
        }
    }

    @Nested
    inner class IsDataStale {

        @ParameterizedTest
        @ValueSource(booleans = [true, false])
        fun `given a boolean value, when isDataStale is called, then correct value is returned`(
            expected: Boolean
        ) = runTest {
            coEvery {
                dataStoreManager.isDataStale()
            }.returns(expected)

            val actual = subject.isDataStale()

            actual.shouldBe(expected)
        }
    }
}
