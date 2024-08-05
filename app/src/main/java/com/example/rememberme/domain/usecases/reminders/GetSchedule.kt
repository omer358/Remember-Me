package com.example.rememberme.domain.usecases.reminders

import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.model.RemindersRepetition
import kotlinx.coroutines.flow.Flow

class GetSchedule(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(): Flow<RemindersRepetition> {
        return settingsManager.getSchedules()
    }
}