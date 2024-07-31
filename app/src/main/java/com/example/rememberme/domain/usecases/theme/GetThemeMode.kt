package com.example.rememberme.domain.usecases.theme

import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

class GetThemeMode(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(): Flow<ThemeMode> {
        return settingsManager.getThemeMode()
    }
}