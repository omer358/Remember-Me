package com.omo.rememberme.domain.usecases.reminders

import com.omo.rememberme.domain.manager.SettingsManager

class SetNotificationEnabled(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsManager.saveNotificationStatus(enabled)
    }
}