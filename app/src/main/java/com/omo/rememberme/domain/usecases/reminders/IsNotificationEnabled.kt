package com.omo.rememberme.domain.usecases.reminders

import com.omo.rememberme.domain.manager.SettingsManager
import kotlinx.coroutines.flow.Flow

class IsNotificationEnabled(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(): Flow<Boolean> {
        return settingsManager.isNotificationEnabled()
    }
}