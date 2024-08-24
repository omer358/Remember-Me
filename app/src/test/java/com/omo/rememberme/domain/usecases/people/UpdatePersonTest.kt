package com.omo.rememberme.domain.usecases.people

import com.omo.rememberme.domain.model.People
import com.omo.rememberme.domain.repository.PeopleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class UpdatePersonTest {

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var updatePerson: UpdatePerson

    // Test coroutine dispatcher for testing
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        updatePerson = UpdatePerson(peopleRepository, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `invoke should update person`() = runBlocking {
        // Arrange
        val person = People(
            id = 0L,
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "12:00 PM",
            note = "Met at the park",
            gender = "Male",
            avatar = 1
        )
        peopleRepository.insertNewPerson(person)
        // Mock behavior of repository methods
        `when`(peopleRepository.updatePeople(person)).thenReturn(Unit)

        // Act
        val updatedPlace = "Home"
        person.place = updatedPlace
        updatePerson(person)

        // Assert
        // Verify that updatePeople was called with the correct person
        verify(peopleRepository).updatePeople(person)
    }
}

