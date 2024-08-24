package com.omo.rememberme.domain.usecases.theme

import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

class GetThemeMode(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(): Flow<ThemeMode> {
        return settingsManager.getThemeMode()
    }
}