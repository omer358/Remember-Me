package com.example.rememberme.presentation.settings

import com.example.rememberme.domain.model.RemindersRepetition
import com.example.rememberme.domain.model.ThemeMode

sealed class SettingsEvent {
    data class ChangeTheme(val theme: ThemeMode) : SettingsEvent()
    data object GetTheme : SettingsEvent()
    data class ChangeRemindersRepetition(val repetition: RemindersRepetition) : SettingsEvent()
    data object GetRemindersRepetition : SettingsEvent()
}