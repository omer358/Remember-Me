package com.omo.rememberme.domain.model

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)