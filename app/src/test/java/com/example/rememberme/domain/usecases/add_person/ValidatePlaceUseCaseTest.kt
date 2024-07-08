package com.example.rememberme.domain.usecases.add_person

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidatePlaceUseCaseTest {
    private lateinit var validatePlaceUseCase: ValidatePlaceUseCase
    @Before
    fun setUp() {
        validatePlaceUseCase = ValidatePlaceUseCase()
    }

    @Test
    fun `should return true if place is valid`() {
        val result = validatePlaceUseCase("Home")
        assert(result.successful)
    }

    @Test
    fun `should return false if place is invalid`() {
        val result = validatePlaceUseCase("")
        assert(!result.successful)
    }

    @Test
    fun `should return not-null error message if place is invalid`() {
        val result = validatePlaceUseCase("")
        assert(!result.errorMessage.isNullOrEmpty())
    }

    @Test
    fun `should return null error message if place is valid`(){
        val result = validatePlaceUseCase("Home")
        assert(result.errorMessage.isNullOrEmpty())
    }

}