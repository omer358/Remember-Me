package com.omo.rememberme.presentation.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omo.rememberme.domain.model.RemindersRepetition
import com.omo.rememberme.domain.model.ThemeMode
import com.omo.rememberme.domain.usecases.reminders.ReminderUseCases
import com.omo.rememberme.domain.usecases.theme.ThemeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val themeUseCases: ThemeUseCases,
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel initialized")
        getCurrentTheme()
        getCurrentRemindersRepetition()
        loadNotificationStatus()
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.ChangeTheme -> {
                Log.d(TAG, "onEvent: ChangeTheme to ${event.theme}")
                changeTheme(event.theme)
            }
            is SettingsEvent.GetTheme -> {
                Log.d(TAG, "onEvent: GetTheme")
                getCurrentTheme()
            }
            is SettingsEvent.ChangeRemindersRepetition -> {
                Log.d(TAG, "onEvent: ChangeRemindersRepetition to ${event.repetition}")
                changeRemindersRepetition(event.repetition)
            }
            is SettingsEvent.GetRemindersRepetition -> {
                Log.d(TAG, "onEvent: GetRemindersRepetition")
                getCurrentRemindersRepetition()
            }
            is SettingsEvent.LoadNotificationsStatus -> {
                loadNotificationStatus()
            }
            is SettingsEvent.ToggleNotifications -> {
                toggleNotification(event.enabled)
            }
        }
    }

    private fun changeTheme(theme: ThemeMode) {
        viewModelScope.launch {
            Log.d(TAG, "changeTheme: Setting theme to $theme")
            themeUseCases.setThemeMode(theme)
            getCurrentTheme()
        }
    }

    private fun getCurrentTheme() {
        viewModelScope.launch {
            Log.d(TAG, "getCurrentTheme: Fetching current theme")
            themeUseCases.getThemeMode().collect { currentTheme ->
                Log.d(TAG, "getCurrentTheme: Current theme is $currentTheme")
                _uiState.update { it.copy(theme = currentTheme) }
            }
        }
    }

    private fun changeRemindersRepetition(repetition: RemindersRepetition) {
        viewModelScope.launch {
            Log.d(TAG, "changeRemindersRepetition: Setting reminders repetition to $repetition")
            reminderUseCases.setSchedule(repetition)
            getCurrentRemindersRepetition()
        }
    }

    private fun getCurrentRemindersRepetition() {
        viewModelScope.launch {
            Log.d(TAG, "getCurrentRemindersRepetition: Fetching current reminders repetition")
            reminderUseCases.getSchedule().collect { currentRepetition ->
                Log.d(
                    TAG,
                    "getCurrentRemindersRepetition: Current reminders repetition is $currentRepetition"
                )
                _uiState.update { it.copy(remindersRepetition = currentRepetition) }
            }
        }
    }

    private fun loadNotificationStatus() {
        viewModelScope.launch {
            Log.d(TAG, "getCurrentNotificationsStatus: Fetching current Notifications status")

            reminderUseCases.isNotificationEnabled()
                .collect { isEnabled ->
                    Log.d(TAG, "loadNotificationStatus: Current notification status is: $isEnabled")
                    _uiState.update { it.copy(notificationsEnabled = isEnabled)}
                }
        }
    }

    private fun toggleNotification(isEnabled: Boolean) {
        viewModelScope.launch {
            reminderUseCases.setNotificationEnabled(isEnabled)
            _uiState.value = _uiState.value.copy(notificationsEnabled = isEnabled)
            loadNotificationStatus()
        }
    }

    companion object {
        private const val TAG = "SettingsViewModel"
    }
}
