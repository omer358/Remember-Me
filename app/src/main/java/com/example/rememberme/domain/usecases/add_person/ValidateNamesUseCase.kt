package com.example.rememberme.domain.usecases.add_person

import com.example.rememberme.domain.model.ValidationResult

class ValidateNamesUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = " this field cannot be blank"
            )
        }
        if (name.length < 2) {
            return ValidationResult(
                successful = false,
                errorMessage = " this field must be at least 2 characters long"
            )
        }
        return ValidationResult(successful = true)
    }
}
