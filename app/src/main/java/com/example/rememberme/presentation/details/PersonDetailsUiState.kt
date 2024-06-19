package com.example.rememberme.presentation.details

import com.example.rememberme.domain.model.People

data class PersonDetailsUiState(
    val person: People? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
