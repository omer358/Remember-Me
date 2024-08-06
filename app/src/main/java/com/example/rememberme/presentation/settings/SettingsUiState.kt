package com.example.rememberme.presentation.settings

import com.example.rememberme.domain.model.RemindersRepetition
import com.example.rememberme.domain.model.ThemeMode

data class SettingsUiState(
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val remindersRepetition: RemindersRepetition = RemindersRepetition.OnceADay
)
