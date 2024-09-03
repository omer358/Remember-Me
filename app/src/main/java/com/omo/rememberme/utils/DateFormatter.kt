package com.omo.rememberme.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun formatElapsedTime(meetingTime: String): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
    val meetingDate = sdf.parse(meetingTime) ?: return "Invalid date"
    val now = Calendar.getInstance().time
    val diff = now.time - meetingDate.time

    // Convert days to milliseconds using Long to avoid overflow
    val oneMinuteMillis = 60 * 1000L
    val oneDayMillis = 24 * 60 * 60 * 1000L
    val twoDaysMillis = 2 * oneDayMillis
    val oneWeekMillis = 7 * oneDayMillis
    val oneMonthMillis = 365 * oneDayMillis

    return when {
        diff < oneMinuteMillis -> "Now" // Less than a minute
        diff < oneDayMillis -> "Today" // Less than a day
        diff < twoDaysMillis -> "Yesterday" // Less than two days
        diff < oneWeekMillis -> SimpleDateFormat("EEEE", Locale.getDefault()).format(meetingDate) // This week
        diff < oneMonthMillis -> SimpleDateFormat("dd MMM", Locale.getDefault()).format(meetingDate) // This month
        else -> SimpleDateFormat("yyyy", Locale.getDefault()).format(meetingDate) // Beyond this month
    }
}