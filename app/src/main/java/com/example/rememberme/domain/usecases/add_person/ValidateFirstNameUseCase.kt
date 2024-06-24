package com.example.rememberme.domain.usecases.add_person

import com.example.rememberme.domain.model.ValidationResult

class ValidateFirstNameUseCase {
    operator fun invoke(firstName: String): ValidationResult {
        if (firstName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "First name cannot be blank")
        }
        return ValidationResult(successful = true)
    }
}
