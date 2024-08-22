package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@Ignore("The test needs some dependencies that is not yet injected properly")
class DeletePersonByIdTest {

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    @Mock
    private lateinit var coroutineDispatcher: CoroutineDispatcher

    private lateinit var deletePersonById: DeletePersonById

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        deletePersonById = DeletePersonById(peopleRepository, coroutineDispatcher)
    }

    @Test
    fun `invoke should call deletePersonById on repository`() = runBlocking {
        val personId = 1L

        deletePersonById(personId)

        verify(peopleRepository).deletePersonById(personId)
    }
}
