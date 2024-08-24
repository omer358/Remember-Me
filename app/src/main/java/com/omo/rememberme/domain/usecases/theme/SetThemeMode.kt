package com.omo.rememberme.domain.usecases.theme

import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.model.ThemeMode

class SetThemeMode(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        settingsManager.setThemeMode(themeMode)
    }
}
