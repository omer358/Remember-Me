package com.example.rememberme.domain.usecases.add_person
import com.example.rememberme.domain.model.ValidationResult

class ValidatePlaceUseCase {
    operator fun invoke(place: String): ValidationResult {
        if (place.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Meeting place cannot be blank")
        }
        return ValidationResult(successful = true)
    }
}
