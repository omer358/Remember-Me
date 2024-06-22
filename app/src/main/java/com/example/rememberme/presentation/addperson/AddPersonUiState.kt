package com.example.rememberme.presentation.addperson

data class AddPersonUiState(
    val firstName: String = "",
    val secondName: String = "",
    val place: String = "",
    val time: String = "",
    val note: String? = null,
    val gender: String = "",
    val avatar: String = ""
)