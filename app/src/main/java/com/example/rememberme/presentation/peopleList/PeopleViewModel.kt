package com.example.rememberme.presentation.peopleList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases
): ViewModel() {
    val state: StateFlow<PeopleState> = peopleUseCases.getAllPeople()
        .map { peopleList ->
            PeopleState(people = peopleList, isLoading = false)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PeopleState(isLoading = true)
        )
    fun onEvent(event: PeopleEvent) {
        when (event) {
            is PeopleEvent.DeletePerson -> {
                viewModelScope.launch {
                    peopleUseCases.deletePersonById(event.personId)
                }
            }
        }
    }
}