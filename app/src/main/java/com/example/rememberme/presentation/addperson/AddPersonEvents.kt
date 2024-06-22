package com.example.rememberme.presentation.addperson
sealed class AddPersonEvents {
    data class OnFirstNameChange(val firstName: String) : AddPersonEvents()
    data class OnSecondNameChange(val secondName: String) : AddPersonEvents()
    data class OnPlaceChange(val place: String) : AddPersonEvents()
    data class OnTimeChange(val time: String) : AddPersonEvents()
    data class OnNoteChange(val note: String?) : AddPersonEvents()
    data class OnGenderChange(val gender: String) : AddPersonEvents()
    data class OnAvatarChange(val avatar: String) : AddPersonEvents()
    data object OnSavePerson : AddPersonEvents()
}