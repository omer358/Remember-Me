package com.example.rememberme.presentation.peopleList

import androidx.lifecycle.ViewModel
import com.example.rememberme.domain.usecases.people.PeopleUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val peopleUseCases: PeopleUseCases
): ViewModel() {
    val people = peopleUseCases.getAllPeople()
}