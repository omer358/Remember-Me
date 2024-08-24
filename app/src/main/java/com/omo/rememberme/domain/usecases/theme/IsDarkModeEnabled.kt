package com.omo.rememberme.domain.usecases.theme

import com.omo.rememberme.domain.manager.SettingsManager
import kotlinx.coroutines.flow.Flow

class IsDarkModeEnabled(private val settingsManager: SettingsManager) {
    suspend fun execute(): Flow<Boolean> {
        return settingsManager.isDarkModeEnabled()
    }
}