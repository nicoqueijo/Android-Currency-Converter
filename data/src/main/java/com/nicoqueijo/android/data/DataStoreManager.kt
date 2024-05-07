package com.nicoqueijo.android.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStoreManager(context: Context) {

    private val Context.myPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")
    private val dataStore = context.myPreferencesDataStore

    suspend fun setFirstLaunch(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.FIRST_LAUNCH] = value
        }
    }

    suspend fun isFirstLaunch(): Boolean {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.FIRST_LAUNCH] ?: true
    }

    suspend fun setTimestampInSeconds(value: Long) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TIMESTAMP] = value
        }
    }

    suspend fun getTimestampInSeconds(): Long {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.TIMESTAMP]?.toLong() ?: Constants.NO_DATA
    }

    suspend fun isDataEmpty(): Boolean {
        return getTimeSinceLastUpdateInSeconds() == Constants.NO_DATA
    }

    suspend fun isDataStale(): Boolean {
        return getTimeSinceLastUpdateInSeconds() > Constants.TWENTY_FOUR_HOURS_IN_SECONDS
    }

    private suspend fun getTimeSinceLastUpdateInSeconds(): Long {
        return if (getTimestampInSeconds() != Constants.NO_DATA) {
            System.currentTimeMillis().toSeconds() - getTimestampInSeconds()
        } else {
            Constants.NO_DATA
        }
    }

    private object PreferencesKeys {
        val FIRST_LAUNCH = booleanPreferencesKey("first_launch")
        val TIMESTAMP = longPreferencesKey("timestamp")
    }

    private object Constants {
        const val TWENTY_FOUR_HOURS_IN_SECONDS = 86_400L
        const val NO_DATA = 0L
    }
}

// TODO: Possibly move these to core module
fun Long.toSeconds() = this / 1_000L

fun Long.toMillis() = this * 1_000L