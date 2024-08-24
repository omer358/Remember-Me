package com.omo.rememberme.domain.usecases.reminders

import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.model.RemindersRepetition
import kotlinx.coroutines.flow.Flow

class GetSchedule(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(): Flow<RemindersRepetition> {
        return settingsManager.getSchedules()
    }
}