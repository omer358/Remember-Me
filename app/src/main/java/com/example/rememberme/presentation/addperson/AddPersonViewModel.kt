package com.example.rememberme.presentation.addperson

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.R
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddPersonViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddPersonUiState())
    val uiState: StateFlow<AddPersonUiState> = _uiState

    fun onEvent(event: AddPersonEvents) {
        when (event) {
            is AddPersonEvents.OnFirstNameChange -> {
                _uiState.value = _uiState.value.copy(firstName = event.firstName)
            }

            is AddPersonEvents.OnSecondNameChange -> {
                _uiState.value = _uiState.value.copy(secondName = event.secondName)
            }

            is AddPersonEvents.OnPlaceChange -> {
                _uiState.value = _uiState.value.copy(place = event.place)
            }

            is AddPersonEvents.OnTimeChange -> {
                _uiState.value = _uiState.value.copy(time = event.time)
            }

            is AddPersonEvents.OnNoteChange -> {
                _uiState.value = _uiState.value.copy(note = event.note)
            }

            is AddPersonEvents.OnGenderChange -> {
                _uiState.value = _uiState.value.copy(gender = event.gender)
            }

            is AddPersonEvents.OnAvatarChange -> {
                _uiState.value = _uiState.value.copy(avatar = event.avatar)
            }

            AddPersonEvents.OnSavePerson -> {
                savePerson()
            }
        }
    }

    private fun savePerson() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val person = People(
                    firstName = _uiState.value.firstName,
                    secondName = _uiState.value.secondName,
                    place = _uiState.value.place,
                    time = _uiState.value.time,
                    note = _uiState.value.note,
                    gender = _uiState.value.gender,
                    // TODO: get avatar from the ui
                    avatar = R.drawable.ic_m1
                )
                peopleUseCases.insertPerson(person)
            }
        }
    }
}