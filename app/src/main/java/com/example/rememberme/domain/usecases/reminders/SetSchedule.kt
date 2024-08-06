package com.example.rememberme.domain.usecases.reminders

import com.example.rememberme.domain.manager.SettingsManager
import com.example.rememberme.domain.model.RemindersRepetition

class SetSchedule(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(remindersRepetition: RemindersRepetition) {
        settingsManager.setSchedules(remindersRepetition)
    }
}