package com.omo.rememberme.domain.usecases.add_person
import com.omo.rememberme.domain.model.ValidationResult

class ValidateTimeUseCase {
    operator fun invoke(time: String): ValidationResult {
        if (time.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Time cannot be blank")
        }
        // Optionally, you could add more complex time validation here
        return ValidationResult(successful = true)
    }
}
