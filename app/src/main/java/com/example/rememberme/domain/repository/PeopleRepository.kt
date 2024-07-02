package com.example.rememberme.domain.repository

import com.example.rememberme.domain.model.People
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun getAllPeople(): Flow<List<People>>
    suspend fun insertNewPerson(people: People)
    suspend fun deletePersonById(personId: Long)
    suspend fun updatePeople(people: People)
    suspend fun deleteAllPeople()
    suspend fun getPeopleById(id: Long): Flow<People?>
    suspend fun getPeopleByName(name: String): List<People?>
}