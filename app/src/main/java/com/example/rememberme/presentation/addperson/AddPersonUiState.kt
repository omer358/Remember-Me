package com.example.rememberme.presentation.addperson

import com.example.rememberme.R

data class AddPersonUiState(
    val firstName: String = "",
    val secondName: String = "",
    val place: String = "",
    val time: String = "",
    val note: String? = null,
    val gender: String = "",
    val avatar: Int = R.drawable.ic_f1,
)