package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeletePersonByIdTest {

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var deletePersonById: DeletePersonById

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deletePersonById = DeletePersonById(peopleRepository)
    }

    @Test
    fun `invoke should call deletePersonById on repository`() = runBlocking {
        val personId = 1L

        deletePersonById(personId)

        verify(peopleRepository).deletePersonById(personId)
    }
}
