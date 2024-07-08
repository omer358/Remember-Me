package com.example.rememberme.domain.usecases.add_person

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ValidateTimeUseCaseTest {

    private lateinit var validateTimeUseCase: ValidateTimeUseCase

    @Before
    fun setUp() {
        validateTimeUseCase = ValidateTimeUseCase()
    }

    @Test
    fun `should return true if time is valid`() {
        val result = validateTimeUseCase("time")
        assert(result.successful)
    }

    @Test
    fun `should return false if time is invalid`() {
        val result = validateTimeUseCase("")
        assert(!result.successful)
    }

    @Test
    fun `should return not-null error message if time is invalid`() {
        val result = validateTimeUseCase("")
        assert(!result.errorMessage.isNullOrEmpty())
    }

    @Test
    fun `should return null error message if time is valid`() {
        val result = validateTimeUseCase("Home")
        assert(result.errorMessage.isNullOrEmpty())
    }

}