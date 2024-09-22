package com.omo.rememberme.domain.usecases.reminders

data class ReminderUseCases(
    val setSchedule: SetSchedule,
    val getSchedule: GetSchedule,
    val setNotificationEnabled: SetNotificationEnabled,
    val isNotificationEnabled: IsNotificationEnabled

)