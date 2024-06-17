package com.example.rememberme.domain.repository

import com.example.rememberme.domain.model.People
import kotlinx.coroutines.flow.Flow

interface PeopleRepository {
    fun getAllPeople(): Flow<List<People>>
    suspend fun insertPeople(people: People)
    suspend fun deletePeople(people: People)
    suspend fun updatePeople(people: People)
    suspend fun deleteAllPeople()
    suspend fun getPeopleById(id: Int): People?
    suspend fun getPeopleByName(name: String): List<People?>
}