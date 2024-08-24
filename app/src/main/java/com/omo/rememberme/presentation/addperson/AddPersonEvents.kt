package com.omo.rememberme.presentation.addperson

import androidx.annotation.DrawableRes

sealed class AddPersonEvents {
    data class OnFirstNameChange(val firstName: String) : AddPersonEvents()
    data class OnSecondNameChange(val secondName: String) : AddPersonEvents()
    data class OnPlaceChange(val place: String) : AddPersonEvents()
    data class OnTimeChange(val time: String) : AddPersonEvents()
    data class OnNoteChange(val note: String?) : AddPersonEvents()
    data class OnGenderChange(val gender: String) : AddPersonEvents()
    data class OnAvatarChange(@DrawableRes val avatar: Int) : AddPersonEvents()
    data object OnSavePerson : AddPersonEvents()
}