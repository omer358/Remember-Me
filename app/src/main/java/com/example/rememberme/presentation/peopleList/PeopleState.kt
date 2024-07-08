package com.example.rememberme.presentation.peopleList

import com.example.rememberme.domain.model.People

data class PeopleState(
    val people: List<People> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)