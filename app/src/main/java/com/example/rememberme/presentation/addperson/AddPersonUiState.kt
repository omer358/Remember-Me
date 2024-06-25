package com.example.rememberme.presentation.addperson

import com.example.rememberme.R

data class AddPersonUiState(
    val firstName: String = "",
    val firstNameError: String? = null,
    val secondName: String = "",
    val secondNameError: String? = null,
    val place: String = "",
    val placeError: String? = null,
    val time: String = "",
    val timeError: String? = null,
    val note: String? = null,
    val gender: String = "",
    val genderError: String? = null,
    val avatar: Int = R.drawable.ic_f1,
)