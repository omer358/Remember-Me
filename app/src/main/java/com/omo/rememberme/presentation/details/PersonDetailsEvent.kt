package com.omo.rememberme.presentation.details

sealed class PersonDetailsEvent {
    data class LoadPerson(val personId: Long) : PersonDetailsEvent()
    data object EditPerson: PersonDetailsEvent()
    data object DeletePerson: PersonDetailsEvent()
}
