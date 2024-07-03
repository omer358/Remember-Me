package com.example.rememberme.domain.usecases.add_person

import com.example.rememberme.domain.model.ValidationResult

class ValidateGenderSelectionUseCase {
    operator fun invoke(gender: String): ValidationResult {
        return if (gender.isBlank()) {
            ValidationResult(successful = false, errorMessage = "please, Select the gender!")
        } else {
            ValidationResult(successful = true)
        }
    }
}