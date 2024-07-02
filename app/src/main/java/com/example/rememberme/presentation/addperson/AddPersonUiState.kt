package com.example.rememberme.presentation.addperson

import com.example.rememberme.R

data class AddPersonUiState(
    val id: Long? = null,
    val firstName: String = "",
    val secondName: String = "",
    val place: String = "",
    val time: String = "",
    val note: String? = null,
    val gender: String = "",
    val avatar: Int = R.drawable.ic_m1,
    val firstNameError: String? = null,
    val secondNameError: String? = null,
    val placeError: String? = null,
    val timeError: String? = null,
    val genderError: String? = null
)
