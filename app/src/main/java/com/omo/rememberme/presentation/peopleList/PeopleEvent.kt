package com.omo.rememberme.presentation.peopleList

sealed class PeopleEvent {
    data class DeletePerson(val personId: Long) : PeopleEvent()
}
