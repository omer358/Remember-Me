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
        TODO("Not yet implemented")
    }

    override suspend fun deletePeople(people: People) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePeople(people: People) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllPeople() {
        TODO("Not yet implemented")
    }

    override suspend fun getPeopleById(id: Int): People? {
        TODO("Not yet implemented")
    }

    override suspend fun getPeopleByName(name: String): List<People?> {
        TODO("Not yet implemented")
    }
}