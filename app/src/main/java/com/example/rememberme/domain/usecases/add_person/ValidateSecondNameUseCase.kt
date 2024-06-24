package com.example.rememberme.domain.usecases.add_person

import com.example.rememberme.domain.model.ValidationResult

class ValidateSecondNameUseCase {
    operator fun invoke(secondName: String): ValidationResult {
        if (secondName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Second name cannot be blank")
        }
        return ValidationResult(successful = true)
    }
}
