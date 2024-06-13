package com.example.rememberme.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.rememberme.data.database.People
import com.example.rememberme.data.database.PeopleDatabase
import com.example.remindme.database.PeopleDao
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class PeopleDatabaseTest {

    private lateinit var database: PeopleDatabase
    private lateinit var peopleDao: PeopleDao

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PeopleDatabase::class.java
        ).allowMainThreadQueries().build()

        peopleDao = database.peopleDao
    }

    @After
    fun closeDb() {
        if (this::database.isInitialized) {
            database.close()
        }
    }

    @Test
    fun insertAndGetPerson() = runBlocking {
        val person = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "2023-06-13 10:00",
            note = "Met at the park",
            gender = "Male",
            avatar = 1
        )

        peopleDao.insert(person)

        val retrievedPerson = peopleDao.getAll()[0]
        Assert.assertEquals(retrievedPerson.firstName, "John")
        Assert.assertEquals(retrievedPerson.secondName, "Doe")
    }

    @Test
    fun updatePerson() = runBlocking {
        val person = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "2023-06-13 10:00",
            note = "Met at the park",
            gender = "Male",
            avatar = 1
        )

        peopleDao.insert(person)

        val retrievedPerson = peopleDao.getAll()[0]
        retrievedPerson.firstName = "Jane"
        peopleDao.update(retrievedPerson)

        val updatedPerson = peopleDao.getAll()[0]
        Assert.assertEquals(updatedPerson.firstName, "Jane")
    }

    @Test
    fun deletePerson() = runBlocking {
        val person = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "2023-06-13 10:00",
            note = "Met at the park",
            gender = "Male",
            avatar = 1
        )

        peopleDao.insert(person)

        val retrievedPerson = peopleDao.getAll()[0]
        peopleDao.removePerson(retrievedPerson.id)

        val people = peopleDao.getAll()
        Assert.assertTrue(people.isEmpty())
    }

    @Test
    fun clearAllPeople() = runBlocking {
        val person1 = People(
            firstName = "John",
            secondName = "Doe",
            place = "Park",
            time = "2023-06-13 10:00",
            note = "Met at the park",
            gender = "Male",
            avatar = 1
        )

        val person2 = People(
            firstName = "Jane",
            secondName = "Doe",
            place = "Mall",
            time = "2023-06-13 12:00",
            note = "Met at the mall",
            gender = "Female",
            avatar = 2
        )

        peopleDao.insertAll(person1, person2)

        peopleDao.clear()
        val people = peopleDao.getAll()
        Assert.assertTrue(people.isEmpty())
    }
}
