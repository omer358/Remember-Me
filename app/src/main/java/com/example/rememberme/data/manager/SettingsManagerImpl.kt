package com.example.rememberme.data.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.model.ThemeMode
import com.example.rememberme.utils.Constants
import com.example.rememberme.utils.Constants.REMEMBER_ME_SETTING_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsManagerImpl(
    private val context: Context
) : SettingsManager {
    override suspend fun getThemeMode(): Flow<ThemeMode> {
        return context.dataStore.data.map { preferences ->
            val ordinal = preferences[PreferencesKeys.UI_MODE] ?: ThemeMode.SYSTEM.ordinal
            ThemeMode.entries[ordinal]
        }
    }

    override suspend fun setThemeMode(themeMode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.UI_MODE] = when (themeMode) {
                ThemeMode.LIGHT -> ThemeMode.LIGHT.ordinal
                ThemeMode.DARK -> ThemeMode.DARK.ordinal
                ThemeMode.SYSTEM -> ThemeMode.SYSTEM.ordinal
            }
        }
    }

    override suspend fun isDarkModeEnabled(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            (preferences[PreferencesKeys.UI_MODE] == ThemeMode.DARK.ordinal)
        }
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = REMEMBER_ME_SETTING_NAME)

private object PreferencesKeys {
    val UI_MODE = intPreferencesKey(Constants.UI_MODE)
}