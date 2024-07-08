package com.example.rememberme.domain.usecases.people

// Import statements
import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetPersonByIdTest {

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var getPersonById: GetPersonById

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPersonById = GetPersonById(peopleRepository)
    }

    @Test
    fun `invoke should return flow of person by id`() = runTest {
        // Given
        val personId = 1L
        val person = People(id = personId, firstName = "John", secondName = "Doe", place = "Place", time = "Time", note = "Note", gender = "Male", avatar = 0)
        val flowPerson = flowOf(person)

        Mockito.`when`(peopleRepository.getPeopleById(personId)).thenReturn(flowPerson)

        // When
        val result = getPersonById(personId)

        // Then
        result.collect { personResult ->
            assert(personResult == person)
        }
        Mockito.verify(peopleRepository).getPeopleById(personId)
    }
}
