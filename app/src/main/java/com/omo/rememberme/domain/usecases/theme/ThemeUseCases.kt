package com.omo.rememberme.domain.usecases.theme

data class ThemeUseCases(
    val getThemeMode: GetThemeMode,
    val setThemeMode: SetThemeMode,
    val isDarkModeEnabled: IsDarkModeEnabled
)