package com.example.rememberme.domain.usecases.theme

import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.model.ThemeMode

class SetThemeMode(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(themeMode: ThemeMode) {
        settingsManager.setThemeMode(themeMode)
    }
}
