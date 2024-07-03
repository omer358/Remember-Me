package com.example.rememberme.data

import com.example.rememberme.domain.model.People
import com.example.remindme.database.PeopleDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PeopleRepositoryImplTest {

    @Mock
    private lateinit var peopleDao: PeopleDao

    private lateinit var peopleRepository: PeopleRepositoryImpl

    @Before
    fun setup() {
        peopleRepository = PeopleRepositoryImpl(peopleDao)
    }

    @Test
    fun getAllPeople_returnsFlowOfPeopleList() = runTest {
        val peopleList = listOf(People(1L, "John", "Doe", "Park", "12:00", "Note", gender = "Male", avatar = 0))
        Mockito.`when`(peopleDao.getAllPeople()).thenReturn(flowOf(peopleList))

        val result = peopleRepository.getAllPeople()

        result.collect { people ->
            assert(people == peopleList)
        }
    }

    @Test
    fun insertNewPerson_insertsPersonIntoDao() = runTest {
        val person = People(1L, "John", "Doe", "Park", "12:00", "Note", gender = "Male", avatar = 0)

        peopleRepository.insertNewPerson(person)

        Mockito.verify(peopleDao).insert(person)
    }

    @Test
    fun deletePersonById_removesPersonFromDao() = runTest {
        val personId = 1L

        peopleRepository.deletePersonById(personId)

        Mockito.verify(peopleDao).removePerson(personId)
    }

    @Test
    fun updatePeople_updatesPersonInDao() = runTest {
        val person = People(1L, "John", "Doe", "Park", "12:00", "Note", gender = "Male", avatar = 0)

        peopleRepository.updatePeople(person)

        Mockito.verify(peopleDao).update(person)
    }

    @Test
    fun getPeopleById_returnsPersonFlowFromDao() = runTest {
        val person = People(1L, "John", "Doe", "Park", "12:00", "Note", gender = "Male", avatar = 0)
        Mockito.`when`(peopleDao.getPerson(1L)).thenReturn(flowOf(person))

        val result = peopleRepository.getPeopleById(1L)

        result.collect { fetchedPerson ->
            assert(fetchedPerson == person)
        }
    }
}
