package com.example.rememberme.domain.usecases.add_person

import android.util.Log
import com.example.rememberme.domain.model.ValidationResult

class ValidateGenderSelectionUseCase {
    operator fun invoke(gender: String): ValidationResult {
        if (gender.isBlank()) {
            Log.d(TAG, "invoke: gender is null")
            return ValidationResult(successful = false, errorMessage = "please, Select the gender!")
        } else {
            Log.d(TAG, "invoke: gender is not null")
            return ValidationResult(successful = true)
        }
    }

    companion object {
        private const val TAG = "ValidateGenderSelection"
    }
}