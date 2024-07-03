package com.example.rememberme.domain.usecases.add_person

import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ValidateNamesUseCaseTest{

    @Mock
    private lateinit var validateNamesUseCase: ValidateNamesUseCase

    @Before
    fun setUp(){
        validateNamesUseCase = ValidateNamesUseCase()
    }

    @Test
    fun `invoke should return true if name is valid`(){
        val result = validateNamesUseCase("Om")
        assert(result.successful)
    }
    @Test
    fun `invoke should return error message is null if name is valid`(){
        val result = validateNamesUseCase("omer")
        assert(result.errorMessage.isNullOrEmpty())
    }

    @Test
    fun `invoke should return false if name is invalid`(){
        val result = validateNamesUseCase("")
        assert(!result.successful)
    }
    @Test
    fun `invoke should return error message if name is blank`(){
        val result = validateNamesUseCase("")
        assert(!result.errorMessage.isNullOrEmpty())
    }

    @Test
    fun `invoke should return false if name is less than 2 characters`(){
        val result = validateNamesUseCase("A")
        assert(!result.successful)
    }
    @Test
    fun `invoke should return error message if name is less than 2 characters`(){
        val result = validateNamesUseCase("A")
        assert(!result.errorMessage.isNullOrEmpty())
    }
}