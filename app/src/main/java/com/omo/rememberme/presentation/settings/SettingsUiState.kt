package com.omo.rememberme.presentation.settings

import com.omo.rememberme.domain.model.RemindersRepetition
import com.omo.rememberme.domain.model.ThemeMode

data class SettingsUiState(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val remindersRepetition: RemindersRepetition = RemindersRepetition.OnceADay,
    val notificationsEnabled: Boolean = false,
)
