package com.example.rememberme.presentation.addperson

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.usecases.add_person.AddPersonUseCases
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
                Log.i(TAG, "onEvent: OnFirstNameChange -> ${event.firstName}")
                _uiState.value = _uiState.value.copy(firstName = event.firstName, firstNameError = null)
            }

            is AddPersonEvents.OnSecondNameChange -> {
                Log.i(TAG, "onEvent: OnSecondNameChange -> ${event.secondName}")
                _uiState.value = _uiState.value.copy(secondName = event.secondName, secondNameError = null)
            }

            is AddPersonEvents.OnPlaceChange -> {
                Log.i(TAG, "onEvent: OnPlaceChange -> ${event.place}")
                _uiState.value = _uiState.value.copy(place = event.place, placeError = null)
            }

            is AddPersonEvents.OnTimeChange -> {
                Log.i(TAG, "onEvent: OnTimeChange -> ${event.time}")
                _uiState.value = _uiState.value.copy(time = event.time)
            }

            is AddPersonEvents.OnNoteChange -> {
                Log.i(TAG, "onEvent: OnNoteChange -> ${event.note}")
                _uiState.value = _uiState.value.copy(note = event.note)
            }

            is AddPersonEvents.OnGenderChange -> {
                Log.i(TAG, "onEvent: OnGenderChange -> ${event.gender}")
                _uiState.value = _uiState.value.copy(gender = event.gender, genderError = null)
            }

            is AddPersonEvents.OnAvatarChange -> {

                Log.i(TAG, "onEvent: OnAvatarChange -> ${event.avatar}")
                _uiState.value = _uiState.value.copy(avatar = event.avatar)
            }

            AddPersonEvents.OnSavePerson -> {
                Log.i(TAG, "onEvent: OnSavePerson")
                savePerson()
            }
        }
    }

    private fun savePerson() {
        val firstNameResult = addPersonUseCases.validateFirstNameUseCase(_uiState.value.firstName)
        val secondNameResult =
            addPersonUseCases.validateSecondNameUseCase(_uiState.value.secondName)
        val placeResult = addPersonUseCases.validatePlaceUseCase(_uiState.value.place)
        val timeResult = addPersonUseCases.validateTimeUseCase(_uiState.value.time)
        val genderResult = addPersonUseCases.validateGenderSelectionUseCase(_uiState.value.gender)
        Log.d(TAG, "savePerson: $genderResult")
        val hasError = listOf(
            firstNameResult,
            secondNameResult,
            placeResult,
            timeResult,
            genderResult
        ).any { !it.successful }
        if (hasError) {
            Log.e(TAG, "savePerson: There are some unvalidated inputs")
            Log.e(TAG, "savePerson: ${_uiState.value}")
            _uiState.value = _uiState.value.copy(
                firstNameError = firstNameResult.errorMessage,
                secondNameError = secondNameResult.errorMessage,
                placeError = placeResult.errorMessage,
                timeError = timeResult.errorMessage,
                genderError = genderResult.errorMessage
            )
            _isPersonSaved.value = false

        } else {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    Log.i(TAG, "savePerson: ${_uiState.value}")
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
    }

    companion object {
        private const val TAG = "AddPersonViewModel"
    }

}