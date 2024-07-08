package com.example.rememberme.domain.usecases.people

import com.example.rememberme.domain.model.People
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
class InsertNewPersonTest {

    @Mock
    private lateinit var peopleRepository: PeopleRepository

    private lateinit var insertNewPerson: InsertNewPerson

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        insertNewPerson = InsertNewPerson(peopleRepository)
    }

    @Test
    fun `invoke should call insertNewPerson on repository`() = runBlocking {
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

        insertNewPerson(person)

        verify(peopleRepository).insertNewPerson(person)
    }
}
