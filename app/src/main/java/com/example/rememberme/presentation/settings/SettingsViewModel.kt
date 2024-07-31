package com.example.rememberme.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.model.ThemeMode
import com.example.rememberme.domain.usecases.theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeUseCases: ThemeUseCases
): ViewModel() {
    private val _themeMode = MutableStateFlow(ThemeMode.SYSTEM)
    val themeMode:StateFlow<ThemeMode> = _themeMode

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeTheme -> {
                Log.d(TAG, "onEvent: ${event.theme}")
                changeTheme(event.theme)
            }
            is SettingsEvent.GetTheme -> {
                Log.d(TAG, "onEvent: get theme")
                getTheme()
            }
        }
    }

    private fun getTheme() {
        viewModelScope.launch {
            _themeMode.update {
                themeUseCases.getThemeMode().first()
            }
            Log.d(TAG, "getTheme: ${_themeMode.value}")
        }
    }

    private fun changeTheme(themeMode: ThemeMode) {
        viewModelScope.launch {
            themeUseCases.setThemeMode(themeMode)
            _themeMode.update {
                themeMode
            }
            Log.d(TAG, "changeTheme: $themeMode")
        }
    }
    companion object {
        private const val TAG = "SettingsViewModel"
    }
}