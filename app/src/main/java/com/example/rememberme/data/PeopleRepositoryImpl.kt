package com.example.rememberme.data

import com.example.rememberme.domain.model.People
import com.example.rememberme.domain.repository.PeopleRepository
import com.example.remindme.database.PeopleDao
import kotlinx.coroutines.flow.Flow

class PeopleRepositoryImpl(
    private val peopleDao: PeopleDao
): PeopleRepository {
    override fun getAllPeople(): Flow<List<People>> {
        return peopleDao.getAllPeople()
    }

    override suspend fun insertPeople(people: People) {
        peopleDao.insert(people)
    }

    override suspend fun deletePeople(people: People) {
        peopleDao.removePerson(people.id)
    }

    override suspend fun updatePeople(people: People) {
        peopleDao.update(people)
    }

    override suspend fun deleteAllPeople() {
        TODO("Not yet implemented")
    }

    override suspend fun getPeopleById(id: Long): Flow<People?> {
        return peopleDao.getPerson(id)
    }

    override suspend fun getPeopleByName(name: String): List<People?> {
        TODO("Not yet implemented")
    }
}