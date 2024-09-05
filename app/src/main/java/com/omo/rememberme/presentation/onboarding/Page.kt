package com.omo.rememberme.presentation.onboarding

import androidx.annotation.DrawableRes
import com.omo.rememberme.R

data class Page (
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Remember your people",
        description = "Never miss a chance to reconnect. keep track of the people you have meet",
        image = R.drawable.people
    ),
    Page(
        title = "Personalized Reminders",
        description = "Create custom reminders for your contacts, and never forget their names",
        image = R.drawable.reminders
    ),
    Page(
        title = "Stay Organized, Stay Connected",
        description = "Keep track of important dates and keep your relationships strong with timely notifications.",
        image = R.drawable.organized
    )
)
