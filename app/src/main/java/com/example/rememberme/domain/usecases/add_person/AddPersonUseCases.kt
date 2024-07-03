package com.example.rememberme.domain.usecases.add_person

data class AddPersonUseCases(
    val validateNamesUseCase: ValidateNamesUseCase,
    val validatePlaceUseCase: ValidatePlaceUseCase,
    val validateTimeUseCase: ValidateTimeUseCase,
    val validateGenderSelectionUseCase: ValidateGenderSelectionUseCase
)
