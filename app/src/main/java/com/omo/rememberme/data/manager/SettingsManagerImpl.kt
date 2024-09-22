package com.omo.rememberme.data.manager

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.model.RemindersRepetition
import com.omo.rememberme.domain.model.ThemeMode
import com.omo.rememberme.utils.Constants
import com.omo.rememberme.utils.Constants.REMEMBER_ME_SETTING_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsManagerImpl @Inject constructor(
    private val context: Context
) : SettingsManager {
    override suspend fun getThemeMode(): Flow<ThemeMode> {
        Log.d(TAG, "getThemeMode: ")
        val theme = context.dataStore.data.map { preferences ->
            val ordinal = preferences[PreferencesKeys.UI_MODE] ?: ThemeMode.SYSTEM.ordinal
            ThemeMode.entries[ordinal]
        }
        return theme
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        Log.d(TAG, "setThemeMode: the new theme is: $themeMode ")
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.UI_MODE] = when (themeMode) {
                ThemeMode.LIGHT -> ThemeMode.LIGHT.ordinal
                ThemeMode.DARK -> ThemeMode.DARK.ordinal
                ThemeMode.SYSTEM -> ThemeMode.SYSTEM.ordinal
            }
        }
    }

    override suspend fun isDarkModeEnabled(): Flow<Boolean> {
        Log.d(TAG, "isDarkModeEnabled: ")
        return context.dataStore.data.map { preferences ->
            (preferences[PreferencesKeys.UI_MODE] == ThemeMode.DARK.ordinal)
        }
    }

    override suspend fun getSchedules(): Flow<RemindersRepetition> {
        return context.dataStore.data.map {preferences ->
            val ordinal = preferences[PreferencesKeys.SCHEDULE] ?: RemindersRepetition.OnceADay.ordinal
            RemindersRepetition.entries[ordinal]
        }
    }

    override suspend fun setSchedules(schedules: RemindersRepetition) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SCHEDULE] = when (schedules) {
                RemindersRepetition.OnceADay -> RemindersRepetition.OnceADay.ordinal
                RemindersRepetition.ThreeADay -> RemindersRepetition.ThreeADay.ordinal
                RemindersRepetition.FiveADay -> RemindersRepetition.FiveADay.ordinal

            }
        }
    }

    override suspend fun saveNotificationStatus(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.REMINDERS_ENABLED] = enabled
        }
    }

    override suspend fun isNotificationEnabled(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.REMINDERS_ENABLED] ?: false
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = REMEMBER_ME_SETTING_NAME)

        private object PreferencesKeys {
            val UI_MODE = intPreferencesKey(Constants.UI_MODE)
            val SCHEDULE = intPreferencesKey(Constants.SCHEDULE)
            val REMINDERS_ENABLED = booleanPreferencesKey(Constants.REMINDERS_ENABLED)
        }

        private const val TAG = "SettingsManagerImpl"
    }
}


