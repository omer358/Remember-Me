package com.example.rememberme.domain.usecases.add_person

import com.example.rememberme.domain.model.ValidationResult

class ValidateNamesUseCase {
    operator fun invoke(name: String): ValidationResult {
        if (name.isBlank() && name.length >= 2) {
            return ValidationResult(
                successful = false,
                errorMessage = " this field cannot be blank"
            )
        }
        return ValidationResult(successful = true)
    }
}
