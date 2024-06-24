package com.example.rememberme.domain.usecases.add_person

data class AddPersonUseCases(
    val validateFirstNameUseCase: ValidateFirstNameUseCase,
    val validateSecondNameUseCase: ValidateSecondNameUseCase,
    val validatePlaceUseCase: ValidatePlaceUseCase,
    val validateTimeUseCase: ValidateTimeUseCase
)
