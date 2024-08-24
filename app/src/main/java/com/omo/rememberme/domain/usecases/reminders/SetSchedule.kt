package com.omo.rememberme.domain.usecases.reminders

import com.omo.rememberme.domain.manager.SettingsManager
import com.omo.rememberme.domain.model.RemindersRepetition

class SetSchedule(private val settingsManager: SettingsManager) {
    suspend operator fun invoke(remindersRepetition: RemindersRepetition) {
        settingsManager.setSchedules(remindersRepetition)
    }
}