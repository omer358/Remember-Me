package com.example.rememberme.presentation.addperson

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.usecases.add_person.AddPersonUseCases
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPersonViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases,
    private val addPersonUseCases: AddPersonUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPersonUiState())
    val uiState: StateFlow<AddPersonUiState> = _uiState

    private val _isPersonSaved = MutableStateFlow(false)
    val isPersonSaved: StateFlow<Boolean> = _isPersonSaved

    fun onEvent(event: AddPersonEvents) {
        when (event) {
            is AddPersonEvents.OnFirstNameChange -> {
                _uiState.value =
                    _uiState.value.copy(firstName = event.firstName, firstNameError = null)
            }

            is AddPersonEvents.OnSecondNameChange -> {
                _uiState.value =
                    _uiState.value.copy(secondName = event.secondName, secondNameError = null)
            }

            is AddPersonEvents.OnPlaceChange -> {
                _uiState.value = _uiState.value.copy(place = event.place, placeError = null)
            }

            is AddPersonEvents.OnTimeChange -> {
                _uiState.value = _uiState.value.copy(time = event.time)
            }

            is AddPersonEvents.OnNoteChange -> {
                _uiState.value = _uiState.value.copy(note = event.note)
            }

            is AddPersonEvents.OnGenderChange -> {
                _uiState.value = _uiState.value.copy(gender = event.gender, genderError = null)
            }

            is AddPersonEvents.OnAvatarChange -> {
                _uiState.value = _uiState.value.copy(avatar = event.avatar)
            }

            AddPersonEvents.OnSavePerson -> {
                if (_uiState.value.id != null) {
                    updatePerson()
                } else {
                    savePerson()
                }
            }
        }
    }

    fun loadPersonDetails(personId: Long) {
        viewModelScope.launch {
            val person = peopleUseCases.getPersonById(personId)
            person.collect { person ->
                if (person != null) {
                    _uiState.value = _uiState.value.copy(
                        id = person.id,
                        firstName = person.firstName,
                        secondName = person.secondName,
                        place = person.place,
                        gender = person.gender,
                        avatar = person.avatar,
                        time = person.time,
                        note = person.note,
                    )
                }
            }
        }
    }

    fun resetForm() {
        _uiState.value = AddPersonUiState()
    }

    private fun savePerson() {
        if (validateInput()) {
            viewModelScope.launch {
                val person = People(
                    firstName = _uiState.value.firstName,
                    secondName = _uiState.value.secondName,
                    place = _uiState.value.place,
                    time = _uiState.value.time,
                    note = _uiState.value.note,
                    gender = _uiState.value.gender,
                    avatar = _uiState.value.avatar
                )
                peopleUseCases.insertPerson(person)
            }
            _isPersonSaved.value = true
        }
    }

    private fun updatePerson() {
        if (validateInput()) {
            Log.i(TAG, "validateInput on update: input are valid!")
            viewModelScope.launch {
                val person: People = _uiState.value.id?.let {
                    People(
                        id = it,
                        firstName = _uiState.value.firstName,
                        secondName = _uiState.value.secondName,
                        place = _uiState.value.place,
                        time = _uiState.value.time,
                        note = _uiState.value.note,
                        gender = _uiState.value.gender,
                        avatar = _uiState.value.avatar
                    )
                }!!
                peopleUseCases.updatePerson(person)
            }
            _isPersonSaved.value = true
        }
    }

    private fun validateInput(): Boolean {
        val firstNameResult = addPersonUseCases.validateNamesUseCase(_uiState.value.firstName)
        val secondNameResult =
            addPersonUseCases.validateNamesUseCase(_uiState.value.secondName)
        val placeResult = addPersonUseCases.validatePlaceUseCase(_uiState.value.place)
        val timeResult = addPersonUseCases.validateTimeUseCase(_uiState.value.time)
        val genderResult = addPersonUseCases.validateGenderSelectionUseCase(_uiState.value.gender)

        val hasError = listOf(
            firstNameResult,
            secondNameResult,
            placeResult,
            timeResult,
            genderResult
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                firstNameError = firstNameResult.errorMessage,
                secondNameError = secondNameResult.errorMessage,
                placeError = placeResult.errorMessage,
                timeError = timeResult.errorMessage,
                genderError = genderResult.errorMessage
            )
            _isPersonSaved.value = false
            return false
        }
        return true
    }

    companion object {
        private const val TAG = "AddPersonViewModel"
    }
}
