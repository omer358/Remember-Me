package com.example.rememberme.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonDetailsUiState())
    val uiState: StateFlow<PersonDetailsUiState> = _uiState

    fun onEvent(event: PersonDetailsEvent) {
        when (event) {
            is PersonDetailsEvent.LoadPerson -> {
                loadPerson(event.personId)
            }
            is PersonDetailsEvent.NavigateUp -> {
                // Handle navigation
            }
        }
    }

    private fun loadPerson(personId: Long) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                peopleUseCases.getPersonById(personId).collect { person ->
                    _uiState.value = PersonDetailsUiState(person = person)
                }
            } catch (e: Exception) {
                _uiState.value = PersonDetailsUiState(error = e.message)
            }
        }
    }
}
