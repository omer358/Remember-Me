package com.omo.rememberme.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omo.rememberme.data.manager.NotificationService
import com.omo.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases,
    private val notificationManager: NotificationService
) : ViewModel() {
    private val _uiState = MutableStateFlow(PersonDetailsUiState())
    val uiState: StateFlow<PersonDetailsUiState> = _uiState

    fun onEvent(event: PersonDetailsEvent) {
        when (event) {
            is PersonDetailsEvent.LoadPerson -> {
                loadPerson(event.personId)
            }

            is PersonDetailsEvent.EditPerson -> {

            }

            is PersonDetailsEvent.DeletePerson -> {
                Log.d(TAG, "Delete event received")
                deletePerson()
            }
        }
    }

    private fun loadPerson(personId: Long) {
        _uiState.update { _uiState.value.copy(isLoading = true) }
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

    private fun editPerson() {

    }

    private fun deletePerson() {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Deleting person with= ID: ${_uiState.value.person!!.id}")
                peopleUseCases.deletePersonById(_uiState.value.person!!.id)
                _uiState.update { PersonDetailsUiState() } // Clear UI state after deletion
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting person: ${e.message}")
                _uiState.update { _uiState.value.copy(error = e.message) }
            }
        }
    }

     fun sendNotification(){
        notificationManager.showNotification(_uiState.value.person!!)
    }

    companion object {
        private const val TAG = "PersonDetailsViewModel"
    }
}
