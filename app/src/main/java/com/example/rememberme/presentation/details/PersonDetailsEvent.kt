package com.example.rememberme.presentation.details

sealed class PersonDetailsEvent {
    data class LoadPerson(val personId: Long) : PersonDetailsEvent()
    data object NavigateUp : PersonDetailsEvent()
}
