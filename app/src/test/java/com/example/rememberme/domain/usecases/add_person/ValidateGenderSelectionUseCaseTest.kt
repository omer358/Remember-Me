package com.example.rememberme.domain.usecases.add_person

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidateGenderSelectionUseCaseTest {

    private lateinit var validateGenderSelectionUseCase: ValidateGenderSelectionUseCase
    @Before
    fun setUp() {
        validateGenderSelectionUseCase = ValidateGenderSelectionUseCase()
    }

    @Test
    fun `should return false if gender is invalid`(){
     val result = validateGenderSelectionUseCase("")
     assert(!result.successful)
    }

    @Test
    fun `should return not-null error message if gender is invalid`(){
        val result = validateGenderSelectionUseCase("")
        assert(!result.errorMessage.isNullOrEmpty())
    }

    @Test
    fun `should return true if gender is valid`(){
        val result = validateGenderSelectionUseCase("Male")
        assert(result.successful)
    }

    @Test
    fun `should return null error message if gender is invalid`(){
        val result = validateGenderSelectionUseCase("Male")
        assert(result.errorMessage.isNullOrBlank())
    }
}