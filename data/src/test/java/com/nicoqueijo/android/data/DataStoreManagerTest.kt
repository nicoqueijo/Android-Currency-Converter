package com.nicoqueijo.android.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nicoqueijo.android.core.TimeProvider
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class DataStoreManagerTest {

    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var timeProvider: TimeProvider
    private lateinit var subject: DataStoreManager

    @BeforeEach
    fun setUp() {
        dataStore = mockk()
        timeProvider = mockk()
        subject = DataStoreManager(
            dataStore = dataStore,
            timeProvider = timeProvider,
        )
    }

    @Nested
    inner class IsFirstLaunch {

        @Test
        fun `given no preference set, when isFirstLaunch called, then returns true`() = runTest {
            val preferences: Preferences = mockk {
                every {
                    get(DataStoreManager.PreferencesKeys.FIRST_LAUNCH)
                } returns null
            }
            coEvery {
                dataStore.data
            } returns flowOf(preferences)

            val actual = subject.isFirstLaunch()

            actual.shouldBeTrue()
        }

        @Test
        fun `given first launch preference is true, when isFirstLaunch called, then returns true`() =
            runTest {
                val preferences: Preferences = mockk {
                    every {
                        get(DataStoreManager.PreferencesKeys.FIRST_LAUNCH)
                    } returns true
                }
                coEvery {
                    dataStore.data
                } returns flowOf(preferences)

                val actual = subject.isFirstLaunch()

                actual.shouldBeTrue()
            }

        @Test
        fun `given first launch preference is false, when isFirstLaunch called, then returns false`() =
            runTest {
                val preferences: Preferences = mockk {
                    every {
                        get(DataStoreManager.PreferencesKeys.FIRST_LAUNCH)
                    } returns false
                }
                coEvery {
                    dataStore.data
                } returns flowOf(preferences)

                val actual = subject.isFirstLaunch()

                actual.shouldBeFalse()
            }
    }

    @Nested
    inner class GetTimestampInSeconds {

        @Test
        fun `given no timestamp set, when getTimestampInSeconds called, then returns NO_DATA`() =
            runTest {
                val preferences: Preferences = mockk {
                    every {
                        get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                    } returns null
                }
                coEvery {
                    dataStore.data
                } returns flowOf(preferences)

                val actual = subject.getTimestampInSeconds()

                actual.shouldBe(expected = DataStoreManager.Constants.NO_DATA)
            }

        @Test
        fun `given timestamp is NO_DATA, when getTimestampInSeconds called, then returns NO_DATA`() =
            runTest {
                val preferences: Preferences = mockk {
                    every {
                        get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                    } returns DataStoreManager.Constants.NO_DATA
                }
                coEvery {
                    dataStore.data
                } returns flowOf(preferences)

                val actual = subject.getTimestampInSeconds()

                actual.shouldBe(expected = DataStoreManager.Constants.NO_DATA)
            }

        @Test
        fun `given timestamp is set, when getTimestampInSeconds called, then returns timestamp`() =
            runTest {
                val preferences: Preferences = mockk {
                    every {
                        get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                    } returns 123_456_789L
                }
                coEvery {
                    dataStore.data
                } returns flowOf(preferences)

                val actual = subject.getTimestampInSeconds()

                actual.shouldBe(expected = 123_456_789L)
            }
    }

    @Nested
    inner class IsDataEmpty {

        @Test
        fun `given no preference set, when isDataEmpty called, then returns true`() = runTest {
            val preferences: Preferences = mockk {
                every {
                    get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                } returns null
            }
            coEvery { dataStore.data } returns flowOf(preferences)

            val actual = subject.isDataEmpty()

            actual.shouldBeTrue()
        }

        @Test
        fun `given timestamp has no data, when isDataEmpty called, then returns true`() = runTest {
            val preferences: Preferences = mockk {
                every {
                    get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                } returns DataStoreManager.Constants.NO_DATA
            }
            coEvery { dataStore.data } returns flowOf(preferences)

            val actual = subject.isDataEmpty()

            actual.shouldBeTrue()
        }

        @Test
        fun `given timestamp data exists, when isDataEmpty called, then returns false`() = runTest {
            val preferences: Preferences = mockk {
                every {
                    get(DataStoreManager.PreferencesKeys.TIMESTAMP)
                } returns 123_456_789L
            }
            coEvery {
                dataStore.data
            } returns flowOf(preferences)
            every {
                timeProvider.currentTimeMillis()
            } returns 987_654_321L

            val actual = subject.isDataEmpty()

            actual.shouldBeFalse()
        }
    }
}
