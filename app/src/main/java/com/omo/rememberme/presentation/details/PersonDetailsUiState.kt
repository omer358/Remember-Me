package com.omo.rememberme.presentation.details

import com.omo.rememberme.domain.model.People

data class PersonDetailsUiState(
    val person: People? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
