package com.example.rememberme.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases
) : ViewModel() {
    private val _person = MutableStateFlow<People?>(null)
    val person = _person

    fun getPerson(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                peopleUseCases.getPersonById(id).collect {
                    _person.value = it
                }
            }
        }

    }
}