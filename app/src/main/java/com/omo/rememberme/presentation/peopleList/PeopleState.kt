package com.omo.rememberme.presentation.peopleList

import com.omo.rememberme.domain.model.People

data class PeopleState(
    val people: List<People> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)