package com.example.rememberme.domain.manager

import com.example.rememberme.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    suspend fun getThemeMode(): Flow<ThemeMode>
    suspend fun setThemeMode(themeMode: ThemeMode)
    suspend fun isDarkModeEnabled(): Flow<Boolean>
}